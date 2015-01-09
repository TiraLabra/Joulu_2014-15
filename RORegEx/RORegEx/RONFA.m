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
 *  The current states in the construction and running of the automaton. There are several as the running is done in parallel.
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
/**
 *  Contains the regular expression used for constructing the automaton.
 */
@property (strong, nonatomic)NSString* regEx;
@end

@implementation RONFA

-(id) initWithRegEx:(NSString *)regEx {
    //this is to be used if the desired initial state doesn't exist yet:
    ROState* blankInitialState=[[ROState alloc]init];
    self=[self initWithState:blankInitialState withRegEx:regEx];
    return self;
}

-(id) initWithState:(ROState *)state withRegEx:(NSString *)regEx {
    self=[super init];
    if (self == nil) return self;
    //Here we implement the class-specific initialization:
    self.operators = [NSCharacterSet characterSetWithCharactersInString:@".*()|?"];
    self.regEx=regEx; //save the regex for reinitialization
    self.finalStates=[NSMutableSet set];
    self.initialState=state;
    [self rewind]; //creates the currentStates array and adds the initial state there
    for (long i=0; i<regEx.length; i++) {
        for (ROState* currentState in self.currentStates) {
            NSString* character=[regEx substringWithRange:NSMakeRange(i, 1)];
            //checking order: 1) parentheses 2) operators 3) next char operators 4) single char match
            if ([character isEqualToString:@"("]) {
                //here we must implement recursive NFAs for the expression in parentheses
                //first, find the corresponding closing parenthesis:
                long parentheses=1;
                long j=i;
                while (parentheses>0) {
                    j++;
                    NSString* subcharacter=[regEx substringWithRange:NSMakeRange(j, 1)];
                    if ([subcharacter isEqualToString:@"("]) parentheses++;
                    if ([subcharacter isEqualToString:@")"]) parentheses--;
                }
                //make a new NFA for the subexpression, set the current state as its initial state and skip to the end:
                NSString* subexpression=[regEx substringWithRange:NSMakeRange(i+1, j-i-1)];
                RONFA* subNFA=[[RONFA alloc] initWithState:currentState withRegEx:subexpression];
                i=j;
                //if the initial state of the subexpression changed (due to nesting), we must update all the properties of freshly created currentState to reflect it!!! (remember the subNFA will be garbagecollected and its initialState with it!!!) if we only change currentState pointer to subNFA.initialState, currentState will no longer be linked to the states in *this* NFA, cannot do that :D
                [currentState copyFromState:subNFA.initialState];
                
                //Here, the resulting NFA may have several final states:
                self.nextStates=subNFA.finalStates;
                //These states are freshly created, they have finality=YES and no nextStates added. Correct that:
                for (ROState* state in subNFA.finalStates) state.finality=NO;
                //self.nextStates exist already, skip creating them:
                continue;
            }
            
            //2) check operators:
            else if ([character isEqualToString:@"|"]) {
                //here we must make a new initial state for the superexpression, nest the current NFA inside it, and create another NFA for the other subexpression!!! forking into multiple final states ensues, but the current state is one of them.
                //now the problem is that *if we have two current states, we are doing the same thing twice* or, if we break at the first option, we are not adding all the current states to the end states? is it enough just to add both and not do anything else before breaking???
                NSString* theOtherSubexpression=[regEx substringWithRange:NSMakeRange(i+1, regEx.length-i-1)];
                ROState* newInitialState = [[ROState alloc] init];
                //here we must be careful about infinite loops when moving pointers!; better create a new state pointer with all the properties of the initial state, and just forget the original initialstate!!
                newInitialState.nextState=[self.initialState copy];
                self.initialState=newInitialState;
                ROState* theOtherState = [[ROState alloc] init];
                newInitialState.alternateState=theOtherState; //the initial state is now a fork!
                RONFA* theOtherNFA =[[RONFA alloc] initWithState:theOtherState withRegEx:theOtherSubexpression];
                //we cannot edit self.currentStates within the loop!!!
                //instead, save the current (final state) and the final states of the other subexpression in self.nextStates:
                self.nextStates=theOtherNFA.finalStates;
                [self.nextStates addObjectsFromArray:[self.currentStates allObjects]];
                //we have processed the rest of the expression and will not have to process the rest of the currentStates!
                //we created self.nextStates and they are the final states, including all of the currentStates:
                i=regEx.length;
                break;
            }
            //if the operator is * or ?, we have to make the previous matching group optional! how to add fork in place of *previous state*, as we don't know how many steps are in between before we arrived at currentState??
            
            //3) check next character operators:
            NSString* nextCharacter;
            if (i<regEx.length-1) nextCharacter=[regEx substringWithRange:NSMakeRange(i+1, 1)];
            if ([nextCharacter isEqualToString:@"?"]) {
                //this means the character is optional, i.e. we have a fork and a match.:
                currentState.alternateState=[[ROState alloc] init];
                if (![character isEqualToString:@"."]) currentState.alternateState.matchingCharacter=character; //this makes sure we can also have . combine with operators
                //create the next state that makes matching optional:
                currentState.nextState=[[ROState alloc] init];
                //we go here also after a single match:
                currentState.alternateState.nextState=currentState.nextState;
                [self.nextStates addObject:currentState.nextState];
                //skip over question mark:
                i++;
                //self.nextStates exist already, skip creating them:
                continue;
            }
            else if ([nextCharacter isEqualToString:@"*"]) {
                //like ?, but we can match more than one character:
                currentState.alternateState=[[ROState alloc] init];
                if (![character isEqualToString:@"."]) currentState.alternateState.matchingCharacter=character; //this makes sure we can also have . combine with operators
                currentState.alternateState.nextState=currentState;
                //create the next state that makes matching optional:
                currentState.nextState=[[ROState alloc] init];
                [self.nextStates addObject:currentState.nextState];
                //skip over the asterisk:
                i++;
                //self.nextStates exist already, skip creating them:
                continue;
            }
            
            //4) *finally* match character, including . operator:
            if ([character rangeOfCharacterFromSet:self.operators].location == NSNotFound) {
                currentState.matchingCharacter = character;
            }
            else if ([character isEqualToString:@"."]) {
                //any character matches. matchingCharacter is already nil by default =)
            }
            //we do not need to explicitly create pointers to the successive states, as the pointers form a tree starting from initialState
            //create the next state:
            currentState.nextState=[[ROState alloc] init];
            //move on to the next state for matching next character:
            [self.nextStates addObject:currentState.nextState];
        }
        //update the sets the loop runs over:
        self.currentStates=self.nextStates;
        self.nextStates=[NSMutableSet set];
    }
    //after the loop, all the states we are in correspond to a pattern match:
    for (ROState* currentState in self.currentStates) currentState.finality=YES;
    self.finalStates=self.currentStates;
    //after initialization, return the automaton to the beginning for running:
    [self rewind];
    return self;
}

