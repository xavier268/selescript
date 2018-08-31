/*******************************************************/
/* Test 1 - basic test file to access google home page */
/********************************************************/

/* print name of file */
emit "Test 1 - accessing a google home page "  ;

/* go to page */
$url = "http://www.google.com" ;

/* get page text */
text = @ ".//body" ;

/* display url and text */
emit     url:$url,     text:text ;

emit status:"OK" ;

! @ "this-xpath-does-not-exist" ;

emit "This message should never appear" ;



