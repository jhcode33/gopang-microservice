pipeline {
    agent any
    tools {
        // Specify the JDK tool installation
        jdk 'openjdk-17'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                        credentialsId: 'gopang',
                        url: 'https://github.com/ProjectGopang/conn_test.git'
            }
        }

        stage('Prepare Workspace') {
            steps {
                script {
                    // Gradle Wrapper execution permission
                    sh 'chmod +x gradlew'
                }
            }
        }

        stage('Gradle Build') {
            steps {
                script {
                    // Run Gradle build
                    sh './gradlew clean build'
                }
            }
        }
    }
}
