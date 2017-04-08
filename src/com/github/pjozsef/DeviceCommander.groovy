package com.github.pjozsef

class DeviceCommander {
    def androidHome
    def devices

    DeviceCommander(androidHome, devices) {
        this.androidHome = androidHome
        this.devices = devices
    }

    def execute(command, progress = null){
        for(device in devices){
            if(progress){
                progress "Executing $command on $device"
            }
            "${androidHome}platform-tools/adb -s $device $command".execute().text
        }
    }
}