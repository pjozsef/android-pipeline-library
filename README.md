# android-pipeline-library
Public repository for Android based Jenkins pipeline steps.
This library contains a few pipeline steps that can come handy when dealing with real devices in Jenkins pipelines.

## Pipeline steps
### build
Builds and archives. If there is no argument supplied, archiving is skipped.
```groovy
build andArchive: '**/*.apk'
```
| argument      | description                                    | type      | mandatory |
| :-:           | :-:                                            | :-:       | :-:       |
| andArchive    | the path specifying the artifacts to archive   | String    | ✘         |

### lint
Lints and archives the lint results. If there is no argument supplied, archiving is skipped.
```groovy
lint andArchive: '**/lint-results*.*'
```
| argument      | description                                    | type      | mandatory |
| :-:           | :-:                                            | :-:       | :-:       |
| andArchive    | the path specifying the artifacts to archive   | String    | ✘         |

### test
Runs the unit tests, archives and publishes the test results. This step ensures that the results are archived/published even if the build fails.
If there is no argument supplied, archiving/publishing is skipped.
```groovy
lint andArchive: '**/lint-results*.*'
```
| argument      | description                                    | type      | mandatory |
| :-:           | :-:                                            | :-:       | :-:       |
| andArchive    | the path specifying the artifacts to archive and publish to junit   | String    | ✘         |

### instrumentationTest
Runs the instrumentation tests, archives and publishes the test results.  This step ensures that the results are archived/published even if the build fails.
If there is no andArchive argument supplied, archiving/publishing is skipped.
The withLock argument requires the presence of the [Lockable Resources Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Lockable+Resources+Plugin).
The withScreenOn argument is set to true by default.
```groovy
instrumentationTest withScreenOn: true, withLock: 'android-device-farm', andArchive: '**/androidTest-results/connected/*.xml'
```
| argument      | description                                    | type      | mandatory |
| :-:           | :-:                                            | :-:       | :-:       |
| withScreenOn  | When set to true, turns on the device screens before running the instrumentation tests and turns them off when the instrumentation tests are finished. | Boolean | ✘ |
| withLock      | Acquires a lock for the duration of this step, so other steps can not interfere with the devices. | String | ✘ |
| andArchive    | the path specifying the artifacts to archive and publish to junit   | String    | ✘         |

### deviceCountShouldBe
Checks whether there is as many connected devices as should be. If the available device count does not match the expected count, this step fails the build.
```groovy
deviceCountShouldBe 4
```
| argument      | description                                    | type           | mandatory |
| :-:           | :-:                                            | :-:            | :-:       |
| expectedCount | the number of devices that should be connected | Integer/String | ✔         |

### rebootDevices
Reboots all connected devices or only a selection.
If no device list is specified, restarts all devices.
The default time unit is seconds.
The withLock argument requires the presence of the [Lockable Resources Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Lockable+Resources+Plugin).
It is advised to set the sleep argument with the withLock argument, so other builds wait until the devices had enough time to reboot.
```groovy
rebootDevices withLock: 'android-device-farm', sleep: 120
```
| argument      | description                                    | type      | mandatory |
| :-:           | :-:                                            | :-:       | :-:       |
| devices       | The list of device IDs returned by adb devices that should be rebooted. Defaults to all devices when omitted. | List<String> | ✘ |
| withLock      | Acquires a lock for the duration of this step, so other steps can not interfere with the devices. | String | ✘ |
| sleep         | The amount of time to wait before the lock is released. Useful to ensure that the devices had enough time to reboot completely, before other jobs can run tests on them, etc.   | Integer    | ✘         |
| unit          | The time unit. Valid values are: 'NANOSECONDS', 'MICROSECONDS', 'MILLISECONDS', 'SECONDS', 'MINUTES', 'HOURS', 'DAYS'. Defaults to 'SECONDS'. | String | ✘ |

### toggleScreen
Toggles the screens on all devices. If the screen was on, this step turns it of, if it was off, this step turns it on.
This step takes no arguments.
```groovy
toggleScreen()
```

### turnOnScreen
Turns on the screens on all devices. If the screen was already on, this step has no effect.
This step takes no arguments.
```groovy
turnOnScreen()
```

### turnOffScreen
Turns off the screens on all devices. If the screen was already off, this step has no effect.
This step takes no arguments.
```groovy
turnOffScreen()
```