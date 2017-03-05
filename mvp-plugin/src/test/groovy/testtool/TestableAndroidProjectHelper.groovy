package testtool

import testtool.custom.AndroidFileHelper

trait TestableAndroidProjectHelper implements GroovyHelper, AndroidFileHelper {

    void blub() {
        int i =5;
    }

    void setupCompilableLibraryProject() {

        createGradleSettings()
        createBuildScript("library", androidLibraryProjectId, localRepo)

        createActivityClass(androidLibraryProjectId)
        createActivityLayout()
        createAndroidManifestWithMainActivity(androidLibraryProjectId)

        createInstrumentationTest(androidLibraryProjectId)
    }

    void setupCompilableApplicationProject() {
        createGradleSettings()
        createBuildScript("application", androidApplicationProjectId, localRepo)

        createActivityClass(androidApplicationProjectId)
        createActivityLayout()
        createAndroidManifestWithMainActivity(androidApplicationProjectId)

        createInstrumentationTest(androidApplicationProjectId)
    }
}