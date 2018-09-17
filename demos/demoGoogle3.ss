
$url = "https://www.google.com";
print "Now on page " + $url ;

print;
print "Looping for 5 minutes max with xpath = './/body'";
go ".//body" , mn:5 {
    

    print "Selected title = " + $title ;

    

    print "Type hello world" ;
    sendkeys t:"hello world", x:".//input['name'='q']" ;
        
    print "Click !";
    click ".//input[@name='btnI']"  ;
    print @ ;
        

}

print ;
print "Done" ;
