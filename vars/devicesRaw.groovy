def call(Map args){
    def command = "${env.ANDROID_HOME}platform-tools/adb devices"
    if (args['verbose']) {
        command += " -l"
    }
    return sh command
}
