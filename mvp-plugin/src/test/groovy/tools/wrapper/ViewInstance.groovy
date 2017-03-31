package tools.wrapper

import org.androidannotations.api.view.HasViews

class ViewInstance extends InstanceWrapper {

    ViewInstance(String name, ClassLoader cl, String androidApplicationProjectId) {
        super(name, cl, androidApplicationProjectId)
    }

    boolean hasInterface(Class clazz) {
        return cls.interfaces.contains(clazz)
    }

    void setCallback(Object instance) {
        Class<?>[] types = [instance.class.superclass.interfaces[0]]
        invoke("setCallback", types, instance)
    }

    Object getCallback() {
        def field = cls.superclass.getDeclaredField("myCallback")
        field.setAccessible(true)
        field.get(instance)
    }

    Object getTextView() {
        def field = cls.superclass.getDeclaredField("textView")
        field.setAccessible(true)
        field.get(instance)
    }

    void onViewChanged(hasViewsMock) {
        Class<?>[] types = [HasViews.class]
        invoke("onViewChanged", types, hasViewsMock)
    }
}