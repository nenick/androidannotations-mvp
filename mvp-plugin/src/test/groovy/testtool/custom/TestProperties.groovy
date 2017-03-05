package testtool.custom

trait TestProperties implements TestProperties1 {

    // WOW? no more than 9 properties or it makes boom
    def androidProjectName = "test-app"
    def androidApplicationProjectId = "de.nenick.test.application"
    def androidLibraryProjectId = "de.nenick.test.library"
    def pluginVersion = "1.0.0-SNAPSHOT"
    def androidPluginVersion = "2.2.3"
    def compileSdkVersion = "25"
    def buildToolsVersion = "25.0.2"
}