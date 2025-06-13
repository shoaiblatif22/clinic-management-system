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

        stage('Build Clinic API and run tests') {
            steps {
                dir('clinic-api') {
                    sh 'mvn clean install -U'
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
