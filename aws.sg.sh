#!/bin/bash
REGION=eu-west-3
SG=sssg

echo "Creating the required security-group"
aws ec2 create-security-group --group-name $SG --description "Selecript utility security group" --region $REGION
echo "Opening the various ports "

# Docker & docker swarm
aws ec2 authorize-security-group-ingress --region $REGION --group-name $SG --port 22   --protocol tcp --cidr "0.0.0.0/0" 
aws ec2 authorize-security-group-ingress --region $REGION --group-name $SG --port 2376 --protocol tcp --cidr "0.0.0.0/0"
aws ec2 authorize-security-group-ingress --region $REGION --group-name $SG --port 2377 --protocol tcp --cidr "0.0.0.0/0" 
aws ec2 authorize-security-group-ingress --region $REGION --group-name $SG --port 7946 --protocol tcp --source-group $SG # discovery 
aws ec2 authorize-security-group-ingress --region $REGION --group-name $SG --port 7946 --protocol udp --source-group $SG # discovery
aws ec2 authorize-security-group-ingress --region $REGION --group-name $SG --port 4789 --protocol udp --source-group $SG # Overlay

# Selenium hub
aws ec2 authorize-security-group-ingress --region $REGION --group-name $SG --port 5555 --protocol tcp --source-group $SG 
aws ec2 authorize-security-group-ingress --region $REGION --group-name $SG --port 4444 --protocol tcp --cidr "0.0.0.0/0" 

# Mongo
aws ec2 authorize-security-group-ingress --region $REGION --group-name $SG --port 27017 --protocol tcp --cidr "0.0.0.0/0" 

echo "Done. Created Security Group : $SG"


