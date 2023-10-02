def call(Map pipelineParams = [:]) {
	pipeline {
		agent any
		stages {
			stage('Install dependencies') {
				steps {
					sh "npm install"
				}
			}
			stage('Build') {
				steps {
					sh "npm run build"
				}
			}
			stage('Test') {
				steps {
					sh "echo 'no test to run'"
				}
			}
			stage('Deploy') {
				steps {
					sh "sudo slingshot.directories.node ${pipelineParams.artifactId}"
					sh "sudo slingshot.ftp.node ${pipelineParams.artifactId} build"
					sh "sudo slingshot.systemd.node ${pipelineParams.artifactId}"
				}
			}
		}
	}
}