import com.github.pjozsef.DeviceLister

def call() {
    def devices = new DeviceLister(env.ANDROID_HOME).availableDevices()
    for(device in devices){
        sh "${env.ANDROID_HOME}platform-tools/adb -s $device shell input keyevent KEYCODE_POWER"
    }
}