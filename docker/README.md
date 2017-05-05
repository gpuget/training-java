# Docker
## Create shared volumes
```
docker volume create --name <name> 
docker run -v <volume-name>:<dst-incontainer> <container>
```

## Create networks
```
docker network create <name>
docker run --network=<network-name> <container>
```
## Build images
```
docker build -t <name>:<version> <dockerfile>
```

## Bash in container
```
docker exec -it -uroot <container> bash
```

## Clean docker
```
docker container prune
docker volume prune
docker network prune
docker image prune
```

# Jenkins
* Workspace in /var/jenkins_home/
* Initial password in /var/jenkins_home/initialAdminPassword
* Expose 8080 for main web interface
* Expose 50000 attached slave agent
* User : jenkins

## Run jenkins
```
docker run -v <jenkins-volume>:/var/jenkins_home -v <project-volume>:<container-volume> -v <war-volume>:<container-volume> -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -p <PORT>:8080 --name <container-name> <image>
```

## Run maven test
```
sudo cp -rf /var/jenkins_home/workspace/$JOB_NAME/. /project
sudo rm -rf ./*

if [ $(sudo docker ps -aqf name=my-maven-test) ];
then sudo docker rm -v my-maven-test;
fi

sudo docker run -v maven-home:/root/.m2 -v project:/usr/src/app --network test-network --ip 192.168.1.2 --name my-maven-test gaetan/maven-test

sudo docker stop my-maven-test

sudo docker rm -v my-maven-test;
```
