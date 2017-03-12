package tools.wrapper

import android.os.Bundle

class ActivityInstance extends Wrapper {

    ActivityInstance(String name, ClassLoader cl, String androidApplicationProjectId) {
        super(name, cl, androidApplicationProjectId)
    }

    def fieldMainView() {

    }

    void onCreate() {
        Class<?>[] types = [Bundle.class]
        invoke("onCreate", types, new Bundle())
    }
}
