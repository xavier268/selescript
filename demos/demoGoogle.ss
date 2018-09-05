/** Navigating the google search page **/

$url = "http://www.google.fr" ;

print "Opened page : " + $url ;

print "title : " + $title ;


go ".//body" {
    print "Entering body loop" ;
    print "Getting the text of the current element" ;
    print @ ;    
    go ".//div" {        
        print "Entering the div loop" ;
        print "time = " + $millis ;
        print "Getting the text of the current element, filtering on existing text" ;
        @ == "" ; /* filter on empty text */
        print @ ;
        }
}
print "done" ;
