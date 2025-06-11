pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/BaithiPraveen/simple-email-poc.git'
            }
        }
        stage('Sonar Analysis') {
            steps {
                bat 'mvn clean package'
                bat ''' mvn sonar:sonar -Dsonar.url=http://localhost:9000/ -Dsonar.login=squ_501049b6ce4f369b8cef583bd16e4c9ac230fa07 -Dsonar.projectName=email-poc -Dsonar.java.binaries=target/classes -Dsonar.projectKey=email-poc '''
            }
        }
    }
} 