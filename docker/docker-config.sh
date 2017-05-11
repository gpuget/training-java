## Create shared volumes
docker volume create --name project
docker volume create --name war

## Create networks
docker network create --subnet 192.168.1.0/24 --gateway 192.168.1.1 test-network
docker network create --subnet 192.168.2.0/24 --gateway 192.168.2.1 prod-network

## Build images
docker build -t gaetan/mysql mysql/.
docker build -t gaetan/maven-test maven/test/.
docker build -t gaetan/maven-prod maven/prod/.
docker build -t gaetan/jenkins jenkins/.
