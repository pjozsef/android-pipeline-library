def call(Map args){
    try{
        sh './gradlew test'
    } catch(e){
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        if(args['andArchive']){
            junit args['andArchive']
            archiveArtifacts args['andArchive']
        }
    }
}
