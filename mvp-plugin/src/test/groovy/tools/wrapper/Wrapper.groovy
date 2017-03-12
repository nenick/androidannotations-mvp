package tools.wrapper

import java.lang.reflect.Method

class Wrapper {
    Object instance
    Class cls

    Wrapper(String name, ClassLoader cl, String androidApplicationProjectId) {
        cls = cl.loadClass("${androidApplicationProjectId}.${name}");
        instance = cls.newInstance();
    }

    Object invoke(String method, Class<?>[] params, Object[] args) {
        Method m = cls.getMethod(method, params);
        m.invoke(instance, args);
    }
}
