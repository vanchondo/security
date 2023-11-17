#!groovy
pipeline {
    tools {
        jdk 'Java17-Temurin'
    }
    agent any
    stages {
        stage('Gradle Build') {
            steps {
                discordSend description: "Build ${env.BUILD_ID} started", footer: "", enableArtifactsList: false, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
                sh 'chmod +x gradlew'
                sh "./gradlew clean build jacocoTestCoverageVerification -PbuildNumber=${env.BUILD_NUMBER}"
            }
        }
        stage('Nexus Deploy') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh "./gradlew publish -PbuildNumber=${env.BUILD_NUMBER} -PnexusUsernam=${NEXUS_USERNAME} -PnexusPassword=${NEXUS_PASSWORD}"
                }
            }
        }
    }
    post {
        always {
            discordSend description: currentBuild.currentResult ? "Build ${env.BUILD_ID} finished successfully" : "Build ${env.BUILD_ID} failed" , footer: "", enableArtifactsList: false, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
        }
    }
}