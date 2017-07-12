def call(Map args){
    def command = "${env.ANDROID_HOME}/platform-tools/adb devices"
    if (args?.get('verbose')) {
        command += " -l"
    }
    return sh (returnStdout: true, script: command)
}