#!groovy
pipeline {
    tools {
        jdk 'Java17-Temurin'
    }
    agent any
    environment {
        version = "1.0-${BRANCH_NAME}.${BUILD_NUMBER}"
    }
    stages {
        stage('Gradle Build') {
            steps {
                discordSend description: "${DISCORD_START_MESSAGE}", footer: "", enableArtifactsList: false, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
                sh 'chmod +x gradlew'
                sh "./gradlew clean build jacocoTestCoverageVerification -Pversion=${version}"
            }
        }
        stage('Nexus Deploy') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh './gradlew publish -Pversion=${version} -PnexusUsername=${NEXUS_USERNAME} -PnexusPassword=${NEXUS_PASSWORD}'
                }
            }
        }
    }
    post {
        success {
            discordSend description: "${DISCORD_SUCCESS_MESSAGE}", footer: "", enableArtifactsList: false, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
        }
        failure {
            discordSend description: "${DISCORD_FAIL_MESSAGE}", footer: "", enableArtifactsList: false, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
        }
    }
}