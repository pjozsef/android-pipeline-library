def call() {
    deviceCommand command: "shell input keyevent KEYCODE_POWER", verbose: true
}