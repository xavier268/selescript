print ;
print "Reading from the input parameter file" ;
print;

go c:10 {

    print "before reading ...";
    read = $read ;
    print "read <" + read + ">" ; 
    read ;
    print "after reading ..." ;

    }

print "Resetting the input file ...";
$read = null;

go c:10 {

    print "before reading ...";
    read = $read ;
    print "read <" + read + ">" ; 
    read ;
    print "after reading ..." ;

    }


print "End of file was reached";
print "end of demo" ;