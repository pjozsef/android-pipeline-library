import groovy.json.JsonSlurper

def call(Map args) {
    def enabled = args['withScreenOn'] || args['withScreenOn'] == null
    def retryCount = args['withRetryCount'] ?: 1
    def runTrulyParallel = args['runTrulyParallel']
    def stageNames = args['withStageNames']
    if(stageNames instanceof String){
        stageNames = new JsonSlurper().parseText(stageNames)
    }

    Closure simpleCat = {
        retry(retryCount) {
            sh './gradlew cAT'
        }
    }

    Closure parallelCat = {
        def tasks = [:]
        for(device in devices()){
            def id = device.id
            def stageName = stageNames?.get(id) ?: id
            tasks[stageName] = {
                withEnv(["ANDROID_SERIAL=$id"]) {
                    retry(retryCount){
                        sh './gradlew cAT'
                    }
                }
            }
        }
        tasks['failFast'] = false

        parallel tasks
    }

    Closure cAT = {
        if (enabled) {
            turnOnScreen()
        }
        try {
            if(runTrulyParallel){
                parallelCat()
            }else {
                simpleCat()
            }
        } catch (e) {
            currentBuild.result = 'FAILURE'
            throw e
        } finally {
            if (args['andArchive']) {
                junit args['andArchive']
                archiveArtifacts args['andArchive']
            }
            if (enabled) {
                turnOffScreen()
            }
        }
    }

    if (args['withLock']) {
        lock(args['withLock']) {
            cAT()
        }
    } else {
        cAT()
    }
}
