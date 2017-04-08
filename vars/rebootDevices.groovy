def call(Map args){
    Closure reboot = {
        new DeviceCommander(env.ANDROID_HOME, args['devices']).execute("reboot")
    }
    Closure wait = {
        if(args['withSleep']){
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
