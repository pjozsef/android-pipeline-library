package com.github.pjozsef

class DeviceLister {
    def androidHome

    DeviceLister(androidHome){
        this.androidHome = androidHome
    }

    def availableDevices(){
        def output = sh(script: "${androidHome}platform-tools/adb devices", returnStdout: true)
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
