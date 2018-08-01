def call(Map args){
    def module = args['module']?.replace(":", "") ?: ''
    sh "./gradlew $module:clean $module:build -x lint -x test"
    if(args['andArchive']){
        archiveArtifacts args['andArchive']
    }
}