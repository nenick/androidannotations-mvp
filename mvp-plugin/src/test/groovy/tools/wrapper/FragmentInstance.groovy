package tools.wrapper

class FragmentInstance extends Wrapper {

    FragmentInstance(String name, ClassLoader cl, String androidApplicationProjectId) {
        super(name, cl, androidApplicationProjectId)
    }

    boolean hasInterface(Class clazz) {
        return cls.interfaces.contains(clazz)
    }
}