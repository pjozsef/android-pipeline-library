def call(Map args){
    sh './gradlew clean build -x lint -x test'
    if(args['andArchive']){
        archiveArtifacts args['andArchive']
    }
}