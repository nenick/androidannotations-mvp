package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.*;
import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.MvpFragment;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.holder.EComponentWithViewSupportHolder;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;

import static com.helger.jcodemodel.JExpr._null;

public class MvpFragmentHandler extends BaseAnnotationHandler<EComponentWithViewSupportHolder> implements MethodInjectionHandler<EComponentHolder> {

    private final InjectHelper<EComponentHolder> injectHelper;

    public MvpFragmentHandler(AndroidAnnotationsEnvironment environment) {
        super(MvpFragment.class, environment);
        injectHelper = new InjectHelper<>(validatorHelper, this);
    }

    @Override
    public void validate(Element element, ElementValidation validation) {
        injectHelper.validate(MvpFragment.class, element, validation);
        if (!validation.isValid()) {
            return;
        }

        validatorHelper.enclosingElementHasEActivityOrEFragment(element, validation);
        validatorHelper.typeOrTargetValueHasAnnotation(EMvpPresenter.class, element, validation);
        validatorHelper.typeOrTargetValueHasAnnotation(EFragment.class, element, validation);
    }

    @Override
    public void process(Element element, EComponentWithViewSupportHolder holder) {
        injectHelper.process(element, holder);
    }

    @Override
    public JBlock getInvocationBlock(EComponentHolder holder) {
        return holder.getInitBodyInjectionBlock();
    }

    @Override
    public void assignValue(JBlock targetBlock, IJAssignmentTarget fieldRef, EComponentHolder holder, Element element, Element param) {
        injectFragmentInstance(targetBlock, fieldRef, element, param);
    }

    private void injectFragmentInstance(JBlock targetBlock, IJAssignmentTarget fieldRef, Element element, Element param) {
        AbstractJClass generatedClass = generatedClassToInject(element, param);
        JInvocation fragmentBuilder = generatedClass.staticInvoke("builder");
        JInvocation fragmentInstance = fragmentBuilder.invoke("build");
        IJStatement assignment = fieldRef.assign(fragmentInstance);

        assignment = addNonConfigurationInstanceHandling(targetBlock, fieldRef, element, param, assignment);
        targetBlock.add(assignment);
    }

    private IJStatement addNonConfigurationInstanceHandling(JBlock targetBlock, IJAssignmentTarget fieldRef, Element element, Element param, IJStatement assignment) {
        IJStatement resultAssigment = assignment;
        if (param.getKind() == ElementKind.FIELD) {
            boolean hasNonConfigurationInstanceAnnotation = element.getAnnotation(NonConfigurationInstance.class) != null;
            if (hasNonConfigurationInstanceAnnotation) {
                JConditional conditional = targetBlock._if(fieldRef.eq(_null()));
                conditional._then().add(assignment);
                resultAssigment = conditional;
            }
        }
        return resultAssigment;
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
