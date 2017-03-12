package tools

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import tools.helper.ProjectHelper

abstract class BaseSpecification extends Specification
        implements ProjectHelper {

    @Rule
    TemporaryFolder dir

    boolean containsGeneratedClassFor(String className) {
        file("build/generated/source/apt/debug/${androidApplicationProjectId.replace(".", "/")}/${className}_.java").exists()
    }
}