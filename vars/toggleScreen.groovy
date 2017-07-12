import com.github.pjozsef.DeviceCommander

def call() {
    def devices = devices(availableOnly: true)
    new DeviceCommander(env.ANDROID_HOME, devices).execute("shell input keyevent KEYCODE_POWER"){
        echo it
    }
}