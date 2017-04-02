package tools

trait TestProperties implements TestProperties1 {


    // WOW? no more than 9 properties or it makes boom
    def androidProjectName = "test-app"
    String androidApplicationProjectId = "de.nenick.test.application"
    def androidLibraryProjectId = "de.nenick.test.library"
    def pluginVersion = readProjectVersion() + "-SNAPSHOT"
    def androidPluginVersion = "2.3.0"
    def compileSdkVersion = "25"
    def buildToolsVersion = "25.0.1"

    def readProjectVersion() {
        Properties properties = new Properties()
        properties.load(getClass().getClassLoader().getResourceAsStream("mvp-plugin.properties"))
        return properties.version
    }
}