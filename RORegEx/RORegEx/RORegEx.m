//
//  RORegEx.m
//  RORegEx
//
//  Created by Riku Oja on 16.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import "RORegEx.h"
#import "RONFA.h"

@interface RORegEx ()

/**
 *  The nondeterministic finite automaton used in the implementation of the interpreter.
 */
@property (strong, nonatomic)RONFA* NFA;
@end

@implementation RORegEx

-(id) initWith:(NSString *)regEx {
    self = [super init];
    if (self == nil) return self;
    //Here we implement the class-specific initialization:
    self.NFA=[[RONFA alloc]initWithRegEx:regEx];
    return self;
}

-(NSTextCheckingResult*) checkString:(NSString *)input {
    NSTextCheckingResult* result = [[NSTextCheckingResult init] alloc];
    //here we construct the result from the automaton:
    NSRange range = [self.NFA findMatch:input];
    return result;
}

@end
