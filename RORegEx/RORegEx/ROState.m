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

-(ROState *)getNextState:(NSString *)character {
    //The default implementation is to match the given character:
    if ([character isEqualToString:self.matchingCharacter]) return self.nextState;
    else return self;
}

@end
