def call(){
	dir( 'srcRetrieved' ){
		withCredentials( [usernamePassword(credentialsId: env.SFDX_CREDENTIALS, usernameVariable: 'SFDC_USN', passwordVariable: 'SFDC_CONSUMER_KEY') ] ){
			def statusCode = sh( script:"sfdx force:mdapi:retrieve -u ${SFDC_USN} -r . -k ../artifacts_folder/package.xml --apiversion ${env.API_VERSION} --wait 30 -s", returnStatus: true );
			echo "INFO: Metadata retrieved with status code: ${statusCode}";
		}
	}
	sh "unzip srcRetrieved/unpackaged.zip -d srcRetrieved && mv srcRetrieved/unpackaged.zip artifacts_folder/srcRetrieved.zip";
	archiveArtifacts allowEmptyArchive: true, artifacts: "artifacts_folder/srcRetrieved.zip", fingerprint: true;
}