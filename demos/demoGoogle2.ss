
$url = "https://www.google.com";
print "Now on page " + $url ;

print;
print "Looping for 5 times max without xpath";

go  c:5 {

    print "Before selecting any xpath :" + @;
    print "Page title = " + $title ;

    go "//body" { 
        print "Entered body loop" ;
        print "Selected BODY = " + @ ;

        go ".//input[@name='btnI']" {
            print "Selected  button with name : " + @ name:;       
            print "Click !";
            click "." ;
            print @ ;
            }
        }

}

print ;
print "Done" ;
