package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.IJAssignmentTarget;
import com.helger.jcodemodel.IJStatement;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JCast;
import com.helger.jcodemodel.JClassAlreadyExistsException;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JVar;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.holder.EComponentWithViewSupportHolder;
import org.androidannotations.holder.GeneratedClassHolder;

import java.util.Collection;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import de.nenick.androidannotations.plugin.mvp.ActivityLauncher;
import de.nenick.androidannotations.plugin.mvp.MvpActivity;
import de.nenick.androidannotations.plugin.mvp.utils.PluginLists;
import de.nenick.androidannotations.plugin.mvp.utils.PluginTypeArguments;

import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;
import static org.androidannotations.helper.ModelConstants.generationSuffix;

/**
 * Handler for @{@link MvpActivity} annotation.
 */
public class MvpActivityHandler extends BaseAnnotationHandler<EComponentWithViewSupportHolder> implements MethodInjectionHandler<EComponentHolder> {

    private final InjectHelper<EComponentHolder> injectHelper;

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
        String activityName = readGeneratedActivityToLaunch(element);
        JDefinedClass activityClass = searchGeneratedActivityClass(activityName);
        JDefinedClass intentBuilderClass = searchIntentBuilderClass(activityClass);

        buildActivityLauncherClass(element, holder, activityClass, intentBuilderClass);

        injectHelper.process(element, holder);
    }

    private String readGeneratedActivityToLaunch(Element element) {
        TypeMirror activityLauncherType = PluginTypeArguments.getTypeElement(element);
        return readGeneratedActivityName(activityLauncherType);
    }

    private void buildActivityLauncherClass(Element element, EComponentWithViewSupportHolder holder, JDefinedClass activityClass, JDefinedClass intentBuilderClass) throws JClassAlreadyExistsException {
        JDefinedClass activityLauncherClass = holder.getGeneratedClass()._class(PUBLIC | STATIC, element.getSimpleName() + "Launcher" + generationSuffix());
        JMethod methodBuilder = activityLauncherClass.method(PUBLIC, intentBuilderClass, "intent");
        JVar contextParam = methodBuilder.param(getEnvironment().getClasses().CONTEXT, "context");

        JCast builderExpression = JExpr.cast(intentBuilderClass, activityClass.staticInvoke("intent").arg(contextParam));
        methodBuilder.body()._return(builderExpression);

        implementInterface(activityLauncherClass, intentBuilderClass);
    }

    private JDefinedClass searchIntentBuilderClass(JDefinedClass activityClass) {
        JDefinedClass intentBuilderClass = null;
        for (JDefinedClass innerClass : activityClass.classes()) {
            String innerClassName = innerClass.name();
            if(innerClassName == null) {
                throw new IllegalStateException();
            }
            if (innerClassName.endsWith("IntentBuilder_")) {
                intentBuilderClass = innerClass;
                break;
            }
        }
        return intentBuilderClass;
    }

    private JDefinedClass searchGeneratedActivityClass(String activityName) {
        JDefinedClass activityClass = null;
        Set<? extends Element> activityAndFragmentElements = getEnvironment().getValidatedElements().getRootAnnotatedElements(EActivity.class.getName());
        Set<? extends Element> fragments = getEnvironment().getValidatedElements().getRootAnnotatedElements(EFragment.class.getName());
        //noinspection unchecked
        activityAndFragmentElements.addAll((Collection) fragments);
        for (Element sharedPrefElement : activityAndFragmentElements) {
            GeneratedClassHolder sharedPrefHolder = getEnvironment().getGeneratedClassHolder(sharedPrefElement);
            String sharedPrefName = sharedPrefHolder.getGeneratedClass().name();

            if (activityName.equals(sharedPrefName)) {
                activityClass = sharedPrefHolder.getGeneratedClass();
                break;
            }
        }
        return activityClass;
    }

    private String readGeneratedActivityName(TypeMirror activityLauncherType) {
        String elementTypeName = activityLauncherType.toString();
        int indexOfLastDot = elementTypeName.lastIndexOf('.');
        int index = elementTypeName.lastIndexOf('.', indexOfLastDot - 1);
        elementTypeName = elementTypeName.substring(index + 1, indexOfLastDot);
        return elementTypeName;
    }

    @Override
    public JBlock getInvocationBlock(EComponentHolder holder) {
        return holder.getInitBodyInjectionBlock();
    }

    @Override
    public void assignValue(JBlock targetBlock, IJAssignmentTarget fieldRef, EComponentHolder holder, Element element, Element param) {
        injectActivityLauncher(targetBlock, fieldRef, element, param);
    }

    private void injectActivityLauncher(JBlock targetBlock, IJAssignmentTarget fieldRef, Element element, Element param) {
        AbstractJClass generatedClass = getJClass(annotationHelper.generatedClassQualifiedNameFromQualifiedName(element.getEnclosingElement().asType().toString()));
        generatedClass = searchGeneratedActivityClass(generatedClass.name());
        AbstractJClass activityLauncher = null;
        for (AbstractJClass innerClass : ((JDefinedClass) generatedClass).classes()) {
            if (innerClass.name().endsWith(element.getSimpleName() + "Launcher_")) {
                activityLauncher = innerClass;
                break;
            }
        }

        if(activityLauncher == null) {
            throw new IllegalStateException();
        }

        JInvocation intentBuilderInstance = JExpr._new(activityLauncher);
        IJStatement assignment = fieldRef.assign(intentBuilderInstance);
        targetBlock.add(assignment);
    }

    @Override
    public void validateEnclosingElement(Element element, ElementValidation valid) {
        validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, valid);
    }

    private void implementInterface(JDefinedClass holder, AbstractJClass callbackClass) {
        AbstractJClass impl = getJClass(ActivityLauncher.class);
        impl = impl.narrow(callbackClass);

        holder._implements(impl);
    }
}
