pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    
    tools {
        maven 'maven3'
        jdk 'jdk17'
    }

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }


    stages {
        stage('Start MailHog') {
            steps {
                sh 'docker run -d -p 1025:1025 -p 8025:8025 --name mailhog mailhog/mailhog'
                sleep 10
                sh '''
                    if curl --fail localhost:8025; then
                      echo "MailHog UI is up"
                    else
                      echo "MailHog UI not reachable"
                      exit 1
                    fi
                '''
            }
        }
        
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build clinic-api') {
            steps {
                dir('clinic-api') {
                    sh 'mvn clean'
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                dir('clinic-api') {
                    echo 'Running unit tests only...'
                    sh 'mvn test'
                }
            }
        }

        stage('Run Integration Tests') {
            steps {
                dir('clinic-api') {
                    echo 'Running integration tests with spring profile "test"...'
                    sh 'mvn verify -Dspring.profiles.active=test'
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
        always {
            sh 'docker rm -f mailhog || true'
        }
    }
}
