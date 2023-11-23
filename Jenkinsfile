pipeline {
     agent any
     stages {

        stage('Checkout') {
            steps {
                git branch: 'MalekBenRabah', url: 'https://github.com/MedFediJatlaoui/gestion-station-ski.git'
            }
        }
        stage('mvn clean') {
             steps {
                echo "Maven Clean";
                 sh 'mvn clean';
             }
        }

        stage('mvn compile'){
            steps{
                echo "Maven Compile";
                sh 'mvn compile';
            }
        }

         stage('junit & mockito') {
            steps {
                sh 'mvn test'
            }
         }


        stage('sonar'){
           steps{
                echo "Sonar";
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
           }
        }




        stage('mvn package'){
          steps{
              echo "mvn package";
              sh 'mvn package -DskipTests=true'
          }
        }

        stage('nexus') {
             steps{
                 sh "mvn deploy -DskipTests"
             }
        }

         stage('docker image') {
            steps{
                sh 'docker build -t stationsky .'
                sh 'docker tag stationsky:latest malekbenrabah/stationsky:latest'

            }
         }

          stage('docker push'){
              steps{
                  script{
                      sh 'docker login -u "malekbenrabah" -p "sFDbd)NRiRyc;x8" docker.io'
                      sh 'docker push malekbenrabah/stationsky:latest'
                  }
              }
          }

          stage('docker compose') {
              steps{
                  sh 'docker compose up -d'
              }
          }

         stage('mail') {
             steps {
                 script {
                     currentBuild.result = currentBuild.currentResult
                     def buildNumber = currentBuild.number
                     def buildUrl = env.BUILD_URL
                     def jobName= env.JOB_NAME

                     //prev
                     def previousResult = currentBuild.getPreviousBuild()?.result ?: 'Aucun résultat précédent'

                     //comparaison
                     def comparison = ''
                     if (previousResult != 'Aucun résultat précédent') {
                         if (currentBuild.result == previousResult) {
                             comparison = "Les résultats sont les mêmes que la construction précédente (${previousResult})."
                         } else {
                             comparison = "Les résultats sont différents de la construction précédente (${previousResult})."
                         }
                     } else {
                         comparison = "C'est la première construction."
                     }



                     emailext attachLog: true,
                         subject: "${jobName} - ${currentBuild.currentResult}",
                         body: "Le pipeline Jenkins :${jobName} a été exécuté avec le statut : ${currentBuild.currentResult}\nNuméro de build : ${buildNumber}\nURL de build : ${buildUrl}\n${comparison}",
                         to: "malek.benrabah2@gmail.com"
                 }
             }
         }



     }
}
