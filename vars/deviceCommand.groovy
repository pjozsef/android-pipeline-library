def call(Map args){
    def command = args['command']
    def devices = args['devices'] ?: devices(availableOnly: true)
    def verbose = args['verbose']
    def result = [:]

    for (device in devices) {
        if (verbose) {
            echo "Executing $command on $device"
        }

        def output = "${env.ANDROID_HOME}/platform-tools/adb -s $device.name $command".execute().text

        if (verbose) {
            echo output
        }
        result[device] = output
    }

    return result
}