package testtool

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import testtool.android.AndroidProjectConfiguration
import testtool.android.ConfiguredTask

trait GroovyTasksHelper {

    ConfiguredTask task

    DefaultTask findTask(name) {
        project.tasks.getByName(name)
    }

    DefaultTask projectHasTask(name) {
        task = findTask(name)
    }

    AndroidProjectConfiguration taskHasProjectConfiguration() {
        return task.configuration
    }

    boolean getTaskHasAppPlugin() {
        return task.configuration.hasAppPlugin
    }

    boolean getTaskHasLibPlugin() {
        return task.configuration.hasLibPlugin
    }

    String getTaskApplicationId() {
        return task.configuration.applicationId
    }

    String getTaskTestApplicationId() {
        return task.configuration.testApplicationId
    }

    abstract Project getProject()
}