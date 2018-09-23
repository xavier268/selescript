
print "Reading from the cli provided input parameter file : " + $input;

go c:10 {    
    print $count +  $tab + "read <" + $read + ">" ;     
    }

print "Resetting the input file to source file : " + $source;
$read = $source;

go c:30 {   
    print $count +  $tab + "read <" + $read + ">" ;     
    }

print "End of " + $source ;

print "End of file was reached";
print "end of demo" ;