print ;
print "Reading from the input parameter file" ;
print;

go c:10 {

    print "before reading ...";
    print "read <" + $read + ">" ; 
    print "after reading ..." ;

    }

print ;
print "resetting the file";
$read = "Any input would do ..." ;
print ;

go c:10 {

    print "before reading ...";
    print "read <" + $read + ">" ; 
    print "after reading ..." ;

    }



print "End of file was reached";
print "end of demo" ;