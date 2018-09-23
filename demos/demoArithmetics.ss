/***************************************************************/
/* demoArithmetic - basic test file string and arithmethic     */
/***************************************************************/

// This is a single line comment
/* this is 
 
   a multiline comment */

print "This was written using print"  ;

print $java ;
print $os ;


print ;
print "Printing some null values : null, then null + null";
print null ;
print null + null ;

print ;
print "Printing null concatenated with empty string : null + '' then '' + null" ; 
print null + "";
print "" + null ;

print "Warning : do not confuse 1 + 2 = 12 and 1 ++ 2 = 3 !!" ;
print 1 + 2     ; // Concatenation
print 1 ++ 2    ; // Number addition

print ;
print "Print some basic calculus, testing precedence" ;
print "1 ++ 2 * 3 = 7 ? "  ;    print   1 ++ 2 * 3 ;   /* numbers will be added correctly, then concateneted */
print "(1 ++ 2 * 3) = 7 ? "  ;  print   (1 ++ 2 * 3) ; /* the right/safer way to do it */
print "(1 ++ 2) * 3 = 9 ? " ;   print  ( 1 ++ 2) * 3  ;

print ;
print "Testing precedence between operators" ;
print "'1' + 2 ~ 2 => true ?" ;         print ('1' + 2 ~ 2 ) ;
print "1 + 2 ~ 3 => null ?" ;           print  (1  +2 ~ 3 ) ;
print "1 + (2 ~ 3) => 1 ?" ;            print (1 + (2 ~ 3) ) ;
print "1 + null | 2 => 1 ?" ;           print (1 + null | 2 ) ;
print "'bla' ~ 'a' => true ?" ;         print ('bla' ~ 'a' ) ;
print "'bla' ~ 'a' == 'b' => null ?" ;  print ('bla' ~ 'a' == 'b') ;
print "'bla' ~('a' == 'b') => true ?";  print ('bla' ~ ('a' == 'b')) ;
print "('bla'~'a') == 'b' => null ?" ;  print (('bla' ~ 'a' ) == 'b') ;

print ;
print "Concat 1 " + "string" ;
print "Concat 2 " + null + "string" ;
print "Concat 3 " + 'string' ;


print  "End of " + $source ;



