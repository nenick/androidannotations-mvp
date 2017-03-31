package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.IJAssignmentTarget;
import com.helger.jcodemodel.IJStatement;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JInvocation;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.holder.EComponentWithViewSupportHolder;

import javax.lang.model.element.Element;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.MvpFragment;
import de.nenick.androidannotations.plugin.mvp.utils.PluginAnnotations;
import de.nenick.androidannotations.plugin.mvp.utils.PluginBaseAnnotationHandler;

/**
 * Handler for @{@link MvpFragment} annotation.
 */
public class MvpFragmentHandler extends PluginBaseAnnotationHandler<EComponentWithViewSupportHolder>
        implements MethodInjectionHandler<EComponentHolder> {

    private final InjectHelper<EComponentHolder> injectHelper;

    public MvpFragmentHandler(AndroidAnnotationsEnvironment environment) {
        super(MvpFragment.class, environment);
        injectHelper = new InjectHelper<>(validatorHelper, this);
    }

    /**
     * - Expect annotated field is inside class with an {@link EActivity} or {@link EFragment} annotation.
     * - Expect annotated field class has {@link EMvpPresenter} annotation.
     * - Expect annotated field class has {@link EFragment} annotation.
     */
    @Override
    public void validate(Element element, ElementValidation validation) {
        validatorHelper.enclosingElementHasEActivityOrEFragment(element, validation);
        validatorHelper.typeOrTargetValueHasAnnotation(EMvpPresenter.class, element, validation);
        validatorHelper.typeOrTargetValueHasAnnotation(EFragment.class, element, validation);
    }

    /**
     * - Inject generated fragment instance to annotated field.
     */
    @Override
    public void process(Element element, EComponentWithViewSupportHolder holder) {
        injectHelper.process(element, holder);
    }

    @Override
    public JBlock getInvocationBlock(EComponentHolder holder) {
        return holder.getInitBodyInjectionBlock();
    }

    @Override
    public void assignValue(JBlock targetBlock, IJAssignmentTarget fieldRef,
                            EComponentHolder holder, Element element, Element param) {
        fragmentInstance(targetBlock, fieldRef, element, param);
    }

    private void fragmentInstance(JBlock targetBlock, IJAssignmentTarget fieldRef, Element element, Element param) {
        AbstractJClass generatedClass = PluginAnnotations.generatedClassToInject(element, param, this);
        JInvocation fragmentBuilder = generatedClass.staticInvoke("builder");
        JInvocation fragmentInstance = fragmentBuilder.invoke("build");
        IJStatement assignment = fieldRef.assign(fragmentInstance);
        targetBlock.add(assignment);
    }

    @Override
    public void validateEnclosingElement(Element element, ElementValidation valid) {
        validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, valid);
    }
}
