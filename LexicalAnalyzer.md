# Introduction #

The lexical analyzer scans the input C files and generates the tokens for the syntax analyzer.



# Details #

### Syntax ###
  * Comments are between /`*` and `*`/. They do not overlap.
  * White spaces are not allowed in the middle of a keyword, and are ignored elsewhere.
  * A keyword is a sequence of letters.
  * A constant integer _nb\_integer_ is a sequence of numbers.
  * The comma lexeme "," serves as a separator of identificators.
  * The semi-colon ";" serves as an instruction terminator.

### Operators ###

  * The relational operators are ==, !=, <=, >=, <, >.
  * The logical operators || (or), && (and), ! (not).
  * The affectation operator is =.
  * The arithmetic operators are +, -, `*`, / and %.
  * The unary minus is also -.
  * Parentheses (), hooks `[]`, brackets {} are lexemes used as in C.