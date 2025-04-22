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
                sleep(5)
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Tomcat Deploy') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8080/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage("API Tests") {
            steps {
                dir('api-test') {
                    git 'https://github.com/LianMiranda/tasks-api-test.git'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy FrontEnd') {
            steps {
                dir('tasks-frontend') {
                    git 'https://github.com/LianMiranda/tasks-frontend.git'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat9(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8080/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
        stage('Functional Tests') {
            steps {
                dir('tasks-functional-tests') {
                    git 'https://github.com/LianMiranda/tasks-functional-tests.git'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy Prod'){
            steps{
                bat 'docker-compose build'
                bat 'docker-compose up -d' //-d para rodar em background
            }
        }
        stage('Health Check') {
            steps {
                sleep(5)
                dir('tasks-functional-tests') {
                    bat 'mvn verify -Dskip.surefire.tests'
                }
            }
        }
    }
    post {
        always {
           junit allowEmptyResults: true, stdioRetention: '', testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml, tasks-functional-tests/target/surefire-reports/*.xml, tasks-backend/target/failsafe-reports/*.xml'
        }  
    }
}
