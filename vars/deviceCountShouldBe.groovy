def call(expectedCount) {
    if(expectedCount instanceof String){
        expectedCount = expectedCount as Integer
    }

    echo devicesRaw(verbose: true)

    def size = devices(availableOnly: true).size()

    if (size != expectedCount) {
        currentBuild.result = 'FAILURE'
        def message = "Currently available devices are ${size}, but should be ${expectedCount}!"
        throw new IllegalStateException(message)
    }
}