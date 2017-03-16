package de.nenick.androidannotations.plugin.mvp.handler;

import de.nenick.androidannotations.plugin.mvp.EMvpView;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.EBean;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.holder.EComponentHolder;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class EMvpViewHandler extends BaseAnnotationHandler<EComponentHolder> {

    public EMvpViewHandler(AndroidAnnotationsEnvironment environment) {
        super(EMvpView.class, environment);
    }

    @Override
    public void validate(Element element, ElementValidation validation) {
        List<Class<? extends Annotation>> validAnnotations = new ArrayList<>();
        validAnnotations.add(EBean.class);
        validatorHelper.hasOneOfAnnotations(element, element, validAnnotations, validation);
    }

    @Override
    public void process(Element element, EComponentHolder holder) {
        // current it has no explicit jop, just mark class as view
    }
}
