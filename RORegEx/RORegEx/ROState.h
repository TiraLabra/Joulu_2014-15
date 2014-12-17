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
 *  @brief The default next state for the automaton.
 */
@property (strong, nonatomic) ROState* nextState;
/**
 *  @brief The alternative next state for the automaton, for NFA where we can have options where to move. This property may be nil, if only one successive state is required.
 */
@property (strong, nonatomic) ROState* alternateState;
/**
 *  @brief The break condition of the automaton, indicating a pattern match. Default is NO.
 */
@property (nonatomic) BOOL finality;
/**
 *  @brief The matching character of the state, or nil if the state doesn't allow matching a character.
 */
@property (strong, nonatomic) NSString* matchingCharacter;
/**
 *  Returns the next state, depending on the input character.
 *
 *  @param character The character to be processed.
 *
 *  @return The next state.
 */
-(ROState *)getNextState:(NSString *)character;

@end
