package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.IJAssignmentTarget;
import com.helger.jcodemodel.IJStatement;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JConditional;
import com.helger.jcodemodel.JFieldVar;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EBeanHolder;
import org.androidannotations.holder.EComponentHolder;

import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.HasMvpViewType;
import de.nenick.androidannotations.plugin.mvp.MvpViewCallback;

import static com.helger.jcodemodel.JExpr._null;

/**
 * Inject MVP presenter instance into annotated field.
 * <p>
 * This class is mostly a copy of the @{@link Bean} annotation.
 * Then it was adjusted to handle the @{@link MvpViewCallback} annotation now.
 */
public class MvpViewCallbackHandler extends BaseAnnotationHandler<EComponentHolder> {

    public MvpViewCallbackHandler(AndroidAnnotationsEnvironment environment) {
        super(MvpViewCallback.class, environment);
    }

    @Override
    public void validate(Element element, ElementValidation validation) {

        validatorHelper.enclosingElementHasAnnotation(EMvpView.class, element, validation);
        validatorHelper.isNotPrivate(element, validation);
    }

    @Override
    public void process(Element element, EComponentHolder holder) {
        holder.getGeneratedClass()._implements(HasMvpViewType.class);

        JMethod toString = holder.getGeneratedClass().method(JMod.PUBLIC, Void.TYPE, "setViewCallback");
        Element elementType = annotationHelper.getTypeUtils().asElement(element.asType());
        toString.annotate(Override.class);
        //toString.param(getJClass(elementType.toString()), "viewCallback");
        toString.param(getClasses().OBJECT, "viewCallback");
        toString.body().directStatement("this." + element.getSimpleName() + " = ("+ elementType.getSimpleName() +") viewCallback;");
    }
}
