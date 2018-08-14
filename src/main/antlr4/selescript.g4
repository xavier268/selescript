
grammar selescript;

program 
    :  statement ( statement )*
;

statement 
    : expression ';'
    ;

expression 
    : expression '*' expression     # etimes
    | expression '+' expression     # eplus
    | '(' expression ')'            # epar
    | ID '(' expressionList ')'     # efunction
    | atom                          # eatom
    ;

expressionList
    : ( expression ',' )*
    ;


atom 
    : NUMBER
    | ID
    ;

// Number are integer or decimal

NUMBER 
    : [0-9]+('.'[0-9]*)?
    | '.'[0-9]+
    ;

ID : [a-zA-Z][a-zA-Z0-9]+ ;

WS : [ \r\n\t] + -> skip ;