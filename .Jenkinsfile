static String newPath(env) {
  return "${env.PATH}:/usr/local/bin"
}

pipeline {
  agent any
    environment {
      SERVICE_TARGET_PORT = 8080
      NAMESPACE = "ait-pro-web"
      PATH = newPath(env)
      ALLOWED_BRANCH = "dev"
      RUN_SNYK= sh script: "git log -1 | grep -E -- '--run-snyk'", returnStatus: true
      RUN_SONAR= sh script: "git log -1 | grep -E -- '--run-sonar'", returnStatus: true
      RUN_BUILD= sh script: "git log -1 | grep -E -- '--run-build'", returnStatus: true
      MERGE_ID= sh script: "git log -1 | grep '^    Merge pull request' | cut -d# -f2 | cut -d' ' -f1", returnStdout: true
      PROJECT_ID = sh(script: "echo ${env.GIT_URL} | cut -d/ -f5 | cut -d. -f1", returnStdout: true).trim()
      VOLUME_MOUNT_PATH = "/srv/ait-spring-boot-template"
      VOLUME_SUB_PATH = "ait_spring_boot_template-dev/"
    }

  stages {

    stage('Check Commit') {
      when { anyOf { allOf { branch "$ALLOWED_BRANCH" } ; allOf{expression{env.CHANGE_TARGET == "$ALLOWED_BRANCH"}} ; allOf { branch 'scan/sonar' } } }
      steps {
        script {
          result = sh (script: "git log -1 | grep -E '(feat|build|chore|fix|docs|refactor|perf|style|test)(\\(.+\\))*:'", returnStatus: true)
          if (result != 0) {
            echo "warning, not meet commit standard!"
          }
        }
      }
    } 

    stage('Unit-Test') {
      when { anyOf { allOf {branch "$ALLOWED_BRANCH"; expression{env.RUN_SONAR == '0'}} ; allOf {expression{env.CHANGE_TARGET == "$ALLOWED_BRANCH"}; expression{env.RUN_SONAR == '0'}} ; allOf { branch 'scan/sonar' } } }
      agent {
        docker {
          image 'maven:3.8.8-eclipse-temurin-21-alpine'
          reuseNode true
          args '-u root -v ${PWD}:/root -w /root'
        }
      }	
      steps {
        sh 'mvn -Dmaven.test.failure.ignore=true install'
        sh 'chown -R 114:121 target'
      }
    }

    stage('Sonarqube Analysis') {
      when { anyOf { allOf {branch "$ALLOWED_BRANCH"; expression{env.RUN_SONAR == '0'}} ; allOf {expression{env.CHANGE_TARGET == "$ALLOWED_BRANCH"}; expression{env.RUN_SONAR == '0'}} ; allOf { branch 'scan/sonar' } } }
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
      when { anyOf { allOf {branch "$ALLOWED_BRANCH"; expression{env.RUN_SONAR == '0'}} ; allOf {expression{env.CHANGE_TARGET == "$ALLOWED_BRANCH"}; expression{env.RUN_SONAR == '0'}} ; allOf { branch 'scan/sonar' } } }
      steps {
        timeout(time: 1, unit: 'HOURS') {
          waitForQualityGate abortPipeline: false
        }
      }
    }

    stage('Snyk Scan for Code') {
      when { allOf { branch "$ALLOWED_BRANCH"; expression { env.RUN_SNYK == '0' } } }
      steps {
        script {
          withCredentials([usernamePassword(credentialsId: 'aitops', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME'), file(credentialsId: 'snyk-sh', variable: 'SNYK_SH')]) {
            sh "cat ${SNYK_SH} > ./snyk.sh && chmod +x snyk.sh && /bin/bash -c ./snyk.sh"
          }
          def now = new Date()
          time = now.format("yyMMdd.HHmm", TimeZone.getTimeZone('Asia/Jakarta'))
          publishHTML (target: [allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: '.',  reportFiles: 'snyk.html', reportName: "SnykCodeReports.${time}.html", reportTitles: "Snyk Code Reports ${time}"])
          numberofline = sh(script: "egrep '(0</strong> low issues|0</strong> medium issues|0</strong> high issues)' snyk.html | wc -l", returnStdout: true).trim()
          if (numberofline != '3') {
            echo " Vulnerabilities Found!!!! "
            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') { sh 'exit 1' }
          } else { echo " === No Vulnerability Found === " }
          println "Scan Result can be viewed via ${env.RUN_ARTIFACTS_DISPLAY_URL}"
        }
      }
    } 

    stage('Snyk Open Source Vulnerability'){
      when { allOf { branch "$ALLOWED_BRANCH"; expression { env.RUN_SNYK == '0' } } }
      steps {
        script {
          def now = new Date()
          time = now.format("yyMMdd.HHmm", TimeZone.getTimeZone('Asia/Jakarta'))
          publishHTML (target: [allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: '.',  reportFiles: 'snykOS.html', reportName: "SnykOpenSourceVulnerabilityReports.${time}.html", reportTitles: "Snyk Open Source Vulnerability Reports ${time}"])
          numberofline = sh(script: "egrep '(0</span> <span>known vulnerabilities|0 vulnerable dependency paths</span>)' snykOS.html | wc -l", returnStdout: true).trim()
          if (numberofline != '2') {
            echo " Vulnerabilities Found!!!! "
            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') { sh 'exit 1' }
          } else { echo " === No Vulnerability Found === " }
          println "Scan Result can be viewed via ${env.RUN_ARTIFACTS_DISPLAY_URL}"
        }
      }
    }

    stage('Build Image - Push - Deploy') {
      when { allOf { branch "$ALLOWED_BRANCH"; expression { env.RUN_BUILD == '0' } } }
      steps {
        script {
          withCredentials([file(credentialsId: 'ait-k8s-do', variable: 'KUBECONFIG'),
            usernamePassword(credentialsId: 'aitops-docker-io', usernameVariable: 'USER', passwordVariable: 'PASS'),
            file(credentialsId: 'cicd-prep', variable: 'CICD_PREP')]) {
            sh 'docker login --username=${USER} --password=${PASS}'
            sh "cat ${CICD_PREP} > ./cicd-prep.sh && chmod +x cicd-prep.sh && /bin/bash -c ./cicd-prep.sh"
            sh 'export KUBECONFIG=$KUBECONFIG && skaffold run -n $NAMESPACE'
          }
        }  
      }
    }

    stage('Note for Merge Request'){
      when { allOf{expression{env.CHANGE_TARGET == "$ALLOWED_BRANCH"}}  }
       environment {
         scannerHome = tool 'sonarqube-scanner'
         PATH="$scannerHome/bin:/usr/bin:$PATH"
         SONAR_MONITOR = credentials('sonarqualitygate-monitor')
       }
      steps {
        script {
          sh "grep -q 'sonar.host.url' sonar-project.properties || echo 'sonar.host.url=https://sonar.akarinti.tech' >> sonar-project.properties"
          sh "sed -i 's/sonar.java.binaries=.*\$/sonar.java.binaries=./' sonar-project.properties"
          gitRepo=sh(script: "echo ${GIT_URL} | cut -d/ -f5 | cut -d. -f1", returnStdout: true).trim()
          withCredentials([usernamePassword(credentialsId: 'aitops', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GITPASSWORD'), usernamePassword(credentialsId: 'ait-sonar-global', usernameVariable: 'SONAR_USER', passwordVariable: 'SONARTOKEN')]) {
            def encodedPassword = URLEncoder.encode("$GITPASSWORD",'UTF-8')
            sh "sonar-quality-gate --version"
               withSonarQubeEnv(installationName: 'sonarqube') {
                sh "sonar-quality-gate -p=github -Dsonar.login=${SONARTOKEN} --sonar.token=${SONARTOKEN} --git.url=PT-Akar-Inti-Teknologi --git.token=${encodedPassword} --git.project_id=${PROJECT_ID} --git.merge_id=${env.CHANGE_ID} --monitor.url=${SONAR_MONITOR_USR} --monitor.token=${SONAR_MONITOR_PSW}"
                } 
              }
            }
          }
        }

    stage('After Merged'){
      when { allOf { expression{env.MERGE_ID != null}; branch "$ALLOWED_BRANCH" } }
      steps {
        script {
          withCredentials([file(credentialsId: 'ait-ghbx', variable: 'AIT_GHBX')]) {
            sh "cat ${AIT_GHBX} > ./ait-ghbx.py && /bin/bash -c 'python3 ait-ghbx.py'"    
          }
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

    always { cleanWs() }
  }
} 
