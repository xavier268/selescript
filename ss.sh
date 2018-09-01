#!/bin/bash

# This launcher will run the latest selescript executable 
# it can find in the target directory. If there are many version, 
# it chooses the last in alphabetical order (latest version).

# Arguments are passed verbatim.

JAR="target/$( ls -1 target | grep "jar-with-dependencies" | sort -r | head -n 1 )"
echo "Arguments : $@"
echo "Using executable : <$JAR>"
java -jar $JAR $@

