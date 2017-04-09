import com.github.pjozsef.DeviceCommander
import com.github.pjozsef.DeviceLister

def call() {
    def devices = new DeviceLister(env.ANDROID_HOME).availableDevices()
    new DeviceCommander(env.ANDROID_HOME, devices).execute("shell input keyevent KEYCODE_POWER"){
        echo it
    }
}