package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.IJAssignmentTarget;
import com.helger.jcodemodel.IJStatement;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JClassAlreadyExistsException;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
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

import de.nenick.androidannotations.plugin.mvp.ActivityLauncher;
import de.nenick.androidannotations.plugin.mvp.MvpActivity;
import de.nenick.androidannotations.plugin.mvp.utils.GeneratedClasses;
import de.nenick.androidannotations.plugin.mvp.utils.JClasses;
import de.nenick.androidannotations.plugin.mvp.utils.PluginBaseAnnotationHandler;
import de.nenick.androidannotations.plugin.mvp.utils.PluginLists;

/**
 * Handler for @{@link MvpActivity} annotation.
 */
public class MvpActivityHandler extends PluginBaseAnnotationHandler<EComponentWithViewSupportHolder>
        implements MethodInjectionHandler<EComponentHolder> {

    private transient final InjectHelper<EComponentHolder> injectHelper;

    public MvpActivityHandler(AndroidAnnotationsEnvironment environment) {
        super(MvpActivity.class, environment);
        injectHelper = new InjectHelper<>(validatorHelper, this);
    }

    /**
     * - Expect annotated field is inside class with an {@link EActivity} or {@link EFragment} annotation.
     * - Expect annotated field type is an {@link ActivityLauncher}.
     */
    @Override
    public void validate(Element element, ElementValidation validation) {
        validatorHelper.enclosingElementHasEActivityOrEFragment(element, validation);
        validatorHelper.extendsOneOfTypes(element, PluginLists.singletonName(ActivityLauncher.class), validation);
    }

    /**
     * - Generate {@link ActivityLauncher} subclass for annotated field.
     * - Inject generated {@link ActivityLauncher} instance to annotated field.
     */
    @Override
    public void process(Element element, EComponentWithViewSupportHolder holder) throws JClassAlreadyExistsException {
        new MvpActivityLauncherBuilder(this).process(element, holder);
        injectHelper.process(element, holder);
    }

    @Override
    public JBlock getInvocationBlock(EComponentHolder holder) {
        return holder.getInitBodyInjectionBlock();
    }

    @Override
    public void assignValue(JBlock targetBlock, IJAssignmentTarget fieldRef,
                            EComponentHolder holder, Element element, Element param) {
        injectActivityLauncher(targetBlock, fieldRef, element);
    }

    private void injectActivityLauncher(JBlock targetBlock, IJAssignmentTarget fieldRef, Element element) {
        AbstractJClass baseActivityToLaunch = JClasses.asGeneratedClass(element.getEnclosingElement(), this);
        AbstractJClass activityToLaunch = GeneratedClasses.generatedActivityOrFragment(baseActivityToLaunch, this);
        AbstractJClass intentBuilder = searchIntentBuilder(element, (JDefinedClass) activityToLaunch);
        JInvocation intentBuilderInstance = JExpr._new(intentBuilder);
        IJStatement assignment = fieldRef.assign(intentBuilderInstance);
        targetBlock.add(assignment);
    }

    private AbstractJClass searchIntentBuilder(Element element, JDefinedClass generatedActivityToLaunch) {
        for (AbstractJClass innerClass : generatedActivityToLaunch.classes()) {
            if (isActivityLauncher(element, innerClass.name())) {
                return innerClass;
            }
        }
        throw new IllegalStateException();
    }

    private boolean isActivityLauncher(Element element, String className) {
        return className.endsWith(element.getSimpleName() + "Launcher_");
    }

    @Override
    public void validateEnclosingElement(Element element, ElementValidation valid) {
        validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, valid);
    }
}
