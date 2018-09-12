/***************************************************************/
/* demoArithmetic - basic test file string and arithmethic     */
/***************************************************************/


print "This was written using print"  ;

emit comment:"'emit' can also be used to print, but is far less readeable in json format" ;
print $JAVA ;
print $OS ;


print ;
print "Printing some null values : null, then null + null";
print null ;
print null + null ;

print ;
print "Printing null concatenated with empty string : null + '' then '' + null" ; 
print null + "";
print "" + null ;

print ;
print "Print some basic calculus, testing precedence" ;
print "1 + 2 * 3 = 7 ? " +  1 + 2 * 3 ;   /* numbers will be added correctly, then concateneted */
print "(1 + 2 * 3) = 7 ? " +  (1 + 2 * 3) ; /* the right/safer way to do it */
print "(1 + 2) * 3 = 9 ? " + ( 1 + 2) * 3  ;

print ;
print "Testing precedence between operators" ;
print "'1' + 2 ~ 2 => 2 ?" + ('1' + 2 ~ 2 ) ;
print "1 + 2 ~ 3 => 3 ?" + (1 + 2 ~ 3 ) ;
print "1 + null | 2 => 1 ?" + (1 + null | 2 ) ;
print "'bla' ~ 'a' != > a ?" ; print ('bla' ~ 'a' ) ;
print "'bla' ~ 'a' == 'b' != > null ?" ; print ('bla' ~ 'a' == 'b') ;
print "'bla' ~ ( 'a' == 'b') != > bla ?" ; print ('bla' ~ ('a' == 'b')) ;
print "('bla' ~ 'a') == 'b' != > null ?" ; print (('bla' ~ 'a' ) == 'b') ;



print ;
print "Concaneted  " + "string" ;
print "Concaneted  " + null + "string" ;
print "Concaneted  " + 'string' ;


print;
print "A non existing xpath will fail the condition check and quit ..." ;
@ "this-xpath-does-not-exist" ;

print  "This message should never appear !!" ;



