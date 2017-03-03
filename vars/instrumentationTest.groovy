def call(Map args){
    def enabled = args['withScreenOn'] || args['withScreenOn'] == null
    Closure withScreen = {
        if(enabled){
            pressPowerButton()
        }
    }
    Closure cAT = {
        withScreen()
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
            withScreen()
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
