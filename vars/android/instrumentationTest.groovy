def call(){
    try{
        sh './gradlew cAT'
    } catch(e){
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        junit '**/test-results/**/*.xml'
        archiveArtifacts '**/test-results/**/*.xml'
    }
}
