//
//  RORegEx.h
//  RORegEx
//
//  Created by Riku Oja on 16.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 Represents an interpreter with a specific regular expression.
 */

@interface RORegEx : NSObject
/**
 *  Initializes the regular expression interpreter.
 *
 *  @param regEx The required regular expression.
 *
 *  @return A regular expression interpreter with the given expression.
 */
-(id) initWith:(NSString *)regEx;
/**
 *  Matches the input string to the regular expression.
 *
 *  @param input The string to be processed.
 *
 *  @return An NSTextCheckingResult object containing the parts of input that match the regular expression.
 */
-(NSTextCheckingResult*) checkString:(NSString *)input;

@end
