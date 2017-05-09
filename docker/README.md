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

## More
* Check database name
* Check database host
* Check privileges network test and prod

## Clean docker
```
docker container prune
docker volume prune
docker network prune
docker image prune
```

# Jenkins
* Workspace in `/var/jenkins_home/`
* Initial password in `/var/jenkins_home/initialAdminPassword`
* Expose `8080` for main web interface
* Expose `50000` attached slave agent
* User : `jenkins`

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

## Run maven prod
```
sudo cp -rf /var/jenkins_home/workspace/$JOB_NAME/. /project
sudo rm -rf ./*

if [ $(sudo docker ps -aqf name=my-maven-prod) ];
then sudo docker stop my-maven-prod; sudo docker rm -v my-maven-prod;
fi

sudo docker run -v maven-home-prod:/root/.m2 -v project:/usr/src/app --network=prod-network --ip 192.168.2.2 --name my-maven-prod gaetan/maven-prod

sudo cp -rf /project/target/*.war /war
sudo rm -rf ./project/*

if [ ! $(sudo docker ps -aqf name=my-mysql-prod) ];
then sudo docker run -d -v mysql-lib-prod:/var/lib/mysql --network=prod-network --ip 192.168.2.3 --name my-mysql-prod gaetan/mysql
fi

if [ $(sudo docker ps -aqf name=my-tomcat) ];
then sudo docker stop my-tomcat; sudo docker start my-tomcat
else sudo docker run -d -v war:/usr/local/tomcat/webapps --network=prod-network --ip 192.168.2.4 -p 8888:8080 --name my-tomcat tomcat:8.5.14
fi

sudo docker stop my-maven-prod

sudo docker container prune -f
sudo docker volume prune -f
```
