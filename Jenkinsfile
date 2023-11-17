#!groovy
pipeline {
    tools {
        jdk 'Java17-Temurin'
    }
    agent any
    stages {
        stage('Gradle Build') {
            steps {
                discordSend description: "${DISCORD_START_MESSAGE}", footer: "", enableArtifactsList: true, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
                sh 'chmod +x gradlew'
                sh "./gradlew clean build jacocoTestCoverageVerification -PbuildNumber=${env.BUILD_NUMBER}"
            }
        }
        stage('Nexus Deploy') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh "./gradlew publish -PbuildNumber=${env.BUILD_NUMBER} -PnexusUsername=${NEXUS_USERNAME} -PnexusPassword=${NEXUS_PASSWORD}"
                }
            }
        }
    }
    post {
        success {
            discordSend description: "${DISCORD_SUCCESS_MESSAGE}", footer: "", enableArtifactsList: true, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
        }
        failure {
            discordSend description: "${DISCORD_FAIL_MESSAGE}", footer: "", enableArtifactsList: true, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
        }
    }
}