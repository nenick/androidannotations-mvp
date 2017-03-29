package tools.wrapper

import android.content.Context

class ActivityLauncherInstance extends InstanceWrapper {

    ActivityLauncherInstance(Object instance) {
        super(instance)
    }

    Object intent(Context context) {
        def method = cls.getMethod("intent", Context.class)
        method.invoke(instance, context)
    }
}