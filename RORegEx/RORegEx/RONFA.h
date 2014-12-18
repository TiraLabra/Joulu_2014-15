//
//  RONFA.h
//  RORegEx
//
//  Created by Riku Oja on 17.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 *  Represents a general nondeterministic finite automaton.
 */

@interface RONFA : NSObject
/**
 *  Initializes the automaton and sets it in the initial state.
 *
 *  @param regEx The regular expression required to construct the automaton.
 *
 *  @return Returns an NFA.
 */
- (id)initWithRegEx:(NSString *)regEx;
/**
 *  Returns the next range in the NSString that corresponds to the expression of the NFA, or a range of (0,0) if there is no match.
 *
 *  @param regEx The regular expression used to construct the NFA.
 *
 *  @return The next range in the string that matches the expression.
 */
-(NSRange)findMatch:(NSString *)string;
/**
 *  Rewinds the automaton to its initial state.
 */
-(void)rewind;

@end
