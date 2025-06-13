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

        stage('Build Clinic API') {
            steps {
                dir('clinic-api') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                dir('clinic-api') {
                    sh 'mvn test'
                }
            }
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
    }
}
