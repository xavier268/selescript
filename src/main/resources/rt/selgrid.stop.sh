#!/bin/bash
echo "This script will try to cleanup and close most of the docker swarm/stack"
echo""
docker stack rm selgrid
docker swarm leave --force
docker info | grep Swarm

