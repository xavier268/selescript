
$url = "https://www.google.com";
print "Now on page " + $url ;

print;
print "Looping for 5 minutes max without xpath = './/body'";
go ".//body" , mn:5 {
    

    print "Selected BODY = " + @ ;

    go ".//input[@name='btnI']" {
        print "Selected button with name : " + @ name:;       
        print "Click !";
        clickw "." ;
        print @ ;
        }

}

print ;
print "Done" ;
