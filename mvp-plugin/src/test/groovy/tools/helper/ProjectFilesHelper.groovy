package tools.helper

import tools.TestProperties
import tools.builder.*

trait ProjectFilesHelper implements TestProperties, GroovyHelper {

    GradleScriptBuilder gradleScript() {
        new GradleScriptBuilder(
                androidPluginVersion: androidPluginVersion,
                compileSdkVersion: compileSdkVersion,
                buildToolsVersion: buildToolsVersion,
                androidApt: androidApt,
                pluginVersion: pluginVersion,
                localRepo: localRepo,
                androidAnnotations: androidAnnotations,
                androidAnnotationsApi: androidAnnotationsApi
        )
    }

    InterfaceBuilder callback(String interfaceName) {
        new InterfaceBuilder(name: interfaceName)
    }

    ActivityBuilder activity(String activityName) {
        new ActivityBuilder(name: activityName)
    }

    ActivityLauncherBuilder activityLauncher(ActivityBuilder activity) {
        new ActivityLauncherBuilder(activity, androidApplicationProjectId)
    }

    FragmentBuilder fragment(String fragmentName) {
        new FragmentBuilder(name: fragmentName)
    }

    ViewBuilder view(String viewName) {
        new ViewBuilder(name: viewName)
    }

    LayoutBuilder layout(String layoutName) {
        new LayoutBuilder(name: layoutName)
    }

    AndroidManifestBuilder androidManifest() {
        new AndroidManifestBuilder()
    }

    AndroidManifestBuilder androidManifest(ActivityBuilder startActivity) {
        new AndroidManifestBuilder().with(startActivity)
    }
}