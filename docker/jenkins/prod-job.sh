sudo cp -rf /var/jenkins_home/workspace/$JOB_NAME/. /project
sudo rm -rf ./*

if [ $(sudo docker ps -aqf name=my-maven-prod) ];
then sudo docker stop my-maven-prod; sudo docker rm -v my-maven-prod;
fi

sudo docker run -v maven-home-prod:/root/.m2 \
		 -v project:/usr/src/app \
		 --network=prod-network --ip 192.168.2.2 \
		 --name my-maven-prod gaetan/maven-prod

sudo cp -rf /project/target/*.war /war
sudo rm -rf ./project/*

if [ ! $(sudo docker ps -aqf name=my-mysql-prod) ];
then sudo docker run -d -v mysql-lib-prod:/var/lib/mysql \
			 --network=prod-network --ip 192.168.2.3 \
			 --name my-mysql-prod gaetan/mysql
fi

if [ $(sudo docker ps -aqf name=my-tomcat) ];
then sudo docker stop my-tomcat; sudo docker start my-tomcat
else sudo docker run -d -v war:/usr/local/tomcat/webapps \
			 --network=prod-network --ip 192.168.2.4 \
			 -p 8888:8080 --name my-tomcat tomcat:8.5.14
fi

sudo docker stop my-maven-prod

sudo docker container prune -f
sudo docker volume prune -f
