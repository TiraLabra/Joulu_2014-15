//
//  RORegEx.m
//  RORegEx
//
//  Created by Riku Oja on 16.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import "RORegEx.h"
#import "RONFA.h"

/*!
 The internal implementation of the RegEx interpreter. The methods and properties here are only used inside the RORegEx class.
 */

@interface RORegEx ()

/*!
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
    //here we construct the result from the automaton:
    //either we have to hard code the possible amount of matches or do the memory management manually:
    NSRange ranges[100];
    //this is the starting location for repetitive searcing:
    NSUInteger start = 0;
    NSString* originalInput = input.copy;
    int i=0;
    while (true) {
        //repeat looking for the pattern until we are at the end of the string:
        if (start > originalInput.length) break;
        input=[originalInput substringFromIndex:start];
        ranges[i] = [self.NFA findMatch:input];
        //if there is no match, quit:
        if (NSEqualRanges(ranges[i],NSMakeRange(0, 0))) break;
        //move the range to the right location:
        ranges[i].location=ranges[i].location+start;
        //mark the next starting location:
        start=ranges[i].location+ranges[i].length;
        i++;
    }
    //construct the ugly result object:
    return [NSTextCheckingResult regularExpressionCheckingResultWithRanges:ranges count:i regularExpression:[NSRegularExpression regularExpressionWithPattern:input options:0 error:nil]];
}

@end
