def call(Map args){
    def enabled = args['withScreenOn'] || args['withScreenOn'] == null

    Closure cAT = {
        if(enabled){
            turnOnScreen()
        }
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
            if(enabled){
                turnOffScreen()
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
