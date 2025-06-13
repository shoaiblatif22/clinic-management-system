pipeline {
    agent any

    tools {
        maven 'maven3' 
        jdk 'jdk17'    
    }

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run mailhog') {
            steps {
                 sh 'docker run -d -p 1025:1025 -p 8025:8025 --name mailhog mailhog/mailhog'
            }
        }

        stage('Build clinic-api') {
            steps {
                dir('clinic-api') {
                    sh 'mvn clean'
                }
            }
        }

        stage('Run tests') {
            steps {
                sh 'mvn test'
        }

        stage('Archive Artifacts') {
            steps {
                dir('clinic-api') {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully.'
        }
        failure {
            echo 'Build failed.'
        }
        always {
            sh 'docker rm -f mailhog || true'
        }
    }
}
