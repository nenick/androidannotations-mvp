package de.nenick.androidannotations.plugin.mvp.handler;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.holder.EComponentWithViewSupportHolder;

import javax.lang.model.element.Element;

public class EMvpPresenterHandler extends BaseAnnotationHandler<EComponentWithViewSupportHolder> {

    public EMvpPresenterHandler(AndroidAnnotationsEnvironment environment) {
        super(EMvpPresenter.class, environment);
    }

    @Override
    protected void validate(Element element, ElementValidation validation) {
        validatorHelper.hasEActivityOrEFragment(element, validation);
    }

    @Override
    public void process(Element element, EComponentWithViewSupportHolder holder) throws Exception {
        // current it has no explicit jop, just mark class as presenter
    }
}
