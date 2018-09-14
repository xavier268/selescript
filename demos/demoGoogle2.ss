
$url = "www.google.com";
print "Now on page " + $url ;

print;
print "Looping dor 30 seconds";
go s:30 {
    

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
