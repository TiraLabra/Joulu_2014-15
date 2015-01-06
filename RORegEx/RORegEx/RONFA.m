//
//  RONFA.m
//  RORegEx
//
//  Created by Riku Oja on 17.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import "RONFA.h"
#import "ROState.h"

@interface RONFA ()
/**
 *  @brief The starting state of the automaton.
 *  @discussion Contains pointers to all the other states; hence, it need be the only strong reference to a state.
 */
@property (strong, nonatomic)ROState* initialState;
/**
 *  The current states in the running of the automaton. There are several as the running is done in parallel.
 */
@property (strong, nonatomic)NSMutableSet* currentStates;
/**
 *  The current states for the next step.
 */
@property (strong, nonatomic)NSMutableSet* nextStates;
/**
 *  The predefined regexp operators that are not treated as literals.
 */
@property NSCharacterSet* operators;
/**
 *  @brief Contains all the states of the automaton that indicate a pattern match, i.e. those that have finality=YES.
 *  @discussion Note that a set contains strong references and the set must be strong. Hence, retain cycles of states pointing back to the NFA must be avoided. This is no problem as long as the state is automaton-agnostic.
*/
@property (strong, nonatomic)NSMutableSet* finalStates;
@end

@implementation RONFA

-(id) initWithRegEx:(NSString *)regEx {
    //this is to be used if the desired initial state doesn't exist yet:
    self=[self initWithState:[[ROState alloc]init] withRegEx:regEx];
    return self;
}

-(id) initWithState:(ROState *)state withRegEx:(NSString *)regEx {
    self=[super init];
    if (self == nil) return self;
    //Here we implement the class-specific initialization:
    self.operators = [NSCharacterSet characterSetWithCharactersInString:@".*()"];
    self.finalStates=[NSMutableSet set];
    self.initialState=state;
    ROState* currentState=self.initialState;
    for (long i=0; i<regEx.length; i++) {
        NSString* character=[regEx substringWithRange:NSMakeRange(i, 1)];
        //Here, we have the default behavior of matching the character:
        if ([character rangeOfCharacterFromSet:self.operators].location == NSNotFound) {
            currentState.matchingCharacter = character;
        }
        else if ([character isEqualToString:@"."]) {
            //any character matches. matchingCharacter is already nil by default =)
        }
        else if ([character isEqualToString:@"*"]) {
            //we have to match zero or more characters.
            //currentState becomes a forking state. The NFA moves immediately to the next state (matches zero characters). The alternate arrow points to "any character" state (matches one character), which points back here. *never* link a fork state directly back to itself (creates an infinite loop when matching)!
            currentState.alternateState=[[ROState alloc] init]; //the any character state
            currentState.alternateState.nextState=currentState; //the current fork state
        }
        else if ([character isEqualToString:@"("]) {
            //here we must implement recursive NFAs for the expression in parentheses
            //first, find the corresponding closing parenthesis:
            long parentheses=1;
            long j=i;
            while (parentheses>0) {
                j++;
                NSString* subcharacter=[regEx substringWithRange:NSMakeRange(i, 1)];
                if ([subcharacter isEqualToString:@"("]) parentheses++;
                if ([subcharacter isEqualToString:@")"]) parentheses--;
            }
            //make a new NFA for the subexpression, set the current state as its initial state and skip to the end:
            NSString* subexpression=[regEx substringWithRange:NSMakeRange(i+1, j-i-1)];
            RONFA* subNFA=[[RONFA alloc] initWithState:currentState withRegEx:subexpression];
            i=j;
            //Here, the construction of the NFA will fork into discrete parts (no subsequent merges possible):
            //move on to the states matched by the subNFA:
            //currentState=subNFA.finalState;
        }
        //we do not need to explicitly create pointers to the successive states, as the pointers form a tree starting from initialState
        //create the next state:
        currentState.nextState=[[ROState alloc] init];
        //move on to the state matched by the character:
        currentState=currentState.nextState;
    }
    //after the loop, the ending state indicates that we have a pattern:
    currentState.finality=YES;
    [self.finalStates addObject:currentState];
    //after initialization, return the automaton to the beginning for running:
    [self rewind];

    return self;
}

