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
 *  @discussion Contains pointers to all the other states; hence, it is the only strong reference to a state.
 */
@property (strong, nonatomic)ROState* initialState;
//Pointers to all the other states are contained in initialState.
/**
 *  The state from which the automaton arrived to the current state, used in both constructing and running the automaton. The same as currentState if we had an epsilon transition.
 */
@property (weak, nonatomic)ROState* previousState;
/**
 *  The current state of the automaton, used in both constructing and running the automaton.
 */
@property (weak, nonatomic)ROState* currentState;
/**
 *  The predefined regexp operators that are not treated as literals.
 */
@property NSCharacterSet* operators;
/**
 *  @brief Contains all the states of the automaton that indicate a pattern match, i.e. those that have finality=YES.
 *  @discussion Note that the array contains strong references and the array must be strong. Hence, retain cycles of states pointing back to the NFA must be avoided. This is no problem as long as the state is automaton-agnostic.
*/
@property (strong, nonatomic)NSMutableArray* finalStates;
@end

@implementation RONFA

-(id) init {
    self = [super init];
    if (self == nil) return self;
    //Here we implement the class-specific initialization:
    self.operators = [NSCharacterSet characterSetWithCharactersInString:@""];
    return self;
}

-(id) initWithRegEx:(NSString *)regEx {
    self=[self init];
    self.initialState=[[ROState alloc] init];
    self.finalStates=[NSMutableArray array];
    self.currentState=self.initialState;
    //in the beginning, we have epsilon transition so the previous state is the current one:
    self.previousState=self.currentState;
    for (int i=0; i<regEx.length; i++) {
        NSString* character=[regEx substringWithRange:NSMakeRange(i, 1)];
        //Here, we have the default behavior of matching the character:
        if ([character rangeOfCharacterFromSet:self.operators].location == NSNotFound) {
            self.currentState.matchingCharacter = character;
            //we do not need to explicitly create pointers to the successive states, as the pointers form a tree starting from initialState
            //create the next state:
            self.currentState.nextState=[[ROState alloc] init];
            //save the current state as the previous one:
            self.previousState=self.currentState;
            //move on to the state matched by the character:
            self.currentState=self.currentState.nextState;
        }
    }
    //after the loop, the ending state indicates that we have a pattern:
    self.currentState.finality=YES;
    [self.finalStates addObject:self.currentState];
    //after initialization, return the automaton to the beginning for running:
    [self rewind];
    return self;
}

-(id) initWithState:(ROState *)state fromNFA:(RONFA *)NFA {
    self=[self init];
    self.initialState=state;
    self.currentState=state;
    self.previousState=NFA.currentState;
    self.finalStates=NFA.finalStates;
    return self;
}

-(NSRange)findMatch:(NSString *)string {
    int i; //the letter we are at in the string
    for (i=0; i<string.length; i++) {
        NSString* character=[string substringWithRange:NSMakeRange(i, 1)];
        //Here, we have the default behavior of matching the character:
        if ([character rangeOfCharacterFromSet:self.operators].location == NSNotFound) {
            //because of nondeterminism, at character match we fork:
            //(remember to compare NSStrings, not pointers =)
            if ([character isEqualToString:self.currentState.matchingCharacter]) {
                //matching character found!
                ROState* startOfMatch=self.currentState;
                //first advance to the next state:
                self.currentState=self.currentState.nextState;
                //check if we reached complete string match:
                if (self.currentState.finality==YES) {
                    return NSMakeRange(i,1);
                }
                //we have to match the substring starting from the next character:
                NSString* substring =[string substringWithRange:NSMakeRange(i+1, string.length-i-1)];
                NSRange substringRange=[self findMatch:substring];
                //if the substring is found, return it:
                if (!NSEqualRanges(substringRange,NSMakeRange(0,0))) {
                    //the range has to be expressed within the original string:
                    substringRange.location=substringRange.location+i;
                    //range is one character longer than the matched substring range:
                    substringRange.length=substringRange.length+1;
                    return substringRange;
                }
                //if the substring is not found, return to the start to process the next character:
                self.currentState=startOfMatch;
            }
            //at character mismatch, return to the initial state:
            //one of these malfunctions in partial recursion (wrong state or wrong recursion level reached!!!)
            else self.currentState=self.initialState;
        }
    }
    //the end is only reached at string mismatch:
    return NSMakeRange(0, 0);
}

-(void)rewind {
    self.currentState=self.initialState;
}

@end
