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

@interface ROState : NSObject <NSCopying>
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
/**
 *  Provides a copy of the ROstate object, as per NSCopying protocol.
 *
 *  @param The zone identifies an area of memory from which to allocate for the new instance. If zone is NULL, the new instance is allocated from the default zone, which is returned from the function NSDefaultMallocZone.
 *
 *  @return An ROState object with same properties as the original.
 */
-(id)copyWithZone:(NSZone *) zone;
/**
 *  Copies the properties of an existing state to this ROState.
 *
 *  @param state The ROState object to be copied from.
 */
 -(void)copyFromState:(ROState *) state;
@end