-(NSRange)findMatch:(NSString *)string {
    long i=0; //the letter we are at in the string
    
    //break condition checking requires ascending order:
    NSSortDescriptor* sortAscendingByStartIndex =[NSSortDescriptor sortDescriptorWithKey:@"startIndex" ascending:YES selector:@selector(compare:)];
    NSArray* sortedStates; //here we store the finished matches sorted according to descriptor
    NSMutableSet* finishedMatches=[NSMutableSet set];
    
    while (i<string.length) {
        //we have to check if we have forking states first:
        BOOL forkDiscovered=YES;
        ROState* toBeRemoved; //the fork state to be removed
        while (forkDiscovered) {
            forkDiscovered=NO;
            toBeRemoved=nil;
            //find the first fork state:
            for (ROState* state in self.currentStates) {
                if (state.alternateState!= nil) {
                    forkDiscovered=YES;
                    toBeRemoved=state;
                    //never link a fork state back to itself (creates an infinite loop!)
                    [self.currentStates addObject:state.nextState];
                    [self.currentStates addObject:state.alternateState];
                    //also, remember to propagate the starting index of the match from any fork state to both of the subsequent states, as the fork state does not enter matchCharacter method!!
                    state.nextState.startIndex=state.startIndex;
                    state.alternateState.startIndex=state.startIndex;
                    //this particular fork has been processed (even if multiple branches ended up in the same fork), so we can remove it from current states.
                    //cannot remove object from within the iteration loop. we have a fork to remove, so get out of the loop:
                    break;
                }
            }
            if (toBeRemoved!=nil) [self.currentStates removeObject:toBeRemoved];
        }
        
        NSString* character=[string substringWithRange:NSMakeRange(i, 1)];
        //first iterate through current states:
        for (ROState* state in self.currentStates) {
            //at this point, there are no forking states present so all the current states with matchingCharacter=nil are of the type "match any character"
            [self matchCharacter:character inState:state forIndex:i];
        }
        //save the states for the next step:
        self.currentStates=self.nextStates;
        self.nextStates=[NSMutableSet set];
        //update start indices before sorting and check finality:
        for (ROState* state in self.currentStates) {
            state.startIndex=state.nextStartIndex;
            state.nextStartIndex=[NSNumber numberWithUnsignedLong:NSUIntegerMax];
            if (state.finality==YES) [finishedMatches addObject:state];
        }
        //finally, check break condition:
        if (![finishedMatches count]==0) {
            sortedStates=[finishedMatches sortedArrayUsingDescriptors:@[sortAscendingByStartIndex]];
            NSNumber* matchStart=((ROState *)sortedStates[0]).startIndex;
            return NSMakeRange([matchStart intValue],i-[matchStart intValue]+1);
        }
        //proceed to the next character:
        i++;
    }
    //only reached if none of the steps reached finality:
    return NSMakeRange(0,0);
}

-(void)matchCharacter:(NSString *) character inState:(ROState *)state forIndex:(long) i{
    //because of nondeterminism, at character match we get two branches!
    //(remember to compare NSStrings, not pointers =)
    if ([character isEqualToString:state.matchingCharacter] || state.matchingCharacter==nil) {
        //matching character found, or "any character" match!
        //first add the next state:
        [self.nextStates addObject:state.nextState];
        NSNumber *matchStartIndex;
        //if the current state is not yet in a matching branch, we are at the beginning of a match and must mark the start index for the next step:
        if ([state.startIndex isEqualToNumber:[NSNumber numberWithUnsignedLong:NSUIntegerMax]]) matchStartIndex=[NSNumber numberWithUnsignedLong:i];
        //if we are already in a matching branch, propagate old starting index:
        else matchStartIndex=state.startIndex;
        //to maintain internal consistency, each step must *only* update the *next* start index for the *next* state, depending on the *current* start index of the *current* state!
        if (state.nextState.nextStartIndex>matchStartIndex) state.nextState.nextStartIndex=matchStartIndex;
    }
    //whether character was found or not, we always wind back to initial state for future matches:
    [self.nextStates addObject:self.initialState];
}

-(void)rewind {
    self.nextStates=[NSMutableSet set];
    self.currentStates=[NSMutableSet set];
    //start from the initial state:
    [self.currentStates addObject:self.initialState];
}

@end
