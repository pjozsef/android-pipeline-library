
import com.github.pjozsef.DeviceLister

def call(){
    def devices = new DeviceLister(env.ANDROID_HOME).availableDevices()
    for(device in devices) {
        def info1 = sh returnStdout: true, script: "${env.ANDROID_HOME}platform-tools/adb -s $device shell dumpsys input_method"
        def info2 = sh returnStdout: true, script: "${env.ANDROID_HOME}platform-tools/adb -s $device shell dumpsys power"
        if(info1.contains("mScreenOn=true") || info2.contains("Display Power: state=ON")){
            sh "${env.ANDROID_HOME}platform-tools/adb -s $device shell input keyevent KEYCODE_HOME"
            sh "${env.ANDROID_HOME}platform-tools/adb -s $device shell input keyevent KEYCODE_POWER"
        }
    }
}
