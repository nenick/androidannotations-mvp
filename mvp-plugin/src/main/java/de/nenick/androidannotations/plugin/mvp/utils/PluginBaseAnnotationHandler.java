package de.nenick.androidannotations.plugin.mvp.utils;

import com.helger.jcodemodel.AbstractJClass;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.helper.IdAnnotationHelper;
import org.androidannotations.holder.GeneratedClassHolder;

import javax.annotation.processing.ProcessingEnvironment;

public abstract class PluginBaseAnnotationHandler<T extends GeneratedClassHolder> extends BaseAnnotationHandler<T>{
    public PluginBaseAnnotationHandler(Class<?> targetClass, AndroidAnnotationsEnvironment environment) {
        super(targetClass, environment);
    }

    public PluginBaseAnnotationHandler(String target, AndroidAnnotationsEnvironment environment) {
        super(target, environment);
    }

    IdAnnotationHelper getAnnotationHelper() {
        return annotationHelper;
    }

    protected ProcessingEnvironment getProcessingEnvironment() {
        return super.getProcessingEnvironment();
    }

    protected AbstractJClass getJClass(String fullyQualifiedClassName) {
        return super.getJClass(fullyQualifiedClassName);
    }
}
