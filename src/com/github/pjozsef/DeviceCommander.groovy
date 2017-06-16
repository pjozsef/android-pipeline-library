package com.github.pjozsef

class DeviceCommander implements Serializable {
    def androidHome
    def devices

    DeviceCommander(androidHome, devices) {
        this.androidHome = androidHome
        this.devices = devices
    }

    def execute(command, progress = null) {
        def result = [:]
        for (device in devices) {
            progress?.call("Executing $command on $device")
            def output = "${androidHome}platform-tools/adb -s $device.name $command".execute().text
            progress?.call(output)

            result[device] = output
        }
        return result
    }
}
