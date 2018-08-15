
grammar Selescript;

// General considerations :
// Semi colon (;) are mandatory after each assigment or action.
// They are not expected after a closing parenthesis (})

// Top level element
unit 
    :   'unit' ID '{' ( bloc )* '}' EOF
    ;

bloc 
    :   ( assign ';' ) +                        # bassign
    |   'when' '{' (  expression ';' ) * '}'    # bwhen
    |   'do' locator '{' ( bloc ) * '}'         # bdo
    |   (action ';') +                          # baction
    ;

action 
    : 'clic' expression
    | 'emit' expression ( ',' expression ) *
    | 'log' expression
    ;

assign
    :  (BIID | ID ) '='  expression
    ;

// Expression have an object value, of various types.
// Conditions are expressions, 
// They evaluate to true if not null, not zero, not empty string, not empty list.
expression
    : '(' expression ')'
    | '!' expression
    | expression '==' expression
    | expression '<' expression
    | expression '>' expression
    | expression '<=' expression
    | expression '>=' expression
    | expression '!=' expression
    | expression 'and' expression
    | expression 'or' expression
    | expression '+' expression
    | expression '-' expression
    | expression '*' expression
    | expression '%' expression
    | atom
    ;



locator : ID | BIID | XPATH ;

atom 
    : NUMBER        # anumber
    | STRING        # astring
    | XPATH         # axpath
    | ID            # aid
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

