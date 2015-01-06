//
//  ROState.h
//  RORegEx
//
//  Created by Riku Oja on 17.12.2014.
//  Copyright (c) 2014 Riku Oja. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 *  A dumb class representing a single state in any finite automaton.
 */

@interface ROState : NSObject
/**
 *  The next state in the tree.
 */
@property (strong, nonatomic) ROState* nextState;
/**
 *  An alternate next state in the tree, if the tree forks. May be nil.
 */
@property (strong, nonatomic) ROState* alternateState;
/**
 *  @brief The break condition of the automaton, indicating a pattern match. Default is NO.
 */
@property (nonatomic) BOOL finality;
/**
 *  @brief The matching character of the state. matchingCharacter=nil represents any character match, or a fork state.
 *  @discussion If alternateState=nil, we have "any character" match, i.e. we match and move to nextState. If alternateState!=nil, we have a fork in the tree, i.e. we don't match and move to nextState and alternateState.
 */
@property (strong, nonatomic) NSString* matchingCharacter;
/**
 *  @brief Used by a finite automaton to save the index of the letter where matching started on the previous step.
 */
@property (strong,nonatomic) NSNumber* startIndex;
/**
 *  @brief Used by a finite automaton to save the next starting index until the end of step.
 */
@property (strong,nonatomic) NSNumber* nextStartIndex;
@end
