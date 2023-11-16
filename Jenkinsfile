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
                discordSend description: "Build started", footer: "", enableArtifactsList: false, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
                sh './gradlew clean build jacocoTestCoverageVerification'
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
            discordSend description: "Build finished", footer: "", enableArtifactsList: false, link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: "${WEBHOOK_URL}"
        }
    }
}