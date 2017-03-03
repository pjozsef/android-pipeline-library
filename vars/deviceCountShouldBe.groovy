def call(expectedCount) {
    if(expectedCount instanceof String){
        expectedCount = expectedCount as Integer
    }

    Class DeviceLister = ((GroovyClassLoader) this.class.classLoader).parseClass new File("./lib/DeviceLister.groovy")
    def size = DeviceLister.newInstance().availableDevices().size()

    if (size != expectedCount) {
        currentBuild.result = 'FAILURE'
        def message = "Currently available devices are ${available.size()}, but should be ${expectedCount}\nAvailable devices:\n${available.join("\n")}"
        throw new IllegalStateException(message)
    }
}