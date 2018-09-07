/** Navigating the google search page **/

$url = "http://www.google.fr" ;

print "Opened page : " + $url ;

print "title : " + $title ;
print "$count = " + $count ;


go ".//body" {
    print "Entering body loop" ;
    print "Getting the text of the current element" ;
    print @ ;    
    print "Body count = " + $count ;   
    print "Now looking for first 20 divs" ;
    go ".//div", c:20 {        
        print "In the div loop" ;
        print "div count = "+$count ;
        print "time = " + $millis ;
        print "Getting the text of the current element, filtering on existing text" ;
        @ == "" ; /* filter on empty text */
        print @ ;
        }
}
print "done" ;
