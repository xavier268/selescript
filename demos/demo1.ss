/*******************************************************/
/* Test 1 - basic test file to access google home page */
/********************************************************/


print "Test 1 - accessing a google home page (this was written using print)"  ;

emit "'emit' can also be used to print, but is far less readeable ..." ;


/* let's do some simple math ..."
print "1 + 2 * 3 = " + ( 1 + 2 * 3 ) ;

/* go to page */
$url = "http://www.google.com" ;

/* get page text */
text = @ ".//body" ;

/* display url and text */
emit     url:$url,     text:text ;

emit status:"OK" ;

@ "this-xpath-does-not-exist" ;

print  "This message should never appear" ;



