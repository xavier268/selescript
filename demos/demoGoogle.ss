/** Navigating the google search page **/

$url = "http://www.google.fr" ;

print "Opened page : " + $url ;



go ".//body" {
    print "Entering body loop" ;
    print "Getting the text of the current element" ;
    print @ ;
    count = ".";
    go ".//div" {
        count =  "." + count;
        print "Entering the div loop" ;
        print "count = " + count ;
        print "Getting the text of the current element, filtering on existing text" ;
        @ == "" ; /* filter on empty text */
        print @ ;
        }
}
print "done" ;
