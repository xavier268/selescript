
$url = "https://www.google.com";
print "Now on page " + $url ;

go c:5  {
    print $nl + "loop "+ $depth + $tab + "count " + $count + $nl ;        
    go x:".//body" , c:5 {
        print $nl + $tab + "loop "+ $depth + $tab + "count " + $count + $nl ;        
        print $tab + "Now on url : " + $url + " and title : " + $title ;    
        xp = ".//input[@name='q']" ;
        print $tab +"Preparing to send content to : " + xp;
        type t:"hello world", x:xp;
        print $tab +"Now on url : " + $url + " and title : " + $title ; 
        print $tab +"Page content = " + ( @ ~ $nl,"") ;   
        print $tab +"Submitting " + xp ;
        submit   w:xp ;
        print $tab +"Now on url : " + $url + " and title : " + $title ; 
        null; // <- this will abort loop
    }
}
print "Now back to loop " + $depth + "count " + $count;
print $nl + "End of " + $source ;

/* A few comments here :
 - we DO call @ inside the loop after the page changed, generating a loop stop
 - the $title and $url do not generate stale emement excptions on changed pages.
*/
