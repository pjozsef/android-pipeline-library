def call(Map args) {
    def expectedCount = args['shouldBe']
    if(expectedCount instanceof String){
        expectedCount = expectedCount as Integer
    }

    echo devicesRaw(verbose: true)

    def devices = devices(availableOnly: true)
    def size = devices.size()

    if(size != expectedCount) {
        def message = "Currently available devices are ${size}, but should be ${expectedCount}!"
        echo message
        if(args['action']){
            args['action'](devices, message)
        } else {
            error(message)
        }
    }
}
