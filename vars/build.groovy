def call(String andArchive){
    sh './gradlew clean build -x lint -x test'
    archiveArtifacts andArchive
}
