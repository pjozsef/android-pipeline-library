def call(Map args){
    def module = args['module']?.replace(":", "") ?: ''
    try{
        sh "./gradlew $module:lint"
    } catch(e){
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        if(args['andArchive']){
            archiveArtifacts args['andArchive']
        }
    }
}
