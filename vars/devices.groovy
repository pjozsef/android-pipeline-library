import com.github.pjozsef.Device

def call(Map args) {
    def devices = []

    def lines = devicesRaw(verbose: true).tokenize("\r?\n")
    for (line in lines) {
        def pattern = ~/^(?<name>[^\s\\]+)\s+(?<status>[a-z]+)\s+(usb:(?<usb>[\w\-\.]+))?(\s*product:(?<product>[-\w]+)\s+model:(?<model>[-\w]+)\s+device:(?<device>[-\w]+)(\stransport_id:(?<transportId>[0-9a-zA-Z]+))?)?$/
        def matcher = (line =~ pattern)
        if (matcher.matches()) {
            devices += new Device(
                    matcher.group("name"),
                    matcher.group("status"),
                    matcher.group("usb"),
                    matcher.group("product"),
                    matcher.group("model"),
                    matcher.group("device"),
                    matcher.group("transportId")
            )
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