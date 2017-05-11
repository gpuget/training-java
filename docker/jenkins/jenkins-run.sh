docker run -v jenkins-home:/var/jenkins_home \
		 -v project:/project \
		 -v war:/war \
		 -v /var/run/docker.sock:/var/run/docker.sock \	
		 -v $(which docker):/usr/bin/docker \
		 -p 9000:8080 --name my-jenkins gaetan/jenkins
