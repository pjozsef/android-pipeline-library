import com.github.pjozsef.DeviceLister

def call(expectedCount) {
    if(expectedCount instanceof String){
        expectedCount = expectedCount as Integer
    }

    def size = new DeviceLister().availableDevices().size()

    if (size != expectedCount) {
        currentBuild.result = 'FAILURE'
        def message = "Currently available devices are ${available.size()}, but should be ${expectedCount}\nAvailable devices:\n${available.join("\n")}"
        throw new IllegalStateException(message)
    }
}