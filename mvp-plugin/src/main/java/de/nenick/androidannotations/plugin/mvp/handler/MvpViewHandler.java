package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.*;
import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.HasMvpCallback;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EBeanHolder;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.holder.EComponentWithViewSupportHolder;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

import static com.helger.jcodemodel.JExpr._null;

public class MvpViewHandler extends BaseAnnotationHandler<EComponentWithViewSupportHolder> implements MethodInjectionHandler<EComponentHolder> {

    private final InjectHelper<EComponentHolder> injectHelper;

    public MvpViewHandler(AndroidAnnotationsEnvironment environment) {
        super(MvpView.class, environment);
        injectHelper = new InjectHelper<>(validatorHelper, this);
    }

    @Override
    public void validate(Element element, ElementValidation validation) {
        injectHelper.validate(MvpView.class, element, validation);

        validatorHelper.enclosingElementHasAnnotation(EMvpPresenter.class, element, validation);
        validatorHelper.typeOrTargetValueHasAnnotation(EMvpView.class, element, validation);
        validatorHelper.typeOrTargetValueHasAnnotation(EBean.class, element, validation);
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

        toString.body().directStatement("if(" + fieldName + " instanceof " + HasMvpCallback.class.getName() + ") {");
        toString.body().directStatement("   ((" + HasMvpCallback.class.getName() + ") " + fieldName + ").setViewCallback(this);");
        toString.body().directStatement("}");
        toString.body().directStatement("if(" + fieldName + " instanceof " + OnViewChangedListener.class.getName() + ") {");
        toString.body().directStatement("   ((" + OnViewChangedListener.class.getName() + ") " + fieldName + ").onViewChanged((" + HasViews.class.getName() + ") this);");
        toString.body().directStatement("}");
        holder.getOnViewChangedBodyBeforeInjectionBlock().invoke(methodName);
    }

    @Override
    public JBlock getInvocationBlock(EComponentHolder holder) {
        return holder.getInitBodyInjectionBlock();
    }

    @Override
    public void assignValue(JBlock targetBlock, IJAssignmentTarget fieldRef, EComponentHolder holder, Element element, Element param) {
        injectViewInstance(targetBlock, fieldRef, holder, element, param);
    }

    private void injectViewInstance(JBlock targetBlock, IJAssignmentTarget fieldRef, EComponentHolder holder, Element element, Element param) {
        AbstractJClass generatedClass = generatedClassToInject(element, param);
        JInvocation beanInstance = generatedClass.staticInvoke(EBeanHolder.GET_INSTANCE_METHOD_NAME).arg(holder.getContextRef());
        IJStatement assignment = fieldRef.assign(beanInstance);
        targetBlock.add(assignment);
    }

    private AbstractJClass generatedClassToInject(Element element, Element param) {
        TypeMirror typeMirror = annotationHelper.extractAnnotationClassParameter(element);
        if (typeMirror == null) {
            typeMirror = param.asType();
            typeMirror = getProcessingEnvironment().getTypeUtils().erasure(typeMirror);
        }
        String typeQualifiedName = typeMirror.toString();
        return getJClass(annotationHelper.generatedClassQualifiedNameFromQualifiedName(typeQualifiedName));
    }

    @Override
    public void validateEnclosingElement(Element element, ElementValidation valid) {
        validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, valid);
    }
}
