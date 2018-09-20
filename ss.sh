#!/bin/bash

# This launcher will run the latest selescript executable 
# it can find in the target directory. If there are many version, 
# it chooses the last in alphabetical order (latest version).
# All arguments are passed verbatim. If the argument -x is speified, 
# it will also run the generate script (see command line help).

mvn -version >/dev/null

if [ $? -ne 0 ] ; then 
   echo "Cannot compile because could not find maven !"
   echo "Go and install the latest maven version and try again ..."
   exit 1
fi

mvn package -DskipTests # Caution : tests are skipped ...

# Capturing the new generated jar
JAR="target/$( ls -1 target | grep "jar-with-dependencies" | sort -r | head -n 1 )"
echo "Using <$JAR> with arguments : $@"
java -jar $JAR $@

