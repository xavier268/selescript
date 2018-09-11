
print ;
print "Demo of pattern matching" ;
print ;
s = "A beautiful string" ;

print "------------------------------------" ;
print "Testing find on '" + s + "'" ;
p = "tiful" ;           print "With :" + p ; print  s~p ;
p = "i+" ;              print "With :" + p ; print  s~p ;
p = "z*" ;              print "With :" + p ; print   (s~p != "")| "Empty string"  ;       
p = null ;              print "With null"  ; print  s~p ;
p = ""   ;              print "With empty string"  ; print  s~p ;


print ;
print "------------------------------------" ;
print "Testing find on null" ;
p = "tiful" ;           print "With :" + p ; print  s~p ;
p = "null" ;            print "With null"   ; print  s~p ;
