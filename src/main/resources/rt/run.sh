#!/bin/bash

echo "Launching the selenium grid stack"
bash <selgrid.start.sh

echo "Launching the generated scrapper"
mvn clean package exec:java -e

echo "Destroying the selenium stack"
bash <selgrid.stop.sh
