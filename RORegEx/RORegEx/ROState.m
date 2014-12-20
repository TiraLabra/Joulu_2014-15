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
    self.nextStates = [[NSMutableArray alloc] init];
    return self;
}

@end
