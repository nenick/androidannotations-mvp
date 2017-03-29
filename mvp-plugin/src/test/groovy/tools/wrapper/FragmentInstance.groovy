package tools.wrapper

import android.os.Bundle

class FragmentInstance extends InstanceWrapper {

    def OTHER_ACTIVITY_LAUNCHER_FIELD = "otherActivity"

    FragmentInstance(String name, ClassLoader cl, String androidApplicationProjectId) {
        super(name, cl, androidApplicationProjectId)
    }

    void onCreate() {
        Class<?>[] types = [Bundle.class]
        invoke("onCreate", types, new Bundle())
    }

    boolean hasInterface(Class clazz) {
        return cls.interfaces.contains(clazz)
    }

    ActivityLauncherInstance getOtherActivityLauncher() {
        def field = cls.superclass.getDeclaredField(OTHER_ACTIVITY_LAUNCHER_FIELD)
        field.setAccessible(true)
        def activityLauncher = field.get(instance)
        if(activityLauncher != null) {
            return new ActivityLauncherInstance(activityLauncher)
        } else {
            return null
        }
    }
}