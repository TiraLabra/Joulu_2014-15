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
/**
 *  The current state of the automaton, used in both constructing and running the automaton.
 */
@property (strong, nonatomic)ROState* currentState;
/**
 *  The predefined regexp operators that are not treated as literals:
 */
@property NSCharacterSet* operators;
@end

@implementation RONFA

- (id) initWithRegEx:(NSString *)regEx {
    self.operators = [NSCharacterSet characterSetWithCharactersInString:@""];
    self.initialState=[[ROState alloc] init];
    self.currentState=self.initialState;
    for (int i=0; i<regEx.length; i++) {
        NSString* character=[regEx substringWithRange:NSMakeRange(i, 1)];
        //Here, we have the default behavior of matching the character:
        if ([character rangeOfCharacterFromSet:self.operators].location == NSNotFound) {
            self.currentState.matchingCharacter = character;
            //we do not need to explicitly create pointers to the successive states, as the pointers form a tree starting from the initial state:
            [self.currentState.nextStates addObject:[[ROState alloc] init]];
            //we move on to the state matched by the character:
            self.currentState=self.currentState.nextStates.lastObject;
        }
    }
    //after the loop, the ending state indicates we have a pattern:
    self.currentState.finality=YES;
    //after initialization, return the automaton to the beginning for running:
    self.currentState=self.initialState;
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
            self.currentState = [self.currentState getNextState:character];
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
    //rewind the automaton (or not!):
    //self.currentState=self.initialState;
    if (started) return NSMakeRange(startIndex, endIndex-startIndex);
    else return NSMakeRange(0, 0);
}

@end
