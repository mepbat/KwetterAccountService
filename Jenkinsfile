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
            sh 'mvn clean package sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=c3b2b407fd5ab3cdabd0b6765b84ec5c6c93e726'
        }
      }
      stage('Docker Build and Tag') {
        steps {
            sh 'docker build -t kwetter-account-service:latest .'
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
