package de.nenick.androidannotations.plugin.mvp.utils;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.JDefinedClass;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.holder.GeneratedClassHolder;
import org.androidannotations.internal.model.AnnotationElements;

import java.util.Collection;
import java.util.Set;

import javax.lang.model.element.Element;

/**
 * Utils for generated classes.
 */
public final class GeneratedClasses {

    private GeneratedClasses() {
        // mark this class as static utility class
    }

    public static JDefinedClass generatedActivityOrFragment(AbstractJClass cls, PluginBaseAnnotationHandler base) {
        Set<? extends Element> baseClasses = generatedActivitiesAndFragments(base.validateElements());
        for (Element baseClass : baseClasses) {
            JDefinedClass generatedBaseClass = generatedBaseClass(base.generatedClassHolder(baseClass));
            if (isTargetActivityToLaunch(cls.name(), generatedBaseClass)) {
                return generatedBaseClass;
            }
        }
        throw new IllegalStateException();
    }

    public static JDefinedClass generatedActivityOrFragment(String cls, PluginBaseAnnotationHandler base) {
        return generatedActivityOrFragment(base.asClass(cls), base);
    }

    public static JDefinedClass intentBuilder(JDefinedClass activity) {
        for (JDefinedClass innerClass : activity.classes()) {
            if (isIntentBuilder(innerClass.name())) {
                return innerClass;
            }
        }
        throw new IllegalStateException();
    }

    private static boolean isIntentBuilder(String innerClassName) {
        return innerClassName != null && innerClassName.endsWith("IntentBuilder_");
    }

    private static boolean isTargetActivityToLaunch(String activityName, JDefinedClass generatedBaseClass) {
        return activityName.equals(generatedBaseClass.name());
    }

    private static JDefinedClass generatedBaseClass(GeneratedClassHolder baseClassHolder) {
        return baseClassHolder.getGeneratedClass();
    }

    private static Set<? extends Element> generatedActivitiesAndFragments(AnnotationElements validatedElements) {
        String activities = PluginClasses.className(EActivity.class);
        String fragments = PluginClasses.className(EFragment.class);
        Set<? extends Element> baseElements = validatedElements.getRootAnnotatedElements(activities);
        Set<? extends Element> baseFragments = validatedElements.getRootAnnotatedElements(fragments);
        return mergeElements(baseElements, baseFragments);
    }

    private static Set<? extends Element> mergeElements(Set<? extends Element> baseElements, Collection fragments) {
        //noinspection unchecked
        baseElements.addAll(fragments);
        return baseElements;
    }
}
