pipeline {
  agent any
  tools {
    maven 'maven 3.6.3'
    jdk 'jdk-11'
  }
  environment {
    registry = "kwetter-account-service"
    registryCredential = '70910a1b-9021-44fc-8b9e-a829bc978392'
    dockerImage = ''
  }
  stages {
      stage('run test') {
        steps {
            sh 'mvn test'
        }
      }
      stage('Building our image') {
        steps {
           sh 'mvn clean package spring-boot:build-image'
        }
      }
      stage('Publish image to Docker Hub') {
        steps {
          withDockerRegistry([ credentialsId: "70910a1b-9021-44fc-8b9e-a829bc978392", url: "" ]) {
            sh 'docker tag $registry:0.0.1-SNAPSHOT i383656/kwetter-account-service'
            sh  'docker push i383656/kwetter-account-service'
          }
        }
      }      
      stage('Cleaning up') {
        steps {
          sh "docker rmi i383656/kwetter-account-service"
        }
      }
  }
}