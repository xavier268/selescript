
$url = "https://www.google.com";
print "Now on page " + $url ;

print;
print "Looping for 5 minutes";
go mn:5 {
    

    print "BODY = " + @ ".//body";

    go ".//input[@name='btnI']" {
        print "On button " + @ ;
        print "Click !";
        click "." ;
        print @ ;
        }

}

print ;
print "Done" ;
