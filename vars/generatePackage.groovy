import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

def call(){
    /*def packageBody = "${env.JSON_PACKAGE}";
    echo "package : ${packageBody}";
    println( new JsonBuilder( "${packageBody}" ) );
    println( JsonOutput.prettyPrint( packageBody ) );
    println( JsonOutput.prettyPrint( new JsonBuilder( "${packageBody}" ).toString() ) );
    println( JsonOutput.prettyPrint( new JsonBuilder( "${packageBody}" ).toString() ).toString() );
    //println( new JsonSlurper().parseText( JsonOutput.prettyPrint( packageBody ).toString() ) );
    //packageBody = new JsonBuilder( "${packageBody}" ).toString().replaceAll( "\n", "" );
    println( new JsonSlurper().parseText( packageBody ) );
    println( new JsonSlurper().parseText( packageBody ).toString() );
    println( JsonOutput.prettyPrint( new JsonSlurper().parseText( packageBody ).toString() ) );
    packageBody = JsonOutput.prettyPrint( new JsonSlurper().parseText( packageBody ).toString() );
    echo "package : ${packageBody}";*/
    
    def packageBody = "${env.JSON_PACKAGE}";
    echo "package : ${packageBody}";
    packageBody = new JsonBuilder( "${packageBody}" ).toString().replaceAll( "\n", "" );
    
    sh "python3.7 ${env.PATH_SCRIPTS}/generatePackage/generatePackage.py -j ${packageBody} -a ${env.API_VERSION}";
    sh "if [ ! -d \"artifacts_folder\" ]; then mkdir artifacts_folder; fi";
    sh "mv package.xml artifacts_folder/package.xml";
    archiveArtifacts allowEmptyArchive: true, artifacts: 'artifacts_folder/package.xml', fingerprint: true;
}