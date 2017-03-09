package tools.builder

import org.junit.rules.TemporaryFolder
import tools.builder.base.ClassBuilder
import tools.TestProperties
import tools.helper.AndroidFileHelper

class AndroidProjectBuilder implements AndroidFileHelper, TestProperties {

    List<ActivityBuilder> activities = []
    List<ClassBuilder> classes = []
    List<LayoutBuilder> layouts = []
    GradleScriptBuilder gradleScript
    AndroidManifestBuilder androidManifest
    File localRepo
    TemporaryFolder dir

    AndroidProjectBuilder with(GradleScriptBuilder script) {
        gradleScript = script
        this
    }

    AndroidProjectBuilder with(AndroidManifestBuilder manifest) {
        androidManifest = manifest
        this
    }

    AndroidProjectBuilder with(ClassBuilder clazz) {
        classes.add(clazz)
        this
    }

    AndroidProjectBuilder with(LayoutBuilder layout) {
        layouts.add(layout)
        this
    }

    def create() {
        //createGradleSettings()
        createBuildScript(androidApplicationProjectId, gradleScript);
        createAndroidManifest(androidApplicationProjectId, androidManifest)

        activities.each {
            createActivity(androidApplicationProjectId, it)
        }

        classes.each {
            createClass(androidApplicationProjectId, it)
        }

        layouts.each {
            createLayout(androidApplicationProjectId, it)
        }

        createInstrumentationTest(androidApplicationProjectId)
    }
}
