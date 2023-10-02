def call(Map pipelineParams = [:]) {
	pipeline {
		agent any
		stages {
			stage('Install dependencies') {
				steps {
					sh "echo 'nothing to install for now'"
				}
			}
			stage('Build') {
				steps {
					sh "echo 'nothing to build for now'"
				}
			}
			stage('Test') {
				steps {
					sh "echo 'no test to run'"
				}
			}
			stage('Deploy') {
				steps {
					sh "sudo slingshot.directories.next ${pipelineParams.artifactId}"
					sh "sudo slingshot.ftp.next ${pipelineParams.artifactId}"
					sh "sudo slingshot.systemd.next ${pipelineParams.artifactId}"
				}
			}
		}
	}
}