#!groovy
pipeline {
    tools {
        jdk 'Java17-Temurin'
    }
    agent any
    environment {
        app_name = 'security'
        version = "0.${BUILD_NUMBER}"
    }
    stages {
        stage('Gradle Build') {
            steps {
                discordSend description: "Build ${env.BUILD_ID} started", footer: "", enableArtifactsList: false, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
                sh './gradlew jacocoTestCoverageVerification'
            }
        }
        stage('Nexus Deploy') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh './gradlew publish -PnexusUsername=${NEXUS_USERNAME} -PnexusPassword=${NEXUS_PASSWORD}'
                }
            }
        }
    }
    post {
        always {
            discordSend description: "Build finished ${env.BUILD_NUMBER}", footer: "", enableArtifactsList: false, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
        }
    }
}