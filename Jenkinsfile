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
        stage('Sonar Analysis') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_TOKEN') {
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.projectName='DeployBack' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_141c58ea5812d448f6f235552b8b1db921dec926 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**/Application.java"
                }
            }
        }
        stage('Quality Gate') {
            steps{
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
