import groovy.json.JsonSlurper

def call(Map args) {
    def module = args['module']?.replace(":", "")?.concat(':') ?: ''
    def screenEnabled = args['withScreenOn'] || args['withScreenOn'] == null
    def retryCount = args['withRetryCount'] ?: 1
    def runTrulyParallel = args['runTrulyParallel']
    def stepNames = args['withStepNames']
    if(stepNames instanceof String){
        def tempMap = [:]
        tempMap.putAll(new JsonSlurper().parseText(stepNames))
        stepNames = tempMap
    }

    Closure simpleCat = {
        retry(retryCount) {
            sh "./gradlew ${module}cAT"
        }
    }

    Closure parallelCat = {
        sh "./gradlew $module:assembleAndroidTest"
        def tasks = [:]
        for(device in devices()){
            def id = device.name
            def stepName = stepNames?.get(id) ?: id
            tasks[stepName] = {
                withEnv(["ANDROID_SERIAL=$id"]) {
                    simpleCat()
                }
            }
        }
        tasks['failFast'] = false
        parallel tasks
    }

    Closure cAT = {
        if (screenEnabled) {
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
            echo "Android Pipeline Error:"
            echo e.message
            throw e
        } finally {
            if (screenEnabled) {
                turnOffScreen()
            }
            if (args['andArchive']) {
                junit args['andArchive']
                archiveArtifacts args['andArchive']
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