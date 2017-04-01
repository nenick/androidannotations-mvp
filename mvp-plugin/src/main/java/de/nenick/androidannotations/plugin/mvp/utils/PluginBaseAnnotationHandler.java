package de.nenick.androidannotations.plugin.mvp.utils;

import com.helger.jcodemodel.AbstractJClass;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.helper.IdAnnotationHelper;
import org.androidannotations.holder.GeneratedClassHolder;
import org.androidannotations.internal.model.AnnotationElements;
import org.androidannotations.internal.process.ProcessHolder;

import javax.lang.model.element.Element;

/**
 * Base for annotation handlers.
 * <p>
 * Makes utils, helpers and environment public for plugin utils.
 */
public abstract class PluginBaseAnnotationHandler<T extends GeneratedClassHolder> extends BaseAnnotationHandler<T> {

    public PluginBaseAnnotationHandler(Class<?> targetClass, AndroidAnnotationsEnvironment environment) {
        super(targetClass, environment);
    }

    /* default */ IdAnnotationHelper annotationHelper() {
        return annotationHelper;
    }

    /* default */ AbstractJClass jClass(String fullyQualifiedClassName) {
        return super.getJClass(fullyQualifiedClassName);
    }

    /* default */ AbstractJClass jClass(Class cls) {
        return super.getJClass(cls);
    }

    /* default */ ProcessHolder.Classes classes() {
        return getClasses(getEnvironment());
    }

    /* default */ AnnotationElements validateElements() {
        return getValidateElements(getEnvironment());
    }

    /* default */ GeneratedClassHolder generatedClassHolder(Element baseClass) {
        return getGeneratedClassHolder(baseClass, getEnvironment());
    }

    private GeneratedClassHolder getGeneratedClassHolder(Element baseClass, AndroidAnnotationsEnvironment environment) {
        return environment.getGeneratedClassHolder(baseClass);
    }

    private ProcessHolder.Classes getClasses(AndroidAnnotationsEnvironment environment) {
        return environment.getClasses();
    }

    private AnnotationElements getValidateElements(AndroidAnnotationsEnvironment environment) {
        return environment.getValidatedElements();
    }


}
