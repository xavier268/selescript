
print "Demo of sending data to the database" ;
print " ( no web element requested ) ";

db from: "demoDb.ss", deux:"two", number: 123, null-key: null;

print "Records were successfully sent to db" ;
print "End of " + $source;