
$url = "https://www.google.com";
print "Now on page " + $url ;

print;
print "Starting a loop for 5 times that should abort during the first loop";

go  c:5 {
    print "Count = " + $count ;
    print "Page title = " + $title ;
    print "Calling @ with no xpath set will now abort the loop " + @ ;    
}
print "Aborted ..." + $nl;

print "Now, looping for different bodies";
go ".//body" { 
        print "Entered body loop" ;
        print "Selected BODY = " + ( @ ~ $nl , " " ) ;

        go ".//input[@name='btnI']" {
            print "Selected  button with name : " + @ name:;       
            print "Click !";
            click "." ;
            print @ ;
            }
        }
print "No more bodies available";
print "End of " + $source ;
