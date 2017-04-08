package com.github.pjozsef

class DeviceCommander {
    def androidHome
    def devices

    DeviceCommander(androidHome, devices = null) {
        this.androidHome = androidHome
        this.devices = devices ?: new DeviceLister(androidHome).availableDevices()
    }

    def execute(command){
        for(device in devices){
            "${env.ANDROID_HOME}platform-tools/adb -s $device $command"
        }
    }
}