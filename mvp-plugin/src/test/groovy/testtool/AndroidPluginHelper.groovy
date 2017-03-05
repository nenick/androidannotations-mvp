package testtool

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
//import de.nenick.connectedcheck.ConnectedCheckCustomizedPlugin
import org.gradle.api.Project

trait AndroidPluginHelper {

    void applyPlugin() {
        //project.pluginManager.apply(ConnectedCheckCustomizedPlugin)
    }

    void applyWithApplicationPlugin() {
        project.pluginManager.apply(AppPlugin)
        applyPlugin()
    }

    void applyWithLibraryPlugin() {
        project.pluginManager.apply(LibraryPlugin)
        applyPlugin()
    }

    boolean projectHasPlugin(Class clazz) {
        project.plugins.hasPlugin(clazz)
    }

    abstract Project getProject()
}