def call(Map args){
    def module = args['module']?.replace(":", "")?.concat(':') ?: ''
    try{
        sh "./gradlew ${module}test"
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
