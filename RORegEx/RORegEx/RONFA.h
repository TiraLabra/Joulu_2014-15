//
//  RONFA.h
//  RORegEx
//
//  Created by Riku Oja on 17.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ROState.h"

/**
 *  Represents a general nondeterministic finite automaton running in parallel, i.e. in several states at each cycle. This ensures linear running time with respect to input length.
 */

@interface RONFA : NSObject
/**
 *  Initializes an automaton and sets it in the initial state.
 *
 *  @param regEx The regular expression required to construct the automaton.
 *
 *  @return Returns an NFA.
 */
- (id)initWithRegEx:(NSString *)regEx;
/**
 *  Initializes an automaton if its desired initial state already exists (e.g. in another automaton).
 *
 *  @param state The state to be set as the initial state of the automaton.
 *  @param regEx The regular expression required to construct the automaton.
 *
 *  @return Returns an NFA.
 */
-(id) initWithState:(ROState *)state withRegEx:(NSString *)regEx;
/**
 *  Returns the next (minimal) range in the NSString that corresponds to the expression of the NFA, or a range of (0,0) if there is no match.
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
