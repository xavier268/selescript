
grammar Selescript;

// General considerations :
// Semi colon (;) are mandatory after each assigment or action.
// They are NOT expected after a closing parenthesis : }

// Top level element
unit 
    :   statement *  EOF
    ;

statement 
    :   'go'    '{' statement * '}'         # go0   // infinite loop
    |   'go'    locator '{' statement * '}' # go    // loop for locators
    |   ( ID | BIID ) '=' locator ';'       # assign
    |   'emit'  locator (',' locator) * ';' # emit
    |   locator ';'                         # exist     // test for existence or jump to next
    |   '!' locator ';'                     # nexist    // check for non existence
    |   '~' locator ';'                     # change    // change for state changed
    |   'click' locator (',' locator) * ';' # click // only the first found
    |   'key'   locator (',' locator) * ';' # key // enter input data
    ;


locator :  STRING | BIID | ID ;



// ------ Lexer ------------


// There is no way to escape the double quote inside string
// Note the greedy ? ...
STRING : '"' .*? '"' ; 

// Numbers are signed integer 
NUMBER  : [+-]?[0-9]+ ;

// Acceptable ID starts with a letter
ID : [a-zA-Z][a-zA-Z0-9]* ;

// Built-in Ids start with a single $ sign
BIID : '$' ID ;

// Skip white spaces
WS :  [ \t\r\n\u000C]+ -> skip ;

// Note the greedy ? ...
COMMENT : '/*' .*?  '*/' -> skip ;

