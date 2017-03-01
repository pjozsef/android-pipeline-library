def call(String archive){
    sh './gradlew clean build -x lint -x test'
    archiveArtifacts archive
}
