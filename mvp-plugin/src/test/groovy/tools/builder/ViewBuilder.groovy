package tools.builder

import tools.builder.base.ClassBuilder

class ViewBuilder extends ClassBuilder<ViewBuilder> {

    @Override
    String buildContent(String projectId, String className, String importAnnotations, String classAnnotations, String fieldEntries, String implInterfaces) {

        return """

package ${projectId};

${importAnnotations}

${classAnnotations}
public class ${className} ${implInterfaces} {

    ${fieldEntries}

}
        """
    }
}
