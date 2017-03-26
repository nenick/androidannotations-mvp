package de.nenick.androidannotations.plugin.mvp.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import de.nenick.androidannotations.plugin.mvp.ActivityLauncher;

/**
 * Custom utils for lists.
 * <p>
 * Used when Collections, Arrays and ... hasn't adequate functionality.
 */
public class PluginLists {

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
