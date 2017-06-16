package com.github.pjozsef

class DeviceLister implements Serializable {
    def androidHome

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
        def devices = []

        def lines = devicesRawVerbose().tokenize("\r?\n")
        for(line in lines) {
            def pattern = ~/^(?<name>\w+)\s+(?<status>[a-z]+)\s+usb:(?<usb>\w+)(\s+product:(?<product>[-\w]+)\s+model:(?<model>[-\w]+)\s+device:(?<device>[-\w]+))?$/
            def matcher = (line =~ pattern)
            if(matcher.matches()){
                devices += new Device(
                        matcher.group("name"),
                        matcher.group("status"),
                        matcher.group("usb"),
                        matcher.group("product"),
                        matcher.group("model"),
                        matcher.group("device"))
            }
        }

        return devices
    }

    def availableDevices() {
        def availableDevices = []

        for(device in devices()){
            if(device.status == "device"){
                availableDevices += device
            }
        }
        return availableDevices
    }
}
