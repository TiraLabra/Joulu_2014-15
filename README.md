RORegEx
=============

An Objective-C library that implements simple fast (polynomial instead of exponential scaling) regular expression pattern matching using a nondeterministic finite automaton.

- Supports several matches in a single string; returns results as an NSTextMatchingResult object.
- Currently provides greedy but minimal matching; returns the earliest and smallest match to the pattern.
- Currently only implements parentheses, ., *, | and ? operators.

# Installation

Including NSString+RORegEx.h allows you to send a match:(NSString*) regEx command directly to an NSString object.

# References

http://en.wikipedia.org/wiki/Regular_expression
http://en.wikipedia.org/wiki/Nondeterministic_finite_automaton
http://swtch.com/~rsc/regexp/regexp1.html