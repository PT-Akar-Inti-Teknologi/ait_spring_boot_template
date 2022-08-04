pipeline {
   agent any

   stages {
     stage('Check Commit') {
       steps {
        script {
          result = sh (script: "git log -1 | grep -E '(feat|build|chore|fix|docs|refactor|perf|style|test)(\\(.+\\))*:'", returnStatus: true)
          if (result != 0) {
            throw new Exception("failed, not meet commit standard!")
          }
        }
       }
     }

     stage('Build') {
       steps {
         sh 'mvn -Dmaven.test.failure.ignore=true install'
       }
     }

      stage('Sonarqube analysis') {
        environment {
          scannerHome = tool 'sonarqube-scanner'
        }

        steps {
          withSonarQubeEnv(installationName: 'sonarqube') {
            sh '$scannerHome/bin/sonar-scanner'
          }
        }
      }

      stage('Quality Gate') {
        steps {
          timeout(time: 1, unit: 'HOURS') {
            waitForQualityGate abortPipeline: true
          }
        }
      }
   }

   post {
     failure {
       emailext subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}",
         body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
         recipientProviders: [
           [$class: 'DevelopersRecipientProvider'],
           [$class: 'RequesterRecipientProvider']
         ]
     }

     always {
       cleanWs()
     }
   }
}
