import com.github.pjozsef.DeviceLister

def call(expectedCount) {
    if(expectedCount instanceof String){
        expectedCount = expectedCount as Integer
    }

    def size = new DeviceLister(env.ANDROID_HOME).availableDevices().size()

    if (size != expectedCount) {
        currentBuild.result = 'FAILURE'
        def message = "Currently available devices are ${size}, but should be ${expectedCount}!}"
        throw new IllegalStateException(message)
    }
}