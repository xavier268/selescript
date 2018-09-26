
grammar Selescript;

// General considerations :
// Semi colon (;) are mandatory after each assigment or action.
// They are NOT expected after a closing parenthesis : }

// Top level element
unit 
    :   statement *  EOF
    ;

statement 
    :   'go'  param ? ( ',' param )*  '{' statement * '}'    # stgo   // one-time loop
    |    ID   '=' stringval ';'                 # stassigni
    |    BIID '=' stringval ';'                 # stassignb
    |   'db' param ? ( ',' param )* ';'         # stdb      // send to mongodb
    |   'print' param ? ( ',' param )* ';'      # stprint   //plain text, possibly to file
    |   'click' param ? ';'                     # stclick   // on the specified xpath
    |   'type'  param ? ( ',' param )* ';'      # sttype    // type text into element
    |   'submit' param ? ';'                    # stsubmit  // submit enclosing form
    |   'shot' param ? ( ',' param )*  ';'      # stshot    // snapshot full page or element
    |    stringval ';'                          # stcheck   // break on null value
    ;

param
    :   ( TAG ) ? stringval 
    ;

stringval 
    
    :   '-' stringval                           # svuminus
    |   stringval '*' stringval                 # svtimes
    |   stringval '/' stringval                 # svdiv
    |   stringval '++' stringval                # svplus
    |   stringval '-'  stringval                # svminus
    |   stringval '~' stringval ',' stringval   # svreplace // find and replace all, null if not found   
    |   stringval '~' stringval                 # svfind // null if not found
    |   stringval '+' stringval                 # svconcat    
    |   '!' stringval                           # svnot   // Not null means true
    |   stringval '==' stringval                # sveq    // Not null means true
    |   stringval '!=' stringval                # svneq
    |   '(' stringval ')'                       # svpar
    |   '@' TAG  ?  stringval  ?                # svat    // derefence based on current search context
    |   stringval '|' stringval                 # svor    // logical or
    |   stringval '&' stringval                 # svand   // logical and
    |   NUMBER                                  # svnumber
    |   BIID                                    # svbiid
    |   ID                                      # svid
    |   NULL                                    # svnull
    |   STRING                                  # svstring
    ;





// ------ Lexer ------------

// Keywords should come first ...
NULL : 'null' ;

// There is no escape, but you can use single or double quotes strings.
// Note the reluctant *? 
STRING  :  '"'  .* ? '"' 
        |  '\'' .* ? '\'' 
        ; 


// Tags end  with a semi-colon..
TAG :  DIGITORLETTER  (DIGITORLETTER | [&/@\-*.])* ':' ;

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
