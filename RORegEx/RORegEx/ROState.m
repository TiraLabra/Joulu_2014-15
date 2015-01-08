//
//  ROState.m
//  RORegEx
//
//  Created by Riku Oja on 17.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import "ROState.h"

@interface ROState ()
@end

@implementation ROState

-(id) init {
    self = [super init];
    if (self == nil) return self;
    //Here we implement the class-specific initialization:
    //at the construction of the automaton, none of the states are the result of a match:
    self.startIndex=[NSNumber numberWithUnsignedLong:NSUIntegerMax];
    self.nextStartIndex=[NSNumber numberWithUnsignedLong:NSUIntegerMax];
    return self;
}

-(id) copyWithZone:(NSZone *)zone {
    ROState* copy=[[ROState allocWithZone:zone]init];
    copy.matchingCharacter=self.matchingCharacter;
    copy.nextState=self.nextState;
    copy.alternateState=self.alternateState;
    copy.startIndex=self.startIndex;
    copy.nextStartIndex=self.nextStartIndex;
    copy.finality=self.finality;
    return copy;
}

-(void) copyFromState:(ROState *)state {
    self.matchingCharacter=state.matchingCharacter;
    self.nextState=state.nextState;
    self.alternateState=state.alternateState;
    self.startIndex=state.startIndex;
    self.nextStartIndex=state.nextStartIndex;
    self.finality=state.finality;
}

@end
