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
 *  The starting state of the automaton.
 */
@property (strong, nonatomic)ROState* initialState;
//Pointers to all the other states are contained in initialState.
/**
 *  The state from which the automaton arrived to the current state. The same as currentState if we had an epsilon transition.
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
 *  Contains all the states of the automaton that indicate a pattern match, i.e. those that have finality=YES.
 */
//Note that the array contains strong references and the array must be strong. Hence, retain cycles of states pointing back to the NFA must be avoided.
@property (strong, nonatomic)NSMutableArray* finalStates;
@end

@implementation RONFA

- (id) initWithRegEx:(NSString *)regEx {
    self.operators = [NSCharacterSet characterSetWithCharactersInString:@""];
    self.initialState=[[ROState alloc] init];
    self.currentState=self.initialState;
    //in the beginning, we have epsilon transition so the previous state is the current one:
    self.previousState=self.currentState;
    for (int i=0; i<regEx.length; i++) {
        NSString* character=[regEx substringWithRange:NSMakeRange(i, 1)];
        //Here, we have the default behavior of matching the character:
        if ([character rangeOfCharacterFromSet:self.operators].location == NSNotFound) {
            self.currentState.matchingCharacter = character;
            //we do not need to explicitly create pointers to the successive states, as the pointers form a tree starting from initialState
            //if the character doesn't match, we must go back to the previous state:
            [self.currentState.nextStates addObject:self.previousState];
            //an alternate nondeterministic outcome for non-match:
            [self.currentState.nextStates addObject:self.currentState];
            //otherwise, we move to a new state:
            [self.currentState.nextStates addObject:[[ROState alloc] init]];
            //save the current state as the previous one:
            self.previousState=self.currentState;
            //we move on to the state matched by the character:
            self.currentState=self.currentState.nextStates[2];
        }
    }
    //after the loop, the ending state indicates that we have a pattern:
    self.currentState.finality=YES;
    [self.finalStates addObject:self.currentState];
    //after initialization, return the automaton to the beginning for running:
    [self rewind];
    return self;
}

-(NSRange)findMatch:(NSString *)string {
    NSUInteger startIndex=0;
    NSUInteger endIndex=0;
    BOOL started = NO;
    for (int i=0; i<string.length; i++) {
        NSString* character=[string substringWithRange:NSMakeRange(i, 1)];
        //Here, we have the default behavior of matching the character:
        if ([character rangeOfCharacterFromSet:self.operators].location == NSNotFound) {
            //because of nondeterminism, at non-match we have several options:
            if (character != self.currentState.matchingCharacter) {
                self.currentState = self.currentState.nextStates.firstObject;
                //implement nondeterminism by forking:
            }
            //at match we only have one option:
            else self.currentState = self.currentState.nextStates.lastObject;
            //if we had a first match, save the start index:
            if (!started && self.currentState != self.initialState) {
                started=YES;
                startIndex=i;
            }
            //check if we reached the end:
            if (self.currentState.finality==YES) {
                endIndex=i+1;
                break;
            }
        }
    }
    if (endIndex!=0) return NSMakeRange(startIndex, endIndex-startIndex);
    else return NSMakeRange(0, 0);
}

-(void)rewind {
    self.currentState=self.initialState;
}

@end
