package tools.builder

import tools.builder.base.ClassBuilder

class InterfaceBuilder extends ClassBuilder<InterfaceBuilder> {

    @Override
    String buildContent(String projectId, String className, String importAnnotations, String classAnnotations, String fieldEntries) {

        return """

package ${projectId};

${importAnnotations}

${classAnnotations}
public interface ${className} {

    ${fieldEntries}

}
        """
    }
}
