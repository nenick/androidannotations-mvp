package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.holder.EComponentHolder;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.lang.model.element.Element;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.HasMvpCallback;
import de.nenick.androidannotations.plugin.mvp.MvpCallback;
import de.nenick.androidannotations.plugin.mvp.utils.PluginBaseAnnotationHandler;
import de.nenick.androidannotations.plugin.mvp.utils.PluginLists;

/**
 * Handler for @{@link MvpCallback} annotation.
 */
public class MvpCallbackHandler extends PluginBaseAnnotationHandler<EComponentHolder> {

    public MvpCallbackHandler(AndroidAnnotationsEnvironment environment) {
        super(MvpCallback.class, environment);
    }

    /**
     * - Expect annotated field is inside class with an {@link EMvpView} annotation.
     * - Expect annotated field is not private.
     */
    @Override
    public void validate(Element element, ElementValidation validation) {
        List<Class<? extends Annotation>> validMvpAnnotations = PluginLists.list(EMvpView.class, EMvpPresenter.class);
        List<Class<? extends Annotation>> validInViewOrFragment = PluginLists.list(EMvpView.class, EFragment.class);

        validatorHelper.enclosingElementHasOneOfAnnotations(element, validMvpAnnotations, validation);
        validatorHelper.enclosingElementHasOneOfAnnotations(element, validInViewOrFragment, validation);
        validatorHelper.isNotPrivate(element, validation);
    }

    /**
     * - Add interface {@link HasMvpCallback} to enclosing element.
     * - Create {@link HasMvpCallback#setCallback(Object)} implementation to inject field instance.
     */
    @Override
    public void process(Element element, EComponentHolder holder) {
        implementSetCallbackMethod(element, holder);
    }

    private void implementSetCallbackMethod(Element element, EComponentHolder holder) {
        Element elementType = annotationHelper.getTypeUtils().asElement(element.asType());
        AbstractJClass callbackClass = getJClass(elementType.toString());

        implementsInterface(holder, callbackClass);

        JMethod toString = holder.getGeneratedClass().method(JMod.PUBLIC, Void.TYPE, "setCallback");


        toString.annotate(Override.class);
        toString.param(callbackClass, "callback");
        toString.body().directStatement("this." + element.getSimpleName()
                + " = (" + elementType.getSimpleName() + ") callback;");
    }

    private void implementsInterface(EComponentHolder holder, AbstractJClass callbackClass) {
        AbstractJClass impl = getJClass(HasMvpCallback.class);
        impl = impl.narrow(callbackClass);

        holder.getGeneratedClass()._implements(impl);
    }
}
