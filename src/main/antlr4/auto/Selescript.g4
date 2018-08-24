
grammar Selescript;

// General considerations :
// Semi colon (;) are mandatory after each assigment or action.
// They are NOT expected after a closing parenthesis : }

// Top level element
unit 
    :   statement *  EOF
    ;

statement 
    :   'go'    '{' statement * '}'             # go0   // infinite loop
    |   'go'    stringval '{' statement * '}'   # go    // loop for locators
    |   ( ID | BIID ) '=' stringval ';'         # assign
    |   'emit' emitparam ? ( ',' emitparam ) ';'# emit
    |   stringval ';'                           # check // break/continue on null value
    ;

emitparam
    :   ( ID ':' ) ? stringval 
    ;

stringval 
    :   NUMBER                                  # number
    |   STRING                                  # string
    |   BIID                                    # biid
    |   ID                                      # id
    |   '@' stringval                           # at    // derefence based on current search context
    |   '!' stringval                           # not   // Not null means true
    |   stringval '==' stringval                # eq    // Not null means true
    |   stringval '+' stringval                 # concat
    ;
    






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

