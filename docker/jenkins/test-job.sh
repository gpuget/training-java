sudo cp -rf /var/jenkins_home/workspace/$JOB_NAME/. /project
sudo rm -rf ./*

if [ $(sudo docker ps -aqf name=my-maven-test) ];
then sudo docker rm -v my-maven-test;
fi

if [ $(sudo docker ps -aqf name=my-mysql-test) ];
then sudo docker stop my-mysql-test; sudo docker rm -v my-mysql-test;
fi

sudo docker run -d -v mysql-lib-test:/var/lib/mysql \
		 --network=test-network --ip 192.168.1.3 \
		 --name my-mysql-test gaetan/mysql

sudo docker run -v maven-home:/root/.m2 \
		 -v project:/usr/src/app \
		 --network=test-network --ip 192.168.1.2 \
		 --name my-maven-test gaetan/maven-test

sudo docker stop my-maven-test

sudo docker rm -v my-maven-test;
