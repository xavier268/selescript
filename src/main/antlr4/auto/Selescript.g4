
grammar Selescript;

// General considerations :
// Semi colon (;) are mandatory after each assigment or action.
// They are NOT expected after a closing parenthesis : }

// Top level element
unit 
    :   statement *  EOF
    ;

statement 
    :   'go'    '{' statement * '}'             # go0   // one-time loop
    |   'go'    stringval '{' statement * '}'   # go    // loop for locators
    |   ( ID | BIID ) '=' stringval ';'         # assign
    |   'timer' constantnumber ';'              # timer  // Time in millis
    |   'emit' param ? ( ',' param )* ';'       # emit   // json format by default
    |   'print' stringval ';'                   # print  //plain text
    |   'click' stringval ';'                   # click  // on the specified xpath
    |   'clickw' stringval ';'                  # clickw // click and wait for page to start reloading
    |   stringval ';'                           # check  // break/continue on null value
    ;

param
    :   ( ID ':' ) ? stringval 
    ;

stringval 
    
    :   constantstring                          # sstring
    |   stringval '==' stringval                # eq    // Not null means true
    |   stringval '!=' stringval                # neq
    |   stringval '~' stringval                 # match // true if provided pattern is matched.
    |   stringval '!~' stringval                # nomatch // true if provided pattern does not match.
    |   '(' stringval ')'                       # par
    |   '@' (ID ? ':' )? ( stringval ) ?        # at    // derefence based on current search context
    |   '!' stringval                           # not   // Not null means true
    |   stringval '+' stringval                 # concat
    |   stringval '|' stringval                 # or    // logical or
    |   stringval '&' stringval                 # and   // logical and
    |   NULL                                    # null
    |   BIID                                    # biid
    |   ID                                      # id
    ;

constantstring
    :  '(' constantstring ')'                   # cspar
    |  '!' constantstring                       # csnot
    |  constantstring '+' constantstring        # csplus
    |  constantnumber                           # csc
    |  STRING                                   # csstring
    ; 


constantnumber

    :  '(' constantnumber ')'                    # cpar
    |  constantnumber '*' constantnumber         # ctimes
    |  constantnumber '/' constantnumber         # cdiv
    |  constantnumber '+' constantnumber         # cplus
    |  constantnumber '-' constantnumber         # cminus
    |  '-' constantnumber                        # uminus
    |  NUMBER                                    # cnumber
    ;





// ------ Lexer ------------

// Keywords should come first ...
NULL : 'null' ;

// There is no escape for double quote inside string, use single quotes !
// Note the greedy *? 
STRING : '"' .* ? '"' ; 

// Numbers are not signed integer ( but unary minus exists )
NUMBER  : NONZERODIGIT DIGIT* ;

// Acceptable ID starts with a letter
ID : LETTER DIGITORLETTER * ;

// Built-in Ids start with a single $ sign
BIID : '$' ID ;

fragment DIGITORLETTER : DIGIT | LETTER ;
fragment DIGIT : [0-9] ;
fragment NONZERODIGIT : [1-9] ;
fragment LETTER : [a-zA-Z_] ;

// Skip white spaces
WS :  [ \t\r\n\u000C]+ -> skip ;

// Note the greedy *? ...
COMMENT : '/*' .*?  '*/' -> skip ;

LCOMMENT : '//' .*? [\r\n] -> skip ;
