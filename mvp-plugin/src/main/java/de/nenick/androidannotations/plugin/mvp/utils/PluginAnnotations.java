package de.nenick.androidannotations.plugin.mvp.utils;

import com.helger.jcodemodel.AbstractJClass;

import org.androidannotations.helper.IdAnnotationHelper;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

public final class PluginAnnotations {

    private PluginAnnotations() {
        // mark this class as static utility class
    }

    public static AbstractJClass generatedClassToInject(Element element, Element param, PluginBaseAnnotationHandler base) {
        IdAnnotationHelper annotationHelper = base.getAnnotationHelper();
        TypeMirror typeMirror = annotationHelper.extractAnnotationClassParameter(element);
        if (typeMirror == null) {
            typeMirror = param.asType();
            typeMirror = base.getProcessingEnvironment().getTypeUtils().erasure(typeMirror);
        }
        String typeQualifiedName = typeMirror.toString();
        return base.getJClass(annotationHelper.generatedClassQualifiedNameFromQualifiedName(typeQualifiedName));
    }
}
