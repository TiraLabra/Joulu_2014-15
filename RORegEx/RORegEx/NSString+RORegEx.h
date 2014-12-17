//
//  NSString+RORegEx.h
//  RORegEx
//
//  Created by Riku Oja on 17.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSString (RORegEx)
/**
 *  Method for matching the string with a given regular expression.
 *
 *  @param regEx The regular expression.
 *
 *  @return An NSTextCheckingResult object containing the parts of input that match the regular expression.
 */
-(NSTextCheckingResult*)match: (NSString*)regEx;

@end
