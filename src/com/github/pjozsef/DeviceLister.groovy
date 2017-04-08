package com.github.pjozsef

class DeviceLister {
    def androidHome

    @com.cloudbees.groovy.cps.NonCPS
    DeviceLister(androidHome) {
        this.androidHome = androidHome
    }

    def devicesRawVerbose() {
        return "${androidHome}platform-tools/adb devices -l".execute().text
    }

    def devicesRaw() {
        return "${androidHome}platform-tools/adb devices".execute().text
    }

    def devices() {
        def deviceLines = devicesRaw()
                .tokenize("\r?\n")
                .drop(1)
        def result = [:]
        for(line in deviceLines){
            def split = line.tokenize("\t+")
            def name = split[0]
            def state = split[1]
            result[state] = result.get(state, []) + name
        }
        return result
    }

    def availableDevices() {
        return devices().get('device', [])
    }
}