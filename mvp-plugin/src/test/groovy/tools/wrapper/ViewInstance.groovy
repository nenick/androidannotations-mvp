package tools.wrapper

class ViewInstance extends InstanceWrapper {

    ViewInstance(String name, ClassLoader cl, String androidApplicationProjectId) {
        super(name, cl, androidApplicationProjectId)
    }

    boolean hasInterface(Class clazz) {
        return cls.interfaces.contains(clazz)
    }
}