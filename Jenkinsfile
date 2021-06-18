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
            bat 'mvn test'
        }
      }
      stage('Building our image') {
        steps {
           bat 'mvn clean package spring-boot:build-image'
        }
      }
      stage('Publish image to Docker Hub') {
        steps {
          withDockerRegistry([ credentialsId: "70910a1b-9021-44fc-8b9e-a829bc978392", url: "" ]) {
            bat 'docker tag $registry:0.0.1-SNAPSHOT i383656/kwetter-account-service'
            bat 'docker push i383656/kwetter-account-service'
          }
        }
      }      
      stage('Cleaning up') {
        steps {
          bat "docker rmi i383656/kwetter-account-service"
        }
      }
  }
}