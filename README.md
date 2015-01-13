RORegEx
=============

An Objective-C library that implements simple superlinear (but not exponential) regular expression pattern matching using a nondeterministic finite automaton.

- Supports several matches in a single string; returns results as an NSTextMatchingResult object.
- Currently provides greedy but minimal matching; returns the earliest and smallest match to the pattern.
- Currently only implements parentheses, ., *, | and ? operators.

http://en.wikipedia.org/wiki/Regular_expression
http://en.wikipedia.org/wiki/Nondeterministic_finite_automaton
http://swtch.com/~rsc/regexp/regexp1.html