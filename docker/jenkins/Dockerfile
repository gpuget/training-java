#Workspace in /var/jenkins_home
FROM jenkins:2.46.2
MAINTAINER Gaetan <gpuget@excilys.com>

# Update repositories, install sudo and remove repositories
USER root
RUN apt-get update && apt-get -y install sudo && rm -rf /var/lib/lists/*
RUN echo 'jenkins ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers

USER jenkins
