package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.IJAssignmentTarget;
import com.helger.jcodemodel.IJStatement;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.holder.EComponentWithViewSupportHolder;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.HasMvpCallback;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import de.nenick.androidannotations.plugin.mvp.utils.JClasses;
import de.nenick.androidannotations.plugin.mvp.utils.JMethods;
import de.nenick.androidannotations.plugin.mvp.utils.PluginBaseAnnotationHandler;
import de.nenick.androidannotations.plugin.mvp.utils.PluginClasses;

/**
 * Handler for @{@link MvpView} annotation.
 */
public class MvpViewHandler extends PluginBaseAnnotationHandler<EComponentWithViewSupportHolder>
        implements MethodInjectionHandler<EComponentHolder> {

    private final transient InjectHelper<EComponentHolder> injectHelper;

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
        JDefinedClass generatedClass = holder.getGeneratedClass();
        JMethod viewUpdateMethod = buildViewUpdateMethod(fieldName, methodName, generatedClass);
        JBlock onViewChanged = holder.getOnViewChangedBodyBeforeInjectionBlock();
        JMethods.invoke(onViewChanged, viewUpdateMethod, HasViews.class, "hasViews");
    }

    private JMethod buildViewUpdateMethod(Name fieldName, String methodName, JDefinedClass generatedClass) {
        JMethod method = generatedClass.method(JMod.PRIVATE, Void.TYPE, methodName);
        buildViewUpdateMethodBody(fieldName, JMethods.body(method));
        return method;
    }

    private void buildViewUpdateMethodBody(Name fieldName, JBlock codeBlock) {
        String hasCallbackInterface = PluginClasses.className(HasMvpCallback.class);
        String onViewChangedInterface = PluginClasses.className(OnViewChangedListener.class);
        codeBlock.directStatement("if(" + fieldName + " instanceof " + hasCallbackInterface + ") {");
        codeBlock.directStatement("   ((" + hasCallbackInterface + ") " + fieldName + ").setCallback(this);");
        codeBlock.directStatement("}");
        codeBlock.directStatement("if(" + fieldName + " instanceof " + onViewChangedInterface + ") {");
        codeBlock.directStatement("   ((" + onViewChangedInterface + ") " + fieldName + ").onViewChanged(hasViews);");
        codeBlock.directStatement("}");
    }

    @Override
    public JBlock getInvocationBlock(EComponentHolder holder) {
        return holder.getInitBodyInjectionBlock();
    }

    @Override
    public void assignValue(JBlock targetBlock, IJAssignmentTarget fieldRef,
                            EComponentHolder holder, Element element, Element param) {
        injectViewInstance(targetBlock, fieldRef, holder, param);
    }

    private void injectViewInstance(JBlock targetBlock, IJAssignmentTarget fieldRef,
                                    EComponentHolder holder, Element param) {
        AbstractJClass generatedClass = JClasses.asGeneratedClass(param, this);
        JInvocation beanInstance = JMethods.invokeBeanGetInstance(generatedClass, holder.getContextRef());
        IJStatement assignment = fieldRef.assign(beanInstance);
        targetBlock.add(assignment);
    }

    @Override
    public void validateEnclosingElement(Element element, ElementValidation valid) {
        validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, valid);
    }
}
