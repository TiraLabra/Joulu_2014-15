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
@end

@implementation RONFA

- (id) initWithRegEx:(NSString *)regEx {
    NSCharacterSet* operators = [NSCharacterSet characterSetWithCharactersInString:@""];
    self.initialState=[[ROState alloc] init];
    self.currentState=self.initialState;
    for (int i=0; i<regEx.length; i++) {
        NSString* character=[regEx substringWithRange:NSMakeRange(i, i+1)];
        //Here, we have the default behavior of matching the character:
        if ([character rangeOfCharacterFromSet:operators].location != NSNotFound) {
            self.currentState.matchingCharacter = character;
            //we do not need to explicitly list pointers to the successive states, as the pointers form a tree starting from the initial state:
            self.currentState.nextState=[[ROState alloc] init];
            self.currentState=self.currentState.nextState;
        }
        if (i==regEx.length) self.currentState.finality=YES;
    }
    //at the end, return the automaton to the beginning for running:
    self.currentState=self.initialState;
    return self;
}

@end
