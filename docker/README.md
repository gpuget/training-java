# Docker

See `docker-config.sh`

## Create shared volumes
```
docker volume create --name <name> 
docker run -v <volume-name>:<dst-in-container> <container>
```

## Create networks
```
docker network create --subnet <network-address>/<network-mask> --gateway <gateway-address> <name>
docker run --network=<network-name> <container>
```
## Build images
```
docker build -t <name>:<version> <dockerfile-directory>
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
## More
* Check database name
* Check database host
* Check database privileges for network test and prod

# Jenkins
* Workspace in `/var/jenkins_home/`
* Initial password in `/var/jenkins_home/initialAdminPassword`
* Expose `8080` for main web interface
* Expose `50000` attached slave agent
* User : `jenkins`

## Run jenkins
```
docker run -v <jenkins-volume>:/var/jenkins_home \
		 -v <project-volume>:<container-volume> \
		 -v <war-volume>:<container-volume> \
		 -v /var/run/docker.sock:/var/run/docker.sock \	
		 -v $(which docker):/usr/bin/docker \
		 -p <PORT>:8080 --name <container-name> <image>
```

See `jenkins/jenkins-run.sh`

## Run test in jenkins
See `jenkins/test-job.sh`

## Run prod in jenkins
See `jenkins/prod-job.sh`
