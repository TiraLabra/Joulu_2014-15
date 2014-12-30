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

- (void)testTwoMatches {
    NSString* pattern=@"a";
    NSRange expectedRange[2]={NSMakeRange(0,1),NSMakeRange(1, 1)};
    RORegEx* regEx= [[RORegEx alloc] initWith:pattern];
    //construct the ugly expected result object:
    NSTextCheckingResult* expected = [NSTextCheckingResult regularExpressionCheckingResultWithRanges:expectedRange count:2 regularExpression:[NSRegularExpression regularExpressionWithPattern:pattern options:0 error:nil]];
    NSTextCheckingResult* result = [regEx checkString:@"aa"];
    XCTAssert([result numberOfRanges]==[expected numberOfRanges], @"Correct number of returned matches");
    XCTAssert(NSEqualRanges(result.range,expected.range), @"Correct first match");
    XCTAssert(NSEqualRanges([result rangeAtIndex:1],[expected rangeAtIndex:1]), @"Correct second match");
}

- (void)testMultipleStringMatches {
    NSString* pattern=@"laa";
    NSRange expectedRange[3]={NSMakeRange(1,3),NSMakeRange(5, 3),NSMakeRange(9,3)};
    RORegEx* regEx= [[RORegEx alloc] initWith:pattern];
    //construct the ugly expected result object:
    NSTextCheckingResult* expected = [NSTextCheckingResult regularExpressionCheckingResultWithRanges:expectedRange count:3 regularExpression:[NSRegularExpression regularExpressionWithPattern:pattern options:0 error:nil]];
    NSTextCheckingResult* result = [regEx checkString:@"Blaablaablaablah"];
    XCTAssert([result numberOfRanges]==[expected numberOfRanges], @"Correct number of returned matches");
    XCTAssert(NSEqualRanges(result.range,expected.range), @"Correct first match");
    XCTAssert(NSEqualRanges([result rangeAtIndex:1],[expected rangeAtIndex:1]), @"Correct second match");
    XCTAssert(NSEqualRanges([result rangeAtIndex:2],[expected rangeAtIndex:2]), @"Correct third match");
}

- (void)testComplexString {
    NSString* pattern=@"lal";
    NSRange expectedRange[3]={NSMakeRange(1,3),NSMakeRange(9,3),NSMakeRange(26,3)};
    RORegEx* regEx= [[RORegEx alloc] initWith:pattern];
    //construct the ugly expected result object:
    NSTextCheckingResult* expected = [NSTextCheckingResult regularExpressionCheckingResultWithRanges:expectedRange count:3 regularExpression:[NSRegularExpression regularExpressionWithPattern:pattern options:0 error:nil]];
    NSTextCheckingResult* result = [regEx checkString:@"Blalalaablalallablllaaaaaalalabaalaah"];
    XCTAssert([result numberOfRanges]==[expected numberOfRanges], @"Correct number of returned matches");
    XCTAssert(NSEqualRanges(result.range,expected.range), @"Correct first match");
    XCTAssert(NSEqualRanges([result rangeAtIndex:1],[expected rangeAtIndex:1]), @"Correct second match");
    
    NSLog(@"Second match %@", NSStringFromRange([result rangeAtIndex:1]));
    XCTAssert(NSEqualRanges([result rangeAtIndex:2],[expected rangeAtIndex:2]), @"Correct third match");
    
    NSLog(@"Third match %@", NSStringFromRange([result rangeAtIndex:2]));
}


- (void)testPerformanceExample {
    // This is an example of a performance test case.
    [self measureBlock:^{
        // Put the code you want to measure the time of here.
    }];
}

@end
