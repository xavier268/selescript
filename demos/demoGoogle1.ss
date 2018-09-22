/** Navigating the google search page **/

$url = "http://www.google.fr" ;

print "Opened page : " + $url ;

print "title : " + $title ;
print "$count = " + $count ;
print "elapsed time = " + $elapsed ;


go ".//body" {
    print "Entering body loop" ;
    print "Getting the text of the current element" ;
    print @ ;    
    print "Body count = " + $count ;   
    print "Now looking for first 6 divs" ;
    go ".//div", c:6 {        
        print "In the div loop" ;
        print "div count = " + $count ;
        print "elapsed time = "  + $elapsed ;
        print "Getting the text of the current element :" ;
        print @ ;
        }

    print ;
    print "Now, looking for existing input tags ..." ;
    go "//input" {
        print "Input value : " + @ value: + " , name  "  + @ name: ;
        }


    print ;
    print "Looking for input, only those with a value other than empty string" ;
    // It seems attributes name or value are never null.
    go "//input" {
        // Assume neither null nor empty string
        (@ value:  | "") != "" ;
        print "Input value : " +  @ value: +  " , name : " + @ name: ;
        }


    print ;
    print "Looking for input, of type hidden" ;
    // It seems attributes name or value are never null.
    go "//input" {
        // Assume type hidden
        @ type:  == "hidden" ;
        print "Input value : " + @ value: +  " , name : "  + @ name: +  " , type :" + @ type:  ;
        }

    print ;
    print "Looking for input where there is no maxlength attribute ?" ;
    // It seems attributes name or value are never null.
    go "//input" {
        print "There is always one, as for name, value, ..., even a default one !";
        ! @ maxlength: ;
        print "Input value : " + @ value:  " , name : " + @ name: +  " , maxlength :" + @ maxlength: ;
        }

    } // go body
print "end of demo" ;
