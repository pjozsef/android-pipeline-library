package com.github.pjozsef

class DeviceLister {
    def androidHome

    DeviceLister(androidHome){
        this.androidHome = androidHome
    }

    def availableDevices(){
        def output = "${androidHome}platform-tools/adb devices".execute().text
        def outputLines = output
                .tokenize("\r?\n")
                .drop(1)
        def available = []
        for(line in outputLines){
            if (line.contains("device")) {
                available += line.split("\\s+")[0]
            }
        }
        return available
    }
}