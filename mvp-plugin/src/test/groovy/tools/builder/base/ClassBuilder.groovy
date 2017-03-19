package tools.builder.base

import tools.builder.InterfaceBuilder

abstract class ClassBuilder<Subclass extends ClassBuilder> implements Builder {
    String name
    String importClass
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

    List<String> fieldClasses = []
    List<String> fieldsNames = []
    List<Class> fieldsAnnotations = []
    List<String> imports = []


    Subclass with(ClassBuilder fieldClass, String name, Class annotation) {
        fieldClasses.add(fieldClass.name)
        fieldsNames.add(name)
        fieldsAnnotations.add(annotation);
        imports.add(fieldClass.importClass)
        return (Subclass) this
    }

    Subclass with(Class fieldClass, String name, Class annotation) {
        fieldClasses.add(fieldClass.simpleName)
        fieldsNames.add(name)
        fieldsAnnotations.add(annotation);
        imports.add(fieldClass.name)
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
        imports.each({
            if(it) {
                importAnnotations += "import ${it};\n"
            }
        })

        def fieldEntries = ""
        if(fieldClasses.size() > 0) {
            fieldEntries = """

    @${fieldsAnnotations[0].simpleName}
    ${fieldClasses[0]} ${fieldsNames[0]};

            """
        }


        return buildContent(projectId, name, importAnnotations, annotionsForClass, fieldEntries)
                .trim()
    }

    abstract String buildContent(String projectId, String className, String importAnnotations, String classAnnotations, String fieldEntries)
}
