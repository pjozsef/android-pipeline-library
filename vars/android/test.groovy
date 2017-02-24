def call(){
    try{
        sh './gradlew test'
    } catch(e){
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        junit '**/test-results/**/*.xml'
        archiveArtifacts '**/test-results/**/*.xml'
    }
}
