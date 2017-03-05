package testtool.android

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.api.LibraryVariant
import org.gradle.api.DomainObjectCollection
import org.gradle.api.Project

/**
 * Class used to obtain information about the project configuration.
 */
class AndroidProjectConfiguration {
    def androidProject
    boolean hasAppPlugin
    boolean hasLibPlugin
    String applicationId
    String testApplicationId
    boolean loaded = false
    ApplicationVariant projectVariant
    LibraryVariant libraryVariant

    AndroidProjectConfiguration(Project project) {
        detectAndroidPlugin(project)
    }

    private void detectAndroidPlugin(Project project) {
        hasAppPlugin = project.plugins.any { p -> p instanceof AppPlugin }
        hasLibPlugin = project.plugins.any { p -> p instanceof LibraryPlugin }

        if (!hasAppPlugin && !hasLibPlugin) {
            throw new IllegalStateException("The 'com.android.application' or 'com.android.library' plugin is required first.")
        }

        androidProject = project.android
    }

    AndroidProjectConfiguration load(variant) {
        if (loaded) return this else loaded = true
        if(hasAppPlugin)
            projectVariant = variant
        else
            libraryVariant = variant

        def manifestFile = projectVariant.outputs.processManifest.manifestOutputFile
        applicationId = readApplicationIdFromAndroidManifest(manifestFile)

        def testManifestFile = projectVariant.testVariant.outputs.processManifest.manifestOutputFile
        testApplicationId = readApplicationIdFromAndroidManifest(testManifestFile)

        return this
    }

    DomainObjectCollection<BaseVariant> getVariants() {
        return hasAppPlugin ? androidProject.applicationVariants : androidProject.libraryVariants
    }

    private static def readApplicationIdFromAndroidManifest(file) {
        new XmlSlurper().parse(file).@package
    }
}