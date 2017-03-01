def call(Map args){
    Closure cAT = {
        try {
            sh './gradlew cAT'
        } catch (e) {
            currentBuild.result = 'FAILURE'
            throw e
        } finally {
            if(args['andArchive']){
                junit args['andArchive']
                archiveArtifacts args['andArchive']
            }
        }
    }
    if(args['withLock']){
        lock(args['withLock']){
            cAT()
        }
    }else{
        cAT()
    }
}
