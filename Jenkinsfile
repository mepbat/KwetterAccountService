pipeline {
    environment {
        registry = "i383656/kwetter-account-service"
        registryCredential = 'b58c057d-2715-4968-a5ec-34975c8d0920'
        dockerImage = ''
    }
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
      stage('Building our image') {
        steps {
          script {
            dockerImage = docker.build registry + ":$BUILD_NUMBER"
          }
        }
      }
      stage('Deploy our image') {
        steps {
           script {
              docker.withRegistry( '', registryCredential ) {
                 dockerImage.push()
              }
           }
        }
      }
      stage('Cleaning up') {
        steps {
          sh "docker rmi $registry:$BUILD_NUMBER"
        }
      }
  }
}
