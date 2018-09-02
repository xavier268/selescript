/***************************************************************/
/* demoArithmetic - basic test file string and arithmethic     */
/***************************************************************/


print "This was written using print"  ;

emit comment:"'emit' can also be used to print, but is far less readeable in json format" ;

print 1 + 2 * 3 ;
print "1 + 2 * 3 = " +  1 + 2 * 3 ; /* numbers will be added correctly, then concateneted */
print "1 + 2 * 3 = " +  (1 + 2 * 3) ; /* the right/safer way to do it */
print "(1 + 2) * 3 = " + ( 1 + 2) * 3  ;

print "first " + "second" ;



@ "this-xpath-does-not-exist" ;

print  "This message should never appear" ;



