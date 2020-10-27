def call(){
    
    commentHandler.editLastMessage( "+ Running Salesforce Deployment" );
    
    def statusCode;
    dir( env.PATH_SALESFORCE ){
        testMR = utils.getPRTests();
        if (testMR != null && testMR.length() > 0){
            statusCode = sfdxQuickDeploy();
        } else {
            statusCode = sfdxDeployNoTests( 'srcToDeploy' );
        }
        archiveArtifacts allowEmptyArchive: true, artifacts: "deploy.json", fingerprint: true;
        def sfdxResponse = readJSON file: 'deploy.json';
        statusMessage = sfdxResponse.result.status;
    }

    utils.handleValidationErrors( 'Deployment', statusCode.toString().trim(), statusMessage );
}

def sfdxQuickDeploy(){
    def statusCode;
    withCredentials( [usernamePassword(credentialsId: env.SFDX_CREDENTIALS, usernameVariable: 'SFDC_USN', passwordVariable: 'SFDC_CONSUMER_KEY') ] ){
        statusCode = sh( script:"sfdx force:mdapi:deploy -u ${SFDC_USN} -q ${env.sfdxBuildId} --apiversion ${env.API_VERSION} --wait 90 --json > deploy.json", returnStatus: true );
    }
    return statusCode;
}

def sfdxDeployNoTests(folder){
    def statusCode;
    withCredentials( [usernamePassword(credentialsId: env.SFDX_CREDENTIALS, usernameVariable: 'SFDC_USN', passwordVariable: 'SFDC_CONSUMER_KEY') ] ){
        statusCode = sh( script:"sfdx force:mdapi:deploy -u ${SFDC_USN} -d ${folder} --apiversion ${env.API_VERSION} --wait 90 --json > deploy.json", returnStatus: true );
    }
    return statusCode;
}