package testtool

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

abstract class UnitSpecification extends BaseSpecification implements SimpleAndroidProjectHelper, GroovyTasksHelper {

    Project project

    void setup() {
        project = ProjectBuilder.builder().withName(androidProjectName).withProjectDir(dir.root).build()
    }
}