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
docker exec -itu <container> bash
```

# Jenkins
* Workspace in /var/jenkins_home/
* Initial password in /var/jenkins_home/initialAdminPassword
