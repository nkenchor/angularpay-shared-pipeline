def call(Map pipelineParams = [:]) {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    withMaven(maven: '3.8.3') {
                        sh "mvn -DskipTests clean package"
                    }
                }
            }
            stage('Test') {
                steps {
                    withMaven(maven: '3.8.3') {
                        sh 'mvn test'
                    }
                }
                post {
                    always {
                        junit 'target/surefire-reports/*.xml'
                    }
                }
            }
            stage('Deploy') {
                steps {
                    sh "sudo slingshot.directories ${pipelineParams.artifactId} ${pipelineParams.serviceType}"
                    sh "sudo slingshot.ftp ${pipelineParams.artifactId} target/${pipelineParams.artifactId}.jar"
                    sh "sudo slingshot.systemd ${pipelineParams.artifactId} ${pipelineParams.serviceType} ${pipelineParams.serviceProfile} ${pipelineParams.doReinstall}"
                }
            }
        }
    }
}