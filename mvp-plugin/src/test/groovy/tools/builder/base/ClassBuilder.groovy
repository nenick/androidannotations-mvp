package tools.builder.base

import tools.builder.InterfaceBuilder

abstract class ClassBuilder<Subclass extends ClassBuilder> implements Builder {
    String name
    InterfaceBuilder implInterface
    Map<Class, String> classAnnotations = [:]

    Subclass annotate(Class annotation, String value = null) {
        classAnnotations.put(annotation, value)
        return (Subclass) this
    }

    Subclass impl(InterfaceBuilder impl) {
        implInterface = impl
        return (Subclass) this
    }

    List<ClassBuilder> fields = []
    List<String> fieldsNames = []
    List<Class> fieldsAnnotations = []


    Subclass with(ClassBuilder field, String name, Class annotation) {
        fields.add(field)
        fieldsNames.add(name)
        fieldsAnnotations.add(annotation);
        return (Subclass) this
    }

    @Override
    String build(String projectId) {
        String importAnnotations = "";
        String annotionsForClass = "";
        classAnnotations.each({
            importAnnotations += "import ${it.key.name};\n"
            String value = it.value ? "(${it.value})" : ""
            annotionsForClass += "@${it.key.simpleName}${value}\n"
        })
        fieldsAnnotations.each({
            if(it) {
                importAnnotations += "import ${it.name};\n"
            }
        })

        def fieldEntries = ""
        if(fields.size() > 0) {
            fieldEntries = """

    @${fieldsAnnotations[0].simpleName}
    ${fields[0].name} ${fieldsNames[0]};

            """
        }


        return buildContent(projectId, name, importAnnotations, annotionsForClass, fieldEntries)
                .trim()
    }

    abstract String buildContent(String projectId, String className, String importAnnotations, String classAnnotations, String fieldEntries)
}
