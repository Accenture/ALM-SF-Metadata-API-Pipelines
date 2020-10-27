@Library('ALM_SF_MDAPI_LIBRARY@master') _

pipeline {
    agent any
    stages{
        stage('Pre Configuration'){
            steps{
                preconfigEasyCommit()
            }
        }
        stage('Configuration'){
            steps{
                configEasyCommit()
            }
        }
        stage('Generate Package'){
            steps{
                generatePackage()
            }
        }
        stage('Retrieve Changes'){
            steps{
                retrieveChanges()
            }
        }
        stage('Merge Changes'){
            steps{
                mergeChanges()
            }
        }
        stage('Commit Changes'){
            steps{
                commitChanges()
            }
        }
    }
}