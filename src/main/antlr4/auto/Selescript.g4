
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
    |   'emit' param ? ( ',' param )* ';'       # emit   // json format by default
    |   'db' param ? ( ',' param )* ';'         # db   // send to mongodb
    |   'print' stringval ? ';'                 # print  //plain text
    |   'click' stringval ';'                   # click  // on the specified xpath
    |   'clickw' stringval ';'                  # clickw // click and wait for page to start reloading
    |   stringval ';'                           # check  // break/continue on null value
    ;

param
    :   ( ID ':' ) ? stringval 
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
    |   '@' (ID ? ':' )? ( stringval ) ?        # at    // derefence based on current search context
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

// There is no escape, but you can use single or double quotes strings.
// Note the reluctant *? 
STRING  :  '"'  .* ? '"' 
        |  '\'' .* ? '\'' 
        ; 

// Numbers are not signed integer ( but unary minus exists )
NUMBER  : '0' | NONZERODIGIT DIGIT* ;

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
