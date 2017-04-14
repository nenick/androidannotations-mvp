package tools

trait TestProperties implements TestProperties1 {


    // WOW? no more than 9 properties or it makes boom
    String androidApplicationProjectId = "de.nenick.test.application"
    def pluginVersion = readProjectVersion()
    def androidPluginVersion = "2.3.0"
    def androidApt = readLibraryVersions().AndroidApt
    def compileSdkVersion = "25"
    def buildToolsVersion = "25.0.1"
    def androidAnnotations = readLibraryVersions().AndroidAnnotations
    def androidAnnotationsApi = readLibraryVersions().AndroidAnnotationsApi

    def readLibraryVersions() {
        def result = [:]
        new File("gradle/library.gradle").getText().eachMatch(/\s*([a-zA-Z]*)\s*:\s*([a-zA-Z:.0-9-"]*)\s*/, {
            result.put(it[1], it[2])
        })
        return result
    }

    def readProjectVersion() {
        Properties properties = new Properties()
        properties.load(getClass().getClassLoader().getResourceAsStream("mvp-plugin.properties"))
        return properties.version
    }
}