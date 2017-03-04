package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JFieldVar;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.holder.EComponentWithViewSupportHolder;

import javax.lang.model.element.Element;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;

public class EMvpPresenterHandler extends BaseAnnotationHandler<EComponentWithViewSupportHolder> {

    public EMvpPresenterHandler(AndroidAnnotationsEnvironment environment) {
        super(EMvpPresenter.class, environment);
    }

    @Override
    protected void validate(Element element, ElementValidation validation) {
        validatorHelper.hasEActivityOrEFragment(element, validation);
        // the annotation only can be used in an enhanced class
    }

    @Override
    public void process(Element element, EComponentWithViewSupportHolder holder) throws Exception {
        //JMethod toString = holder.getGeneratedClass().method(JMod.PRIVATE, Void.TYPE, "updateViewInstanceElements");

        //toString.body().directStatement("((OnViewChangedListener) view).onViewChanged((HasViews) this);");
        //toString.body()._return(null);

        //JFieldVar view = holder.getGeneratedClass().field(JMod.PROTECTED, getClasses().VIEW, "view");

        // creates a method in the generated class:
        // @Override
        // public String toString() {
        //   return "Hello, AndroidAnnotations!";
        // }


        //holder.getOnViewChangedBodyBeforeInjectionBlock().invoke("updateViewInstanceElements");
    }
}
