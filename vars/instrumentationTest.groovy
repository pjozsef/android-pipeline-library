def call(Map args){
    Closure cAT = {
        pressPowerButton()
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
            pressPowerButton()
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
