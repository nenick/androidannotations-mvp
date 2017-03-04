package de.nenick.androidannotations.plugin.mvp.handler;

import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JFieldRef;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.EBean;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.helper.IdValidatorHelper;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.rclass.IRClass;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.HasMvpViewType;

/**
 * Does nothing ???
 */
public class EMvpViewHandler extends BaseAnnotationHandler<EComponentHolder> {

    public EMvpViewHandler(AndroidAnnotationsEnvironment environment) {
        super(EMvpView.class, environment);

    }

    @Override
    public void validate(Element element, ElementValidation validation) {
        List<Class<? extends Annotation>> validAnnotations = new ArrayList<>();
        validAnnotations.add(EBean.class);
        validatorHelper.hasOneOfAnnotations(element, element, validAnnotations, validation);
        //validatorHelper.typeOrTargetValueHasAnnotation(EMvpView.class, element, validation);
        validatorHelper.isNotPrivate(element, validation);

    }

    @Override
    public void process(Element element, EComponentHolder holder) {
        //holder.getGeneratedClass().annotate(EBean.class);
/*
        List<JFieldRef> fieldRefs = annotationHelper.extractAnnotationFieldRefs(element, IRClass.Res.LAYOUT, false);

        JFieldRef contentViewId = null;
        if (fieldRefs.size() == 1) {
            contentViewId = fieldRefs.get(0);
        }

        JMethod toString = holder.getGeneratedClass().method(JMod.PUBLIC, Integer.TYPE, "getContentViewId");
        toString.annotate(Override.class);
        //toString.param(getJClass(elementType.toString()), "viewCallback");
        toString.body()._return(contentViewId);
        */
    }
}
