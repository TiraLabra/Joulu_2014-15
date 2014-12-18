//
//  RORegExTest.m
//  RORegEx
//
//  Created by Riku Oja on 18.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import <XCTest/XCTest.h>
#import "RORegEx.h"

@interface RORegExTest : XCTestCase

@end

@implementation RORegExTest

- (void)setUp {
    [super setUp];
    // Put setup code here. This method is called before the invocation of each test method in the class.
}

- (void)tearDown {
    // Put teardown code here. This method is called after the invocation of each test method in the class.
    [super tearDown];
}

- (void)testExample {
    // This is an example of a functional test case.
    XCTAssert(YES, @"Pass");
}

- (void)testInitRegEx {
    NSString* pattern=@"a";
    NSRange expectedRange=NSMakeRange(0,1);
    RORegEx* regEx= [[RORegEx alloc] initWith:pattern];
    //construct the ugly expected result object:
    NSTextCheckingResult* expected = [NSTextCheckingResult regularExpressionCheckingResultWithRanges:&expectedRange count:1 regularExpression:[NSRegularExpression regularExpressionWithPattern:pattern options:0 error:nil]];
    XCTAssert(NSEqualRanges([regEx checkString:@"a"].range,expected.range), @"Matches a single letter");
}


- (void)testPerformanceExample {
    // This is an example of a performance test case.
    [self measureBlock:^{
        // Put the code you want to measure the time of here.
    }];
}

@end
