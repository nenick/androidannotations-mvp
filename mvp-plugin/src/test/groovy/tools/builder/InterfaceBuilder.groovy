package tools.builder

import tools.builder.base.ClassBuilder

class InterfaceBuilder extends ClassBuilder<InterfaceBuilder> {

    @Override
    String buildContent(String projectId, String className, String importAnnotations, String classAnnotations, String fieldEntries, String implInterfaces) {
        def extendInterfaces = ""
        if (implInterface != null) {
            extendInterfaces = implInterface.replace("implements", "extends")
        }

        return """

package ${projectId};

${importAnnotations}

${classAnnotations}
public interface ${className} ${extendInterfaces} {

    ${fieldEntries}

}
        """
    }
}
