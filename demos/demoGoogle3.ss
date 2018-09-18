
$url = "https://www.google.com";
print "Now on page " + $url ;

print;
print "Looping for 5 loops max ";
go  c:5 {
    
    go ".//body" {
    print "Selected title = " + $title ;

    print "Type hello world" ;
    xp = ".//input[@name='q']" ;
    sendkeys t:"hello world\n", x:xp;
    print @ ;
    print "Submitting ...";
    submit   xp ;
    
    go ".//div[@id='resultStats']" {
        print @;
        }
        
    }

}

print ;
print "Done" ;
