def call(Map args) {
    def devices = args['devices'] ?: devices(availableOnly: true)
    def verbose = args['verbose']
    def result = deviceCommand command: "logcat -d", devices: devices, verbose: verbose

    if (args['andArchive']) {
        sh 'mkdir -p logs'
        for (entry in result) {
            def command = "echo $entry.value > logs/logcat_${entry.key.name}.log"
            sh command
        }
        archiveArtifacts "logs/logcat_*.log"
    }

    return result
}