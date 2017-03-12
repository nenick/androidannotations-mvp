package tools.helper

import tools.builder.ActivityBuilder
import tools.builder.AndroidManifestBuilder
import tools.builder.FragmentBuilder
import tools.builder.GradleScriptBuilder
import tools.builder.InterfaceBuilder
import tools.builder.LayoutBuilder
import tools.builder.ViewBuilder
import tools.TestProperties

trait ProjectFilesHelper implements TestProperties, GroovyHelper {

    GradleScriptBuilder gradleScript() {
        new GradleScriptBuilder(
                androidPluginVersion: androidPluginVersion,
                compileSdkVersion: compileSdkVersion,
                buildToolsVersion: buildToolsVersion,
                pluginVersion: pluginVersion,
                localRepo: localRepo
        )
    }

    InterfaceBuilder callback(String interfaceName) {
        new InterfaceBuilder(name: interfaceName)
    }

    ActivityBuilder activity(String activityName) {
        new ActivityBuilder(name: activityName)
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