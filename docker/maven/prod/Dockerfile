#Install maven and create /usr/src/app
FROM maven:3.5.0-jdk-8
MAINTAINER Gaetan <gpuget@excilys.com>

#Check and choose the workdir
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

#Run the build
CMD mvn clean package -DskipTests
