
grammar Selescript;

// General considerations :
// Semi colon (;) are mandatory after each assigment or action.
// They are NOT expected after a closing parenthesis : }

// Top level element
unit 
    :   statement *  EOF
    ;

statement 
    :   'go'  param ? ( ',' param )*  '{' statement * '}'    # go   // one-time loop
    |   ( ID | BIID ) '=' stringval ';'         # assign
    |   'emit' param ? ( ',' param )* ';'       # emit    // json format by default
    |   'db' param ? ( ',' param )* ';'         # db      // send to mongodb
    |   'print' stringval ? ';'                 # print   //plain text
    |   'click' stringval ? ';'                 # click   // on the specified xpath
    |   'click' 'w' ':' stringval ? ';'         # clickw  // click and wait for page to start reloading
    |   'sendkeys' param ? ( ',' param ) ';'    # send    // type text into element
    |   'submit' stringval ? ';'                # submit  // submit enclosing form
    |   'submit' 'w' ':' stringval ? ';'        # submitw //and wait for element to disappear
    |    stringval ';'                          # check   // continue on null value
    ;

param
    :   ( TAG ':' ) ? stringval 
    ;

stringval 
    
    :   constantstring                          # sstring
    |   '!' stringval                           # not   // Not null means true
    |   stringval '+' stringval                 # concat    
    |   stringval '~' stringval                 # find // null if not found
    |   stringval '~' stringval ':' stringval   # replace // find and replace all, null if not found   
    |   stringval '==' stringval                # eq    // Not null means true
    |   stringval '!=' stringval                # neq
    |   '(' stringval ')'                       # par
    |   '@' (TAG ? ':' )? ( stringval ) ?        # at    // derefence based on current search context
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

    :  '(' constantnumber ')'                    # cnpar
    |  constantnumber '*' constantnumber         # cntimes
    |  constantnumber '/' constantnumber         # cndiv
    |  constantnumber '+' constantnumber         # cnplus
    |  constantnumber '-' constantnumber         # cnminus
    |  '-' constantnumber                        # cnuminus
    |  NUMBER                                    # cnnumber
    ;





// ------ Lexer ------------

// Keywords should come first ...
NULL : 'null' ;

// There is no escape, but you can use single or double quotes strings.
// Note the reluctant *? 
STRING  :  '"'  .* ? '"' 
        |  '\'' .* ? '\'' 
        ; 

// Numbers are not signed integer ( but unary minus exists )
NUMBER  : '0' | NONZERODIGIT DIGIT* ;

// Tag are currently similar to IDs but that may change.
TAG : : LETTER DIGITORLETTER * ;

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

// Note the reluctant *? ...
COMMENT : '/*' .*?  '*/' -> skip ;

LCOMMENT : '//' .*? [\r\n] -> skip ;
