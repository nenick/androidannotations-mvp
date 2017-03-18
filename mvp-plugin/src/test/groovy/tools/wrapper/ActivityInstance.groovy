package tools.wrapper

import android.os.Bundle
import org.androidannotations.api.view.HasViews

class ActivityInstance extends Wrapper {

    def VIEW_FIELD = "myView"

    ActivityInstance(String name, ClassLoader cl, String androidApplicationProjectId) {
        super(name, cl, androidApplicationProjectId)
    }

    Object getMyView() {
        def field = cls.superclass.getDeclaredField(VIEW_FIELD)
        field.setAccessible(true)
        field.get(instance)
    }

    def setMyView(Object value) {
        def field = cls.superclass.getDeclaredField(VIEW_FIELD)
        field.setAccessible(true)
        field.set(instance, value)
    }

    void onCreate() {
        Class<?>[] types = [Bundle.class]
        invoke("onCreate", types, new Bundle())
    }

    void onViewChanged() {
        Class<?>[] types = [HasViews.class]
        invoke("onViewChanged", types, instance)
    }
}