def call(Map pipelineParams = [:]) {
	pipeline {
		agent any
		tools {
				go 'go1.17'
			}
		environment {
			GO117MODULE = 'on'
		}
		stages {
			stage('Build') {
				steps {
						   echo 'Compiling and building'
						   sh 'go build .'
					  }
			}
			stage('Deploy') {
				steps {
					sh "sudo slingshot.directories.go ${pipelineParams.artifactId}"
					sh "sudo slingshot.ftp.go ${pipelineParams.artifactId} ${pipelineParams.binaryName} ${pipelineParams.artifactId}.env"
					sh "sudo slingshot.systemd.go ${pipelineParams.artifactId} ${pipelineParams.binaryName} ${pipelineParams.serviceProfile} ${pipelineParams.doReinstall}"
				}
			}
		}
	}
}