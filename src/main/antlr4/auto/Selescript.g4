
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
    |    ID   '=' stringval ';'                 # assigni
    |    BIID '=' stringval ';'                 # assignb
    |   'db' param ? ( ',' param )* ';'         # db      // send to mongodb
    |   'print' param ? ( ',' param )* ';'      # print   //plain text
    |   'click' param ? ';'                     # click   // on the specified xpath
    |   'type'  param ? ( ',' param ) ';'       # type    // type text into element
    |   'submit' param ? ';'                    # submit  // submit enclosing form
    |    stringval ';'                          # check   // continue on null value
    ;

param
    :   ( TAG ) ? stringval 
    ;

stringval 
    
    :   constantstring                          # svstring
    |   '!' stringval                           # svnot   // Not null means true
    |   stringval  stringval                    # svconcat    
    |   stringval '~'  stringval ',' stringval  # svreplace // find and replace all, null if not found   
    |   stringval '~' stringval                 # svfind // null if not found
    |   stringval '==' stringval                # sveq    // Not null means true
    |   stringval '!=' stringval                # svneq
    |   '(' stringval ')'                       # svpar
    |   '@' TAG  ?  stringval  ?                # svat    // derefence based on current search context
    |   stringval '|' stringval                 # svor    // logical or
    |   stringval '&' stringval                 # svand   // logical and
    |   BIID                                    # svbiid
    |   ID                                      # svid
    ;

constantstring
    :  '(' constantstring ')'                   # cspar
    |  '!' constantstring                       # csnot
    |  constantstring constantstring            # csconcat
    |  constantnumber                           # csnumber
    |  STRING                                   # csstring
    |  NULL                                     # csnull
    
    ; 


constantnumber

    :  '(' constantnumber ')'                    # cnpar
    |  '-' constantnumber                        # cnuminus
    |  constantnumber '*' constantnumber         # cntimes
    |  constantnumber '/' constantnumber         # cndiv
    |  constantnumber '+' constantnumber         # cnplus
    |  constantnumber '-' constantnumber         # cnminus
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

// Tags start with a semi-colon..
TAG : ':' DIGITORLETTER + ;

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
