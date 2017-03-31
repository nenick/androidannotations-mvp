package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.IJAssignmentTarget;
import com.helger.jcodemodel.IJStatement;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JVar;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EBeanHolder;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.holder.EComponentWithViewSupportHolder;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.HasMvpCallback;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import de.nenick.androidannotations.plugin.mvp.utils.PluginAnnotations;
import de.nenick.androidannotations.plugin.mvp.utils.PluginBaseAnnotationHandler;

/**
 * Handler for @{@link MvpView} annotation.
 */
public class MvpViewHandler extends PluginBaseAnnotationHandler<EComponentWithViewSupportHolder>
        implements MethodInjectionHandler<EComponentHolder> {

    private final InjectHelper<EComponentHolder> injectHelper;

    public MvpViewHandler(AndroidAnnotationsEnvironment environment) {
        super(MvpView.class, environment);
        injectHelper = new InjectHelper<>(validatorHelper, this);
    }


    @Override
    public void validate(Element element, ElementValidation validation) {
        validatorHelper.enclosingElementHasAnnotation(EMvpPresenter.class, element, validation);
        validatorHelper.typeOrTargetValueHasAnnotation(EMvpView.class, element, validation);
        validatorHelper.isNotPrivate(element, validation);
    }

    @Override
    public void process(Element element, EComponentWithViewSupportHolder holder) {
        injectHelper.process(element, holder);
        connectViewPresenterOnViewChange(element, holder);
    }

    private void connectViewPresenterOnViewChange(Element element, EComponentWithViewSupportHolder holder) {
        Name fieldName = element.getSimpleName();
        String methodName = fieldName + "Update";
        JMethod toString = holder.getGeneratedClass().method(JMod.PRIVATE, Void.TYPE, methodName);
        JVar hasViewsParam = toString.param(HasViews.class, "hasViews");

        toString.body().directStatement("if(" + fieldName + " instanceof " + HasMvpCallback.class.getName() + ") {");
        toString.body().directStatement("   ((" + HasMvpCallback.class.getName() + ") " + fieldName
                + ").setCallback(this);");
        toString.body().directStatement("}");
        toString.body().directStatement("if(" + fieldName + " instanceof "
                + OnViewChangedListener.class.getName() + ") {");
        toString.body().directStatement("   ((" + OnViewChangedListener.class.getName() + ") "
                + fieldName + ").onViewChanged(hasViews);");
        toString.body().directStatement("}");

        holder.getOnViewChangedBodyBeforeInjectionBlock().invoke(toString).arg(hasViewsParam);
    }

    @Override
    public JBlock getInvocationBlock(EComponentHolder holder) {
        return holder.getInitBodyInjectionBlock();
    }

    @Override
    public void assignValue(JBlock targetBlock, IJAssignmentTarget fieldRef,
                            EComponentHolder holder, Element element, Element param) {
        injectViewInstance(targetBlock, fieldRef, holder, element, param);
    }

    private void injectViewInstance(JBlock targetBlock, IJAssignmentTarget fieldRef,
                                    EComponentHolder holder, Element element, Element param) {
        AbstractJClass generatedClass = PluginAnnotations.generatedClassToInject(element, param, this);
        JInvocation beanInstance = generatedClass.staticInvoke(EBeanHolder.GET_INSTANCE_METHOD_NAME)
                .arg(holder.getContextRef());
        IJStatement assignment = fieldRef.assign(beanInstance);
        targetBlock.add(assignment);
    }

    @Override
    public void validateEnclosingElement(Element element, ElementValidation valid) {
        validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, valid);
    }
}
