#!/bin/bash

echo "launching the selenium grid stack"
bash -c selgrid.start.sh

echo "Running the generated scrapper"
mvn clean exec:java -e 
