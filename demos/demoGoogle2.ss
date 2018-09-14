
$url = "https://www.google.com";
print "Now on page " + $url ;

print;
print "Looping for 5 minutes";
go ".//body" , mn:5 {
    

    print "BODY = " + @ ;

    go ".//input[@name='btnI']" {
        print "On button with name : " + @ name:;       
        print "Click !";
        clickw "." ;
        print @ ;
        }

}

print ;
print "Done" ;
