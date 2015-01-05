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
 *  @brief The current states in the running of the automaton. There are several as the running is done in parallel.
 *  @discussion The greedy algorithm requires the current states to be sorted at each step according to the start index of the match and, hence, extra O(mlogm) load. Another option would be to save the states in a two-dimensional binary search tree.
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

-(id) init {
    self = [super init];
    if (self == nil) return self;
    //Here we implement the class-specific initialization:
    self.operators = [NSCharacterSet characterSetWithCharactersInString:@"."];
    self.initialState=[[ROState alloc] init];
    self.finalStates=[NSMutableSet set];
    [self rewind];
    return self;
}

-(id) initWithRegEx:(NSString *)regEx {
    self=[self init];
    ROState* currentState=self.initialState;
    for (int i=0; i<regEx.length; i++) {
        NSString* character=[regEx substringWithRange:NSMakeRange(i, 1)];
        //we have to check if the next character is an operator:
        NSString* nextCharacter=nil;
        if (i<regEx.length-1) nextCharacter=[regEx substringWithRange:NSMakeRange(i+1, 1)];
        //Here, we have the default behavior of matching the character:
        if ([character rangeOfCharacterFromSet:self.operators].location == NSNotFound) {
            currentState.matchingCharacter = character;
        }
        else if ([character isEqualToString:@"."]) {
            currentState.matchingCharacter=nil;
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

-(id) initWithState:(ROState *)state fromNFA:(RONFA *)NFA {
    self=[self init];
    self.initialState=state;
    //move to the new initial state:
    [self rewind];
    self.finalStates=NFA.finalStates;
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
        while (forkDiscovered) {
            forkDiscovered=NO;
            for (ROState* state in self.currentStates) {
                if (state.alternateState!= nil) {
                    forkDiscovered=YES;
                    [self.currentStates addObject:state.nextState];
                    [self.currentStates addObject:state.alternateState];
                    //this particular fork has been processed (even if multiple branches ended up in the same fork), so we can remove it from current states:
                    [self.currentStates removeObject:state.alternateState];
                    //currentStates have been modified, so restart from the beginning:
                    break;
                }
            }
        }
        
        NSString* character=[string substringWithRange:NSMakeRange(i, 1)];
        //first iterate through current states:
        for (ROState* state in self.currentStates) {
            //Here, we have the default behavior of matching the character:
            //if ([character rangeOfCharacterFromSet:self.operators].location == NSNotFound) {
                [self matchCharacter:character inState:state forIndex:i];
            //}
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
