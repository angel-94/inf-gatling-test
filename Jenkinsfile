pipeline {
    agent { docker { image 'maven:3.8.5-openjdk-17-slim' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
                sh 'mvn gatling:test'
            }
        }
    }
}
