pipeline {
    agent any
    stages {
        stage('Build Backend') {
            steps {
               bat 'mvn clean package -DskipTests=true' //Bat é para executar comandos no Windows
            }
        }
        stage('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }
    }

}