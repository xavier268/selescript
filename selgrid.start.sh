#!/bin/bash
echo "This script is provided as a convenience script to test the selescript langage"
echo "It will launch a docker swarm/stack with a few headless browsers and a mongodb instance"
echo "You may need to launch this as root, depending of your configuration details"
echo ""

# Test for docker to be running ..
docker info | grep -E "Swarm|Server Version|Kernel"
if [ $? -ne 0 ] ; then
    echo "Docker does not seem to be available on your machine".
    echo "You're on your own ... Aborting"
    exit 1;
fi

docker swarm init 2>/dev/null 1>/dev/null
docker info | grep "Swarm"
docker node ls
docker stack deploy --compose-file selgrid.yaml selgrid
docker stack ls
docker service ls




