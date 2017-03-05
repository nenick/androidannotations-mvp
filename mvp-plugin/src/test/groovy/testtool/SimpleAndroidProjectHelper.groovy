package testtool

import testtool.custom.AndroidFileHelper

trait SimpleAndroidProjectHelper implements AndroidPluginHelper, AndroidFileHelper {

    void setupApplicationProject() {
        applyWithApplicationPlugin()
        project.android {
            compileSdkVersion 22
            buildToolsVersion "22.0.1"
            defaultConfig {
                applicationId "de.nenick.test.app"
            }
        }
        project.evaluate()
    }

    void setupLibraryProject() {
        createSimpleAndroidManifest()
        applyWithLibraryPlugin()
        project.android {
            compileSdkVersion 22
            buildToolsVersion "22.0.1"
        }
        project.evaluate()
    }
}