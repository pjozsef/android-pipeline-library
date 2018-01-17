import groovy.json.JsonSlurper

def call(Map args) {
    def enabled = args['withScreenOn'] || args['withScreenOn'] == null
    def retryCount = args['withRetryCount'] ?: 1
    def runTrulyParallel = args['runTrulyParallel']
    def stepNames = args['withStepNames']
    if(stepNames instanceof String){
        stepNames = new JsonSlurper().parseText(stepNames)
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
            def stepName = stepNames?.get(id) ?: id
            tasks[stepName] = {
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
