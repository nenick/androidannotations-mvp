package de.nenick.androidannotations.plugin.mvp.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.nenick.androidannotations.plugin.mvp.ActivityLauncher;

/**
 * Utils for creating lists.
 *
 * Used when Collections, Arrays and ... hasn't adequate functionality.
 */
public final class PluginLists {

    private PluginLists() {
        // mark this class as static utility class
    }

    /**
     * Create list with annotation class.
     *
     * @param cls annotation for list.
     * @return List with given annotation.
     */
    public static List<Class<? extends Annotation>> singleton(Class<? extends Annotation> cls) {
        List<Class<? extends Annotation>> annotations = new ArrayList<>();
        annotations.add(cls);
        return annotations;
    }

    /**
     * Create list with annotation classes.
     *
     * @param cls annotations for list.
     * @return List with given annotations.
     */
    @SafeVarargs
    public static List<Class<? extends Annotation>> list(Class<? extends Annotation> ... cls) {
        return Arrays.asList(cls);
    }

    /**
     * Create list with class name.
     *
     * @param cls class to read name.
     * @return List with given class name.
     */
    public static List<String> singletonName(Class<ActivityLauncher> cls) {
        List<String> classNames = new ArrayList<>();
        classNames.add(cls.getName());
        return classNames;
    }
}
