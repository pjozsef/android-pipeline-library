class DeviceLister {
    def availableDevices(){
        def output = sh(script: "${env.ANDROID_HOME}platform-tools/adb devices", returnStdout: true)
        def outputLines = output
                .tokenize("\r?\n")
        def available = []
        outputLines
                .drop(1)
                .each {
            if (it.contains("device") && !it.contains("no device")) {
                available += it.split(" ")[0]
            }
        }
        return available
    }
}
