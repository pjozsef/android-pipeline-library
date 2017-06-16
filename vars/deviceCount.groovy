import com.github.pjozsef.DeviceLister

def call(Map args) {
    def expectedCount = args['shouldBe']
    if(expectedCount instanceof String){
        expectedCount = expectedCount as Integer
    }

    def deviceLister = new DeviceLister(env.ANDROID_HOME)

    echo deviceLister.devicesRawVerbose()

    def devices = deviceLister.availableDevices()
    def size = devices.size()

    if(size != expectedCount) {
        def message = "Currently available devices are ${size}, but should be ${expectedCount}!"
        echo message
        if(args['action'){
            args['action'](devices)
        } else {
            currentBuild.result = 'FAILURE'
            throw new IllegalStateException(message)
        }
    }
}
