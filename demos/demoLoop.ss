print "Starting " + $source ;
print "Here, there is no url initialization, so no web access";


print ;
print "Loop for 4 loops, no web element requested";
go c:4 {

    print "inside loop, elapsed time = " + $elapsed ;
    print "Loop count : " + $count ;      
    print "Looping 3 times inside ";
    go c:3 {
        print "  inside loop : " + $count ;       
        }    
   
    }
print $nl + "Idem, but break the inner loop during first loop" + $nl;
print "Loop for 4 loops, no web element requested";
go c:4 {

    print "inside loop, elapsed time = " + $elapsed ;
    print "Loop count : " + $count ;      
    print "  Looping 3 times inside ";
    go c:3 {
        print "  inside loop : " + $count ;  
        print "  Preparing to break in inner loop ...";
        print $break;    
        print "This should never display";
        }    
   
    }
    
print $nl + "Idem, but check the inner loop during first loop" + $nl;
print "Loop for 4 loops, no web element requested";
go c:4 {

    print "inside loop, elapsed time = " + $elapsed ;
    print "Loop count : " + $count ;      
    print "  Looping 3 times inside ";
    go c:3 {
        print "  inner count " + $count ;  
        print "  Preparing to check for count != 2 in inner loop ...";
        $count != 2;     
        print "This should never display when inner count is 2 or above";
        }    
   
    }
    
print $nl + "Indem, but continue the inner loop during first loop" + $nl;
print "Loop for 4 loops, no web element requested";
go c:4 {

    print "inside loop, elapsed time = " + $elapsed ;
    print "Loop count : " + $count ;      
    print "  Looping 3 times inside ";
    go c:3 {
        print "  inside loop : " + $count ;  
        print "  Preparing to 'continue' inner loop ...";
        print $continue;    
        print "This should never display";
        }    
   
    }

print $nl + "Indem, but abort the inner loop during first loop" + $nl;
print "Loop for 4 loops, no web element requested";
go c:4 {

    print "inside loop, elapsed time = " + $elapsed ;
    print "Loop count : " + $count ;      
    print "  Looping 3 times inside ";
    go c:3 {
        print "  inside loop : " + $count ;  
        print "  Preparing to abort inner loop ...";
        print $abort;    
        print "This should never display";
        }    
   
    }
 
print ;
print "End of " + $source ;