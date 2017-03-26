package de.nenick.androidannotations.plugin.mvp.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import de.nenick.androidannotations.plugin.mvp.ActivityLauncher;

/**
 * Custom utils to create lists.
 *
 * Used when Collections, Arrays and ... hasn't adequate functionality.
 */
public class PluginLists {

    public static List<Class<? extends Annotation>> singleton(Class<? extends Annotation> cls) {
        List<Class<? extends Annotation>> annotations = new ArrayList<>();
        annotations.add(cls);
        return annotations;
    }

    public static List<String> singletonName(Class<ActivityLauncher> cls) {
        List<String> classNames = new ArrayList<>();
        classNames.add(cls.getName());
        return classNames;
    }
}
