def call(Map args) {
    def devices = args['devices'] ?: devices(availableOnly: true)
    def verbose = args['verbose']
    def result = deviceCommand command: "logcat -d", devices: devices, verbose: verbose

    if (args['andArchive']) {
        sh 'mkdir -p logs'
        result.each { entry ->
            def file = new File("${env.WORKSPACE}/logs/logcat_${entry.key.name}.log")
            file.createNewFile()
            file.write entry.value
        }
        archiveArtifacts "logs/logcat_*.log"
    }

    return result
}