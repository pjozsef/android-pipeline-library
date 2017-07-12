def call(Map args){
    Closure reboot = {
        def devices = args['devices'] ?: devices(availableOnly: true)
        deviceCommand command: "reboot", devices: devices, verbose: true
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
    }
}
