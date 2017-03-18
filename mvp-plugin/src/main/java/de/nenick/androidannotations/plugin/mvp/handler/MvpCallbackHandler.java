package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.HasMvpViewType;
import de.nenick.androidannotations.plugin.mvp.MvpCallback;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.holder.EComponentHolder;

import javax.lang.model.element.Element;

public class MvpCallbackHandler extends BaseAnnotationHandler<EComponentHolder> {

    public MvpCallbackHandler(AndroidAnnotationsEnvironment environment) {
        super(MvpCallback.class, environment);
    }

    @Override
    public void validate(Element element, ElementValidation validation) {
        validatorHelper.enclosingElementHasAnnotation(EMvpView.class, element, validation);
        validatorHelper.isNotPrivate(element, validation);
    }

    @Override
    public void process(Element element, EComponentHolder holder) {
        implementSetViewCallbackMethod(element, holder);
    }

    private void implementSetViewCallbackMethod(Element element, EComponentHolder holder) {
        Element elementType = annotationHelper.getTypeUtils().asElement(element.asType());
        AbstractJClass callbackClass = getJClass(elementType.toString());

        implementsInterface(holder, callbackClass);

        JMethod toString = holder.getGeneratedClass().method(JMod.PUBLIC, Void.TYPE, "setViewCallback");


        toString.annotate(Override.class);
        toString.param(callbackClass, "viewCallback");
        toString.body().directStatement("this." + element.getSimpleName() + " = ("+ elementType.getSimpleName() +") viewCallback;");
    }

    private void implementsInterface(EComponentHolder holder, AbstractJClass callbackClass) {
        AbstractJClass impl = getJClass(HasMvpViewType.class);
        impl = impl.narrow(callbackClass);

        holder.getGeneratedClass()._implements(impl);
    }
}
