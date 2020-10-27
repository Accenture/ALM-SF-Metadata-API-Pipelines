def call(){
    sh "python3.7 ${env.PATH_SCRIPTS}/mergeMetadata/mergeMetadata.py";
}