-(BOOL) pruneForks:(NSMutableSet*) setOfStates{
    BOOL forkDiscovered=YES;
    ROState* toBeRemoved; //the fork state to be removed
    ROState* toBeAdded;
    ROState* alternateToBeAdded; //the two children of the fork state, to be added
    while (forkDiscovered) {
        forkDiscovered=NO;
        toBeRemoved=nil;
        toBeAdded=nil;
        alternateToBeAdded=nil;
        //find the first fork state:
        for (ROState* state in setOfStates) {
            if (state.alternateState!= nil) {
                forkDiscovered=YES;
                toBeRemoved=state;
                //never link a fork state back to itself (creates an infinite loop!)
                toBeAdded=state.nextState;
                alternateToBeAdded=state.alternateState;
                //also, remember to propagate the starting index of the match from any fork state to both of the subsequent states, as the fork state does not enter matchCharacter method!!
                //*don't* overwrite smaller start indices if already present (greedy matching):
                if (state.nextState.startIndex>state.startIndex) state.nextState.startIndex=state.startIndex;
                if (state.alternateState.startIndex>state.startIndex) state.alternateState.startIndex=state.startIndex;
                //this particular fork has been processed (even if multiple branches ended up in the same fork), so we can remove it from current states.
                //cannot add and remove objects from within the iteration loop. we have states to add and a fork to remove, so get out of the loop:
                break;
            }
        }
        if (toBeRemoved!=nil) [setOfStates removeObject:toBeRemoved];
        if (toBeAdded!= nil) [setOfStates addObject:toBeAdded];
        if (alternateToBeAdded!=nil) [setOfStates addObject:alternateToBeAdded];
    }
    return forkDiscovered;
}

-(NSRange)findMatch:(NSString *)string {
    //we must reinitialize the machine to reset start indices:
    [self initWithRegEx:self.regEx];
    
    long i=0; //the letter we are at in the string
    
    //break condition checking requires ascending order:
    NSSortDescriptor* sortAscendingByStartIndex =[NSSortDescriptor sortDescriptorWithKey:@"startIndex" ascending:YES selector:@selector(compare:)];
    NSArray* sortedStates; //here we store the finished matches sorted according to descriptor
    NSMutableSet* finishedMatches=[NSMutableSet set];

    //first we have to check if the initial states contain forks:
    [self pruneForks:self.currentStates];
    
    while (i<string.length) {
        //at this point, there are no forking states present so all the current states with matchingCharacter=nil are of the type "match any character"
        NSString* character=[string substringWithRange:NSMakeRange(i, 1)];
        //first iterate through current states:
        for (ROState* state in self.currentStates) {
            [self matchCharacter:character inState:state forIndex:i];
        }
        //update start indices to the new states *before* checking finality:
        for (ROState* state in self.nextStates) {
            state.startIndex=state.nextStartIndex;
            state.nextStartIndex=[NSNumber numberWithUnsignedLong:NSUIntegerMax];
        }
        //the next states have not yet been checked for forks! we have to do it before checking break condition:
        [self pruneForks:self.nextStates];
        for (ROState* state in self.nextStates) if (state.finality==YES) [finishedMatches addObject:state];
        //finally, return a match if a final state is ahead:
        if (![finishedMatches count]==0) {
            sortedStates=[finishedMatches sortedArrayUsingDescriptors:@[sortAscendingByStartIndex]];
            NSNumber* matchStart=((ROState *)sortedStates[0]).startIndex;
            return NSMakeRange([matchStart intValue],i-[matchStart intValue]+1);
        }
        //proceed to the next character:
        self.currentStates=self.nextStates;
        self.nextStates=[NSMutableSet set];
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
