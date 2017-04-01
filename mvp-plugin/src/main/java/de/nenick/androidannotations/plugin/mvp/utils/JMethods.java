package de.nenick.androidannotations.plugin.mvp.utils;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.IJExpression;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JVar;

import org.androidannotations.holder.EBeanHolder;

/**
 * Utils for element methods.
 */
public final class JMethods {

    private JMethods() {
        // mark this class as static utility class
    }

    public static JBlock body(JMethod method) {
        return method.body();
    }

    public static void invoke(JBlock atTargetCodeBlock, JMethod method, Class paramType, String paramVariableName) {
        JVar param = method.param(paramType, paramVariableName);
        JInvocation invocation = atTargetCodeBlock.invoke(method);
        withArgument(invocation, param);
    }

    public static JInvocation invokeBeanGetInstance(AbstractJClass cls, IJExpression contextRef) {
        JInvocation invocation = staticInvoke(cls, EBeanHolder.GET_INSTANCE_METHOD_NAME);
        withArgument(invocation, contextRef);
        return invocation;
    }

    public static JInvocation invokeFragmentCreation(AbstractJClass cls) {
        JInvocation builder = staticInvoke(cls, "builder");
        return invoke(builder, "build");
    }

    public static JInvocation staticInvoke(AbstractJClass cls, String methodName, JVar param) {
        JInvocation invocation = cls.staticInvoke(methodName);
        withArgument(invocation, param);
        return invocation;
    }

    private static void withArgument(JInvocation invocation, IJExpression param) {
        invocation.arg(param);
    }

    private static JInvocation invoke(JInvocation builder, String methodName) {
        return builder.invoke(methodName);
    }

    private static JInvocation staticInvoke(AbstractJClass cls, String methodName) {
        return cls.staticInvoke(methodName);
    }
}
