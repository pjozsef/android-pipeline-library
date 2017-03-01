def call(Map args){
    try{
        sh './gradlew lint'
    } catch(e){
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        if(args['andArchive']){
            archiveArtifacts args['andArchive']
        }
    }
}
