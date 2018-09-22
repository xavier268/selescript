
print "Here, there is no url initialization, so no web access";


print ;
print "Loop for 6 loops, no web element requested";
go c:6 {

    print "inside loop, elapsed time = " + $elapsed ;
    print "Loop count : " + $count ;      
    print "  Looping 3 times inside ";
    go c:3 {
        print "  inside loop : " + $count ;       
        }    
   
    }

    

print ;
print "End of demo" ;