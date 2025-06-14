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
        DOCKER_HOST = 'unix:///var/run/docker.sock'
    }

    stages {
        stage('Verify Docker') {
            steps {
                script {
                    try {
                        sh 'docker --version'
                        sh 'docker ps' 
                        echo "Docker is working properly"
                    } catch(e) {
                        error "Docker not functioning: ${e}"
                    }
                }
            }
        }

        stage('Start MailHog') {
            steps {
                script {
                    sh 'docker rm -f mailhog || true'
                    
                    sh '''
                        docker run -d \
                          --name mailhog \
                          -p 1025:1025 \
                          -p 8025:8025 \
                          mailhog/mailhog
                    '''
                    
                    sh '''
                        timeout 30 bash -c '
                        until curl -f http://localhost:8025; do
                            echo "Waiting for MailHog..."
                            sleep 2
                        done
                        '
                    '''
                }
            }
        }
        
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                dir('clinic-api') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Run Tests') {
            steps {
                dir('clinic-api') {
                    sh 'mvn verify -Dspring.profiles.active=test'
                }
            }
        }
    }

    post {
        always {
            script {
                sh 'docker rm -f mailhog || true'
                
                sh 'docker system prune -f || true'
            }
        }
    }
}
