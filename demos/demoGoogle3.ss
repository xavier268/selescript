
$url = "https://www.google.com";
print "Now on page " + $url ;

go x:".//body" , c:5 {
    print $nl + "loop " + $tab + $count + $nl ;
    print "Now on url : " + $url + " and title : " + $title ;    
    xp = ".//input[@name='q']" ;
    print "Preparing to send content to : " + xp;
    type t:"hello world", x:xp;
    print "Now on url : " + $url + " and title : " + $title ;    
    print "Submitting " + xp ;
    submit   w:xp ;
    print "Now on url : " + $url + " and title : " + $title ;     
    }
print $nl + "End of " + $source ;

/* A few comments here :
 - we don't call @ inside the loop after the page changed, otherwise the loop would stop.
 - the $title and $url do not generate stale emement excptions on changed pages.
*/
