print ;
print "Starting " + $source;

target1 = "target/demos/demoOutput/file1.txt";
target2 = target1 ~ 1 , 2 ;

print "Output is initally configured to : " + $output;
print "About to print alternatively to " + target1 + " and " + target2  ;

print file: target1, "First message recorded in " + target1 ;
print "Current output is now : " + $output ;

print file: target2, "First message recorded in " + target2 ;
print "Current output is now : " + $output ;

print file: target1, "Second message recorded in " + target1 ;
print "Current output is now : " + $output ;

print ;

$input = target1;
print "Dumping file : " + $input ;
go {
    print $tab + $read ;
    }

$input = target2;
print "Dumping file : " + $input ;
go {
    print $tab + $read ;
    }

print ; print "End of " + $source ;


