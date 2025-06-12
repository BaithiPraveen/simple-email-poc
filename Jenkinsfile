pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('SonarQubeToken')
        JAVA_HOME = tool 'jdk17'
        MAVEN_HOME = tool 'maven3'
    }

    tools {
        maven 'maven3'
        jdk 'jdk17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/BaithiPraveen/simple-email-poc.git',
                    ]]
                ])
            }
        }

        stage('Build') {
            steps {
                script {
                    try {
                        bat 'mvn clean compile'
                    } catch (Exception e) {
                        echo "Build failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        error('Build stage failed')
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    try {
                        bat 'mvn test'
                    } catch (Exception e) {
                        echo "Tests failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        error('Test stage failed')
                    }
                }
            }
            post {
                always {
                    script {
                        if (fileExists('target/surefire-reports/TEST-*.xml')) {
                            junit 'target/surefire-reports/TEST-*.xml'
                        } else {
                            echo 'No test reports found. Skipping JUnit report.'
                        }
                    }
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    try {
                        withSonarQubeEnv('SonarQube') {
                            echo "Starting SonarQube analysis..."
                            bat '''
                                mvn sonar:sonar \
                                -Dsonar.projectKey=email-poc \
                                -Dsonar.projectName=email-poc \
                                -Dsonar.java.binaries=target/classes \
                                -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                                -Dsonar.sources=src/main/java \
                                -Dsonar.tests=src/test/java \
                                -Dsonar.java.source=17
                            '''
                            echo "SonarQube analysis completed."
                        }
                    } catch (Exception e) {
                        echo "SonarQube analysis failed: ${e.message}"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    try {
                        echo "Waiting for SonarQube Quality Gate..."
                        timeout(time: 5, unit: 'MINUTES') {
                            waitForQualityGate abortPipeline: false
                        }
                    } catch (Exception e) {
                        echo "Quality Gate check failed or timed out: ${e.message}"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    try {
                        bat 'mvn package -DskipTests'
                    } catch (Exception e) {
                        echo "Package failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        error('Package stage failed')
                    }
                }
            }
        }

        stage('Cleanup') {
            steps {
                cleanWs()
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
        unstable {
            echo 'Pipeline completed with warnings!'
        }
    }
} 