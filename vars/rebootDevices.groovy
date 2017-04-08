import com.github.pjozsef.DeviceCommander
import com.github.pjozsef.DeviceLister

def call(Map args){
    Closure reboot = {
        def devices = args['devices'] ?: new DeviceLister(env.ANDROID_HOME).availableDevices()
        new DeviceCommander(env.ANDROID_HOME, devices).execute("reboot")
    }
    Closure wait = {
        if(args['sleep']){
            def duration = args['sleep']
            def unit = args['unit'] ?: 'SECONDS'
            sleep time: duration, unit: unit
        }
    }

    if(args['withLock']){
        lock(args['withLock']){
            reboot()
            wait()
        }
    }else{
        reboot()
        wait()
    }
}
