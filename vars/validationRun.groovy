def call(){
    commentHandler.editLastMessage( "+ Running Salesforce Validation" );

    testMR     = utils.getPRTests();
    testToRun  = checkTestToRun( testMR );

    def statusCode;
    def statusMessage;
    dir( env.PATH_SALESFORCE ){
        statusCode = validatePackage( 'srcToDeploy', testToRun );
        archiveArtifacts allowEmptyArchive: true, artifacts: "validate.json", fingerprint: true;
        def sfdxResponse = readJSON file: 'validate.json';
        statusMessage = sfdxResponse.result.status;
        if( statusMessage.toString().trim() == 'Succeeded' ){
            env.sfdxBuildId = sfdxResponse.result.id;
        }
    }

    utils.handleValidationErrors( 'Validation', statusCode.toString().trim(), statusMessage);
}

def validatePackage( folder, testToRun ){
    def testString = ( testToRun.trim() != '' ) ? "-l RunSpecifiedTests -r ${testToRun}" : '';
    def statusCode;
    withCredentials( [usernamePassword(credentialsId: env.SFDX_CREDENTIALS, usernameVariable: 'SFDC_USN', passwordVariable: 'SFDC_CONSUMER_KEY') ] ){
        statusCode = sh( script:"sfdx force:mdapi:deploy -u ${SFDC_USN} -d ${folder} ${testString} --apiversion ${env.API_VERSION} --wait 90 -c --json > validate.json", returnStatus: true );
        echo "INFO: Validation returned status code: ${statusCode}";
    }
    return statusCode;
}

def checkTestToRun( testToRun ){
    def textTestMR = 'Elimina este mensaje y escribe en su lugar las clases de test a ejecutar durante la validacion de esta Merge Request, dejala vacia si no quieres que se ejecuten tests';
    if( testToRun == null || testToRun.equalsIgnoreCase( textTestMR ) ){
        testToRun = '';
    }

    def hasApexClasses;
    def hasApexTriggers;
    dir( env.PATH_SALESFORCE ){
        hasApexClasses  = sh( script: "if [ -d 'srcToDeploy/classes' ]; then echo 'true'; else echo 'false'; fi", returnStdout: true );
        hasApexTriggers = sh( script: "if [ -d 'srcToDeploy/triggers' ]; then echo 'true'; else echo 'false'; fi", returnStdout: true );
    }

    if( ( hasApexClasses.trim() == 'true' || hasApexTriggers.trim() == 'true' ) && testToRun.trim() == '' ){
        def errorMessage = 'The package contains Apex code and the description has no test classes added';
        def gitErrorMessage = "+ **Execution blocked because no test classes were added.**  Please add test classes to section *TestToRun*.";
        commentHandler.editLastMessage( gitErrorMessage );
        error "$errorMessage";
    }
    return testToRun;
}