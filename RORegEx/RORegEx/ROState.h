//
//  ROState.h
//  RORegEx
//
//  Created by Riku Oja on 17.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 *  Represents a single state in a finite automaton.
 */

@interface ROState : NSObject
/**
 *  @brief The array containing all the possible next states in a nondeterministic automaton. The ordering of the states depends on the automaton.
 */
@property (strong, nonatomic) NSMutableArray* nextStates;
/**
 *  @brief The break condition of the automaton, indicating a pattern match. Default is NO.
 */
@property (nonatomic) BOOL finality;
/**
 *  @brief The matching character of the state, or nil if the state doesn't allow matching a character.
 */
@property (strong, nonatomic) NSString* matchingCharacter;
@end
