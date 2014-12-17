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
 *  Initializes the automaton.
 *
 *  @param regEx The regular expression required to construct the automaton.
 *
 *  @return Returns an NFA.
 */
- (id)initWithRegEx:(NSString *)regEx;

@end
