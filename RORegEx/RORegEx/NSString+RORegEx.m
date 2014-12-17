//
//  NSString+RORegEx.m
//  RORegEx
//
//  Created by Riku Oja on 17.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import "NSString+RORegEx.h"
#import "RORegEx.h"

@implementation NSString (RORegEx)

-(NSTextCheckingResult*)match: (NSString*)regEx {
    return [[[RORegEx alloc] initWith:regEx] checkString:self];
}

@end
