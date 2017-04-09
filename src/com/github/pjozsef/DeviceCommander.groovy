package com.github.pjozsef

class DeviceCommander {
    def androidHome
    def devices

    DeviceCommander(androidHome, devices) {
        this.androidHome = androidHome
        this.devices = devices
    }

    def execute(command, progress = null) {
        for (device in devices) {
            progress?.call("Executing $command on $device")
            def output = "${androidHome}platform-tools/adb -s $device $command".execute().text
            progress?.call(output)
        }
    }
}