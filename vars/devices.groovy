import com.github.pjozsef.Device

def call(Map args) {
    def devices = []

    def lines = devicesRaw(verbose: true).tokenize("\r?\n")
    for (line in lines) {
        def pattern = ~/^(?<name>\w+)\s+(?<status>[a-z]+)\s+usb:(?<usb>[\w\-\.]+)(\s+product:(?<product>[-\w]+)\s+model:(?<model>[-\w]+)\s+device:(?<device>[-\w]+))?$/
        def matcher = (line =~ pattern)
        if (matcher.matches()) {
            devices += new Device(
                    matcher.group("name"),
                    matcher.group("status"),
                    matcher.group("usb"),
                    matcher.group("product"),
                    matcher.group("model"),
                    matcher.group("device"))
        }
    }

    if (args?.get('availableOnly')) {
        def availableDevices = []
        for (device in devices) {
            if (device.status == "device") {
                availableDevices += device
            }
        }
        devices = availableDevices
    }

    return devices
}