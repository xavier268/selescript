
grammar Selescript;

// Top level element
unit 
    :  'unit' ID '{' statement ( ';' statement )* '}'
    ;

// A statement is a logical part of the program.
statement
    :  EXPECT expression 
    |  REJECT expression
    |  PAGE ID '{' statement ( ';' statement ) * '}'
    |  ID '{' statement ( ';' statement )* '}'
    |  CLIC ID
    |  expression
    |  // empty statements are ok !
    ;

// An expression has a value ...
expression
    : (ID|BIID) '=' expression
    | '!' expression
    | expression '+' expression
    | expression '<' expression
    | expression '>' expression
    | expression '==' expression
    | atom
    ;


atom 
    : NUMBER
    | STRING
    | XPATH
    | ID
    ;


// ------ Lexer ------------

EXPECT  : 'expect' ;
REJECT  : 'reject' ;
PAGE    : 'page' ;
ELEMENT : 'element' ; 
EMIT    : 'emit' ;
CLIC    : 'clic' ;


// There is no way to escape the double quote inside string
// Note the greedy ? ...
STRING : '"' .*? '"' ; 

// XPath
// Note the greedy ? ...
XPATH : 'x"' .*? '"' ;

// Numbers are signed integer 
NUMBER  : [+-]?[0-9]+ ;

// Acceptable ID starts with a letter
ID : [a-zA-Z][a-zA-Z0-9]* ;

// Built-in start with a $ sign
BIID : '$' ID ;

WS :  [ \t\r\n\u000C]+ -> skip ;

// Note the greedy ? ...
COMMENT : '/*' .*?  '*/' -> skip ;