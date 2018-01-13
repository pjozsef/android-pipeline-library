# android-pipeline-library
Public repository for Android based Jenkins pipeline steps.
This library contains a few pipeline steps that can come handy when dealing with real devices in Jenkins pipelines.

## How to use in your own Jenkins setup
If you are not sure about how to use shared libraries, take a look at the [documentation](https://jenkins.io/doc/book/pipeline/shared-libraries/#using-libraries).

## Pipeline steps
All steps can be found under the vars folder.

### androidBuild
Builds and archives. If there is no argument supplied, archiving is skipped.
```groovy
androidBuild andArchive: '**/*.apk'
```
| argument      | description                                    | type      | mandatory |
| :-:           | :-:                                            | :-:       | :-:       |
| andArchive    | The path specifying the artifacts to archive   | String    | ✘         |

### androidLint
Lints and archives the lint results. If there is no argument supplied, archiving is skipped.
```groovy
androidLint andArchive: '**/lint-results*.*'
```
| argument      | description                                    | type      | mandatory |
| :-:           | :-:                                            | :-:       | :-:       |
| andArchive    | The path specifying the artifacts to archive   | String    | ✘         |

### androidTest
Runs the unit tests, archives and publishes the test results. This step ensures that the results are archived/published even if the build fails.
If there is no argument supplied, archiving/publishing is skipped.
```groovy
androidTest andArchive: '**/test-results/**/*.xml'
```
| argument      | description                                    | type      | mandatory |
| :-:           | :-:                                            | :-:       | :-:       |
| andArchive    | The path specifying the artifacts to archive and publish to junit   | String    | ✘         |

### androidInstrumentationTest
Runs the instrumentation tests, archives and publishes the test results.  This step ensures that the results are archived/published even if the build fails.
If there is no andArchive argument supplied, archiving/publishing is skipped.
The withLock argument requires the presence of the [Lockable Resources Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Lockable+Resources+Plugin).
Retry count can be specified if needed. By default retry count is 1.
The withScreenOn argument is set to true by default.
```groovy
androidInstrumentationTest withScreenOn: true, withLock: 'android-device-farm', withRetryCount: 2, andArchive: '**/androidTest-results/connected/*.xml'
```
| argument      | description                                    | type      | mandatory |
| :-:           | :-:                                            | :-:       | :-:       |
| withScreenOn  | When set to true, turns on the device screens before running the instrumentation tests and turns them off when the instrumentation tests are finished. Set to true by default. | Boolean | ✘ |
| withLock      | Acquires a lock for the duration of this step, so other steps can not interfere with the devices. | String | ✘ |
| withRetryCount | Specifies whether this step should be retried a number of times in case the instrumentation test faild. Defaults to 1 | Integer | ✘ |
| andArchive    | The path specifying the artifacts to archive and publish to junit   | String    | ✘         |

### deviceCount
Checks whether there are as many connected devices as should be. 
If the available device count does not match the expected count and there is no action specified, this step fails the build. 
If there is an action provided, the action will be invoked, the build will not fail.
```groovy
deviceCount shouldBe: 4,
            action: { devices, message ->
                //take some action, like notifying a slack channel
                //slackMessage channel: 'CI', text: message
             }
```
| argument      | description                                    | type           | mandatory |
| :-:           | :-:                                            | :-:            | :-:       |
| shouldBe | The number of devices that should be connected | Integer/String | ✔         |
| action | The closure/custom action to be invoked if missing devices are detected. It gets the list of available devices and a default error message as parameters. | Closure (List
Device>, String) -> () | ✘         |

### devices
Returns the list of devices as List\<Device\>
```groovy
def devices = devices availableOnly: true
```
| argument      | description                                    | type           | mandatory |
| :-:           | :-:                                            | :-:            | :-:       |
| availableOnly | When set to true, only return devices with adb state `device`, filtering out `offline`, `unauthorized` devices. Default value is false | Boolean |   ✘  |

### devicesRaw
Returns the raw output of `adb devices`.
When verbose parameter is true, `adb devices -l` is called.
```groovy
def adbOutput = devicesRaw(verbose: true)
```
| argument      | description                                    | type           | mandatory |
| :-:           | :-:                                            | :-:            | :-:       |
| verbose | When set to true, calls `adb devices -l` instead of `adb devices`. Default value is false | Boolean |   ✘  |

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
| devices       | The list of Device instances that should be rebooted. Defaults to all devices when omitted. | List\<Device\>  | ✘ |
| withLock      | Acquires a lock for the duration of this step, so other steps can not interfere with the devices. | String | ✘ |
| sleep         | The amount of time to wait before the lock is released. Useful to ensure that the devices had enough time to reboot completely, before other jobs can run tests on them, etc.   | Integer    | ✘         |
| unit          | The time unit. Valid values are: 'NANOSECONDS', 'MICROSECONDS', 'MILLISECONDS', 'SECONDS', 'MINUTES', 'HOURS', 'DAYS'. Defaults to 'SECONDS'. | String | ✘ |


### deviceCommand
Runs adb commands on the devices.
For more info about the possible commands, please consult [The official ADB documentation](https://developer.android.com/studio/command-line/adb.html#issuingcommands)
Returns the console output as plain String.
Example: By supplying `shell input keyevent KEYCODE_POWER` as parameter, `adb shell input keyevent KEYCODE_POWER` will be run on each device.
```groovy
def adbOutput = deviceCommand command: "shell input keyevent KEYCODE_POWER", verbose: true
```
| argument      | description                                    | type           | mandatory |
| :-:           | :-:                                            | :-:            | :-:       |
| command | The command to run on the devices | String |   ✔  |
| devices | When given, the command is only run on the specified devices. Defaults to all available devices  | List\<Device\> |   ✘  |
| verbose | When set to true, logs the command String and the result of the command to the Jenkins Pipeline log. Default value is false | Boolean |   ✘  |


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

## Utility classes

### Device
This class represents the rows of the `adb devices -l` command. 
Instances of this class are used in some of the Pipeline steps listed above.
[Located here](https://github.com/pjozsef/android-pipeline-library/blob/master/src/com/github/pjozsef/Device.groovy)

Given the following output:
```shell
List of devices attached
0123456789ABCDEF       device usb:339804160X
R32D203L00M            device usb:339804160X product:mantaray model:Nexus_10 device:manta
```

The corresponding Devices objects are:
```
Device(0123456789ABCDEF, device, 339804160X, null, null, null)
Device(R32D203L00M, device, 339804160X, mantaray, Nexus_10, manta)
```

## Other project
[general-pipeline-library](https://github.com/pjozsef/general-pipeline-library), a Jenkins Pipeline library that focuses on more general tasks, like Slack message handling.


## Real life example
For those who'd like to see something more complex than code snippets, [this Jenkinsfile](https://github.com/emartech/android-mobile-engage-sdk/blob/master/Jenkinsfile) is a real life usage of this library.


## Legal stuff
This library is provided as-is, under the MIT license, without any warranties, what so ever.<br>
Pull requests are welcome, if you find something.
