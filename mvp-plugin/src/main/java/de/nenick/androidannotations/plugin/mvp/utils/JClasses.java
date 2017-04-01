package de.nenick.androidannotations.plugin.mvp.utils;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JNarrowedClass;

import org.androidannotations.helper.AnnotationHelper;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * Utils for AbstractJClass.
 */
public final class JClasses {

    private JClasses() {
        // mark this class as static utility class
    }


    public static AbstractJClass asClass(Element fieldElement, PluginBaseAnnotationHandler base) {
        String className = fullyQualifiedClassName(fieldElement.asType());
        return base.jClass(className);
    }

    public static AbstractJClass asGeneratedClass(Element fieldElement, PluginBaseAnnotationHandler base) {
        String className = fullyQualifiedGeneratedClassName(base.annotationHelper(), fieldElement.asType());
        return base.jClass(className);
    }

    public static void implementsInterface(JDefinedClass target, AbstractJClass arg,
                                           Class interfaceClass, PluginBaseAnnotationHandler base) {
        AbstractJClass interfaceJClass = base.jClass(interfaceClass);
        AbstractJClass implNarrowed = narrow(arg, interfaceJClass);
        target._implements(implNarrowed);
    }

    private static JNarrowedClass narrow(AbstractJClass callbackClass, AbstractJClass interfaceClass) {
        return interfaceClass.narrow(callbackClass);
    }

    private static String fullyQualifiedClassName(TypeMirror type) {
        return type.toString();
    }

    private static String fullyQualifiedGeneratedClassName(AnnotationHelper annotationHelper, TypeMirror type) {
        return annotationHelper.generatedClassQualifiedNameFromQualifiedName(type.toString());
    }
}
