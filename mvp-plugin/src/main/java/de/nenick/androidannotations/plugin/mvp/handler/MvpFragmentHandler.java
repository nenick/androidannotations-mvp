package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.IJAssignmentTarget;
import com.helger.jcodemodel.IJStatement;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JConditional;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EBeanHolder;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.holder.EComponentWithViewSupportHolder;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;

import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.MvpFragment;
import de.nenick.androidannotations.plugin.mvp.MvpView;

import static com.helger.jcodemodel.JExpr._null;

/**
 * Inject MVP view instance into annotated field.
 * <p>
 * This class is mostly a copy of the @{@link Bean} annotation.
 * Then it was adjusted to handle the @{@link EMvpView} annotation now.
 */
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
        validatorHelper.typeOrTargetValueHasAnnotation(EFragment.class, element, validation);
        validatorHelper.isNotPrivate(element, validation);
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
        TypeMirror typeMirror = annotationHelper.extractAnnotationClassParameter(element);
        if (typeMirror == null) {
            typeMirror = param.asType();
            typeMirror = getProcessingEnvironment().getTypeUtils().erasure(typeMirror);
        }
        String typeQualifiedName = typeMirror.toString();
        AbstractJClass injectedClass = getJClass(annotationHelper.generatedClassQualifiedNameFromQualifiedName(typeQualifiedName));
        //JInvocation beanInstance = injectedClass.staticInvoke(EBeanHolder.GET_INSTANCE_METHOD_NAME).arg(holder.getContextRef());

        JInvocation fragmentBuilder = injectedClass.staticInvoke("builder");
        JInvocation fragmentInstance = fragmentBuilder.invoke("build");

        IJStatement assignment = fieldRef.assign(fragmentInstance);
        if (param.getKind() == ElementKind.FIELD) {
            boolean hasNonConfigurationInstanceAnnotation = element.getAnnotation(NonConfigurationInstance.class) != null;
            if (hasNonConfigurationInstanceAnnotation) {
                JConditional conditional = targetBlock._if(fieldRef.eq(_null()));
                conditional._then().add(assignment);
                assignment = conditional;
            }
        }

        targetBlock.add(assignment);
    }

    @Override
    public void validateEnclosingElement(Element element, ElementValidation valid) {
        validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, valid);
    }
}
