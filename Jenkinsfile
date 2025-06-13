pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'   // Make sure this is installed and named in Jenkins
        jdk 'JDK 24'          // Change to JDK 21 or JDK 24 if you've installed it
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=false"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Allure Report') {
            steps {
                sh 'mvn allure:report'
                sh 'mvn allure:serve'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
        failure {
            echo 'Build or tests failed!'
        }
    }
}
