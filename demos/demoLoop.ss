
print "Here, there is no url initialization";
print "Dereferencing current element gives empty string : " ;
print @ ;

print ;
print "Loop for 6 loops";
go c:6 {

    print "inside loop, elapsed time = " + $elapsed ;
    print $count ;  

    print ;
    print "  Looping 3 times inside ";
    go c:3 {
        print "  inside loop : " + $count ;       
    }     

    print "if title cannot be found, stop here " ;
    @ title: ;
    print "title was found" ;
    }

    

print ;
print "Text of current top webelement " ;
@ ;

print "End of demo" ;