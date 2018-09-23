
print "Here, there is no url initialization, so no web access";


print ;
print "Loop for 4 loops, no web element requested";
go c:4 {

    print "inside loop, elapsed time = " + $elapsed ;
    print "Loop count : " + $count ;      
    print "  Looping 3 times inside ";
    go c:3 {
        print "  inside loop : " + $count ;       
        }    
   
    }

print $nl + "Indem, but aborting the inner loop during first loop" + $nl;

print "Loop for 4 loops, no web element requested";
go c:4 {

    print "inside loop, elapsed time = " + $elapsed ;
    print "Loop count : " + $count ;      
    print "  Looping 3 times inside ";
    go c:3 {
        print "  inside loop : " + $count ;  
        print "Preparing to abort inner loop ...";
        null;     
        print "This should never display";
        }    
   
    }
    

print ;
print "End of " + $source ;