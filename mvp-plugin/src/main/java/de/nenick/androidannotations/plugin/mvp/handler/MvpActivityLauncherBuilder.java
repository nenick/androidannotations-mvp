package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JCast;
import com.helger.jcodemodel.JClassAlreadyExistsException;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JVar;

import org.androidannotations.holder.EComponentWithViewSupportHolder;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import de.nenick.androidannotations.plugin.mvp.ActivityLauncher;
import de.nenick.androidannotations.plugin.mvp.MvpActivity;
import de.nenick.androidannotations.plugin.mvp.utils.GeneratedClasses;
import de.nenick.androidannotations.plugin.mvp.utils.JClasses;
import de.nenick.androidannotations.plugin.mvp.utils.JMethods;
import de.nenick.androidannotations.plugin.mvp.utils.JTypeArguments;
import de.nenick.androidannotations.plugin.mvp.utils.PluginBaseAnnotationHandler;
import de.nenick.androidannotations.plugin.mvp.utils.PluginClasses;

import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;
import static org.androidannotations.helper.ModelConstants.generationSuffix;

/**
 * {@link ActivityLauncher} Builder for @{@link MvpActivity} annotation.
 */
/* default */ class MvpActivityLauncherBuilder {

    private final PluginBaseAnnotationHandler base;

    /* default */ MvpActivityLauncherBuilder(PluginBaseAnnotationHandler base) {
        this.base = base;
    }

    /* default */ void process(Element element, EComponentWithViewSupportHolder holder)
            throws JClassAlreadyExistsException {
        String activityName = readGeneratedActivityToLaunch(JTypeArguments.genericTypeArgument(element));
        JDefinedClass activityToLaunch = GeneratedClasses.generatedActivityOrFragment(activityName, base);
        JDefinedClass intentBuilderClass = GeneratedClasses.intentBuilder(activityToLaunch);
        buildActivityLauncherClass(element, holder.getGeneratedClass(), activityToLaunch, intentBuilderClass);
    }

    private String readGeneratedActivityToLaunch(TypeMirror elementType) {
        return getActivityName(elementType.toString());
    }

    private void buildActivityLauncherClass(Element element, JDefinedClass generatedClass, JDefinedClass activityClass,
                                            JDefinedClass intentBuilderClass) throws JClassAlreadyExistsException {
        String activityLauncherName = element.getSimpleName() + "Launcher" + generationSuffix();
        JDefinedClass activityLauncherClass = generatedClass._class(PUBLIC | STATIC, activityLauncherName);
        buildIntentMethodMethod(activityLauncherClass, activityClass, intentBuilderClass);
        JClasses.implementsInterface(activityLauncherClass, intentBuilderClass, ActivityLauncher.class, base);
    }

    private void buildIntentMethodMethod(JDefinedClass activityLauncherClass,
                                         JDefinedClass activityClass, JDefinedClass intentBuilderClass) {
        JMethod methodBuilder = activityLauncherClass.method(PUBLIC, intentBuilderClass, "intent");
        JVar contextParam = buildIntentMethodSignature(methodBuilder);
        JInvocation intentBuilderInstance = JMethods.staticInvoke(activityClass, "intent", contextParam);
        JCast builderExpression = JExpr.cast(intentBuilderClass, intentBuilderInstance);
        buildIntentMethodMethodBody(builderExpression, JMethods.body(methodBuilder));
    }

    private void buildIntentMethodMethodBody(JCast builderExpression, JBlock methodBody) {
        methodBody._return(builderExpression);
    }

    private JVar buildIntentMethodSignature(JMethod methodBuilder) {
        return methodBuilder.param(PluginClasses.context(base), "context");
    }

    private String getActivityName(String elementTypeName) {
        int indexOfLastDot = elementTypeName.lastIndexOf('.');
        int index = elementTypeName.lastIndexOf('.', indexOfLastDot - 1);
        return elementTypeName.substring(index + 1, indexOfLastDot);
    }
}
