#!/bin/bash
# This is Paris, zone a
REGION=eu-west-3
ZONE=a
SG=docker-machine
echo "Creating an aws cluster in ${REGION}$ZONE  with security group $SG"
docker-machine version  || { echo "Could not start docker-machine" ; exit 1 ; } 

# The network ports required for a Docker Swarm to function correctly are:
# TCP port 2376 for secure Docker client communication. 
#          This port is required for Docker Machine to work. 
#          Docker Machine is used to orchestrate Docker hosts.
# TCP port 2377. 
#          This port is used for communication between the nodes of a Docker Swarm or cluster. 
#          It only needs to be opened on manager nodes.
# TCP and UDP port 7946 for communication among nodes (container network discovery).
# UDP port 4789 for overlay network traffic (container ingress networking). 
#	   Can be restricted to internal scope.

# Don't forget TCP port 22 and all application specific ports ...

# The docker-machine security group should pre-exist with adequate configuration.

echo "Expecting existing and configured security group named docker-machine"
# Here, we assume that a docker-machine security group was created with the default VPC
docker-machine create --driver amazonec2 --amazonec2-region $REGION --amazonec2-zone $ZONE \
	--amazonec2-security-group $SG  \
	testMaster || \
		{ echo "Could not create Master" ; exit 1 ; } 

docker-machine create --driver amazonec2 --amazonec2-region $REGION --amazonec2-zone $ZONE \
	--amazonec2-security-group $SG  \
	testWorker || \
		{ echo "Could not create Worker" ; exit 1 ; } 

# Starting a swarm manager, allocating tokens, memorizing manager address.
eval $( docker-machine env testMaster )
docker swarm init
WTOKEN=$( docker swarm join-token -q worker )
MTOKEN=$( docker swarm join-token -q manager )
MASTERADDR=$( docker node inspect --format '{{.ManagerStatus.Addr}}' self )
echo "Debug : $WTOKEN ---- $MASTERADDR "

# Starting worker(s)
eval $( docker-machine env testWorker )
docker swarm join --token $WTOKEN $MASTERADDR 

# Display status
eval $( docker-machine env testMaster )
docker node ls







