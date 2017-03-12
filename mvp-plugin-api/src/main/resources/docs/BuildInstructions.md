# Build Instructions

After you clone the project ..
You need a device or emulator for instrumentation tests

### Command Line

Run inside project root `./gradlew build` to check that all is working.

* mvp-plugin-api
    * build plugin api
* mvp-plugin
    * build plugin
* mvp-plugin-test
    * build module with plugin
    * run lint checks
    * run unit tests
    * run instrumentation tests
