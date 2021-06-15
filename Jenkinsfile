pipeline {
    environment {
        registry = "i383656/kwetter-account-service"
        registryCredential = '372a3c4a-f903-4f24-8a97-1a27afc08275'
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
            sh 'mvn clean package sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=01468af9f814471f74fe7de4028ab67f32479b0f'
        }
      }
      stage('Building our image') {
        steps {
                sh 'ECHO $USER'
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

/*       stage('Docker Build and Tag') {
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
      } */
  }
}
