package tools.wrapper

class ViewInstance extends Wrapper {

    ViewInstance(String name, ClassLoader cl, String androidApplicationProjectId) {
        super(name, cl, androidApplicationProjectId)
    }

    boolean hasInterface(Class clazz) {
        return cls.interfaces.contains(clazz)
    }
}