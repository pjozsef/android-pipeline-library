def call(){
    for(device in devices(availableOnly: true)) {
        def info1 = sh returnStdout: true, script: "${env.ANDROID_HOME}/platform-tools/adb -s $device.name shell dumpsys input_method"
        def info2 = sh returnStdout: true, script: "${env.ANDROID_HOME}/platform-tools/adb -s $device.name shell dumpsys power"
        if(info1.contains("mScreenOn=true") || info2.contains("Display Power: state=ON")){
            sh "${env.ANDROID_HOME}/platform-tools/adb -s $device.name shell input keyevent KEYCODE_HOME"
            sh "${env.ANDROID_HOME}/platform-tools/adb -s $device.name shell input keyevent KEYCODE_POWER"
        }
    }
}
