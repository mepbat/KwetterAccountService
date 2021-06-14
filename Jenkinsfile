pipeline {
  agent any
  tools {
    maven 'maven 3.6.3'
    jdk 'jdk-11'
  }
  stages {
      stage('run test') {
        steps {
            sh 'mvn test'
        }
      }
      stage('SonarQube analysis') {
        steps {
            sh 'mvn clean package sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=2acda40194236facad9e62771a0ebe7d99f57f16'
        }
      }
      stage('Docker Build and Tag') {
        steps {
            sh 'docker build -t kwetter-account-service:latest '
            sh 'docker tag kwetter-account-service kwetter-account-service:latest'
            sh 'docker tag kwetter-account-service kwetter-account-service:$BUILD_NUMBER'
        }
      }
      stage('Publish image to Docker Hub') {
        steps {
          withDockerRegistry([ credentialsId: "b58c057d-2715-4968-a5ec-34975c8d0920", url: "" ]) {
            sh  'docker push kwetter-account-service kwetter-account-service:latest'
            sh  'docker push kwetter-account-service kwetter-account-service:$BUILD_NUMBER'
          }
        }
      }
  }
}
