package de.nenick.androidannotations.plugin.mvp.utils;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.AbstractJType;

import org.androidannotations.internal.process.ProcessHolder;

/**
 * Utils to read class information.
 */
public final class PluginClasses {

    private PluginClasses() {
        // mark this class as static utility class
    }

    /**
     * Read class name from target class.
     */
    public static String className(Class cls) {
        return cls.getName();
    }

    /**
     * Get reference to context class.
     */
    public static AbstractJType context(PluginBaseAnnotationHandler base) {
        ProcessHolder.Classes environmentClasses = base.classes();
        return contextClass(environmentClasses);
    }

    private static AbstractJClass contextClass(ProcessHolder.Classes classes) {
        return classes.CONTEXT;
    }
}
