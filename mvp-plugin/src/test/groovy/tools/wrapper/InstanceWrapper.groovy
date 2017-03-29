package tools.wrapper

import java.lang.reflect.Method

class InstanceWrapper {
    Object instance
    Class cls

    InstanceWrapper(String name, ClassLoader cl, String androidApplicationProjectId) {
        cls = cl.loadClass("${androidApplicationProjectId}.${name}");
        instance = cls.newInstance();
    }

    InstanceWrapper(Object instance) {
        cls = instance.class;
        this.instance = instance;
    }

    Object invoke(String method, Class<?>[] params, Object[] args) {
        Method m = cls.getMethod(method, params);
        m.invoke(instance, args);
    }
}
