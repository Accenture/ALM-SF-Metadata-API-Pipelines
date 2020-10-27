def call(){

    commentHandler.editLastMessage( "+ Running PMD Analysis" );
    
    def ruleSetPath         = "${env.PATH_SCRIPTS}/pmd/rules.xml";
    def reportPath          = "${env.PATH_SALESFORCE}/artifacts_folder/pmd.html";
    def reportPathFormatted = "${env.PATH_SALESFORCE}/artifacts_folder/pmdReport.html";
    def deployFolderPath    = "${env.PATH_SALESFORCE}/srcToDeploy";
    
    sh "mkdir -p ${env.PATH_SALESFORCE}/artifacts_folder"
    
    def statusCode = sh( script: "run.sh pmd -d $deployFolderPath -f html -R $ruleSetPath -reportfile $reportPath", returnStatus: true );
    
    def pmdMsg = "+ PMD Analysis finished with status **${statusCode}**";

    if( fileExists( "$reportPath" ) ){
        archiveArtifacts allowEmptyArchive: true, artifacts: "${reportPath}", fingerprint: true;

        sh "python3.7 ${env.PATH_SCRIPTS}/pmd/createReport.py -r $reportPath -o $reportPathFormatted -s srcToDeploy/"
        archiveArtifacts allowEmptyArchive: true, artifacts: "${reportPathFormatted}", fingerprint: true;

        pmdMsg += " [PMD Report]( ${env.BUILD_URL}artifact/artifacts_folder/pmdReport.html )";
    }

    commentHandler.editLastMessage( pmdMsg );
}