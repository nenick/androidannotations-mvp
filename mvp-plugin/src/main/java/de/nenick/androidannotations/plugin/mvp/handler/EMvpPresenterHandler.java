package de.nenick.androidannotations.plugin.mvp.handler;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.holder.EComponentWithViewSupportHolder;

import javax.lang.model.element.Element;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;

/**
 * Handler for @{@link EMvpPresenter} annotation.
 */
public class EMvpPresenterHandler extends BaseAnnotationHandler<EComponentWithViewSupportHolder> {

    public EMvpPresenterHandler(AndroidAnnotationsEnvironment environment) {
        super(EMvpPresenter.class, environment);
    }

    /**
     * - Expect annotated class has also an {@link EActivity} or {@link EFragment} annotation.
     */
    @Override
    protected void validate(Element element, ElementValidation validation) {
        validatorHelper.hasEActivityOrEFragment(element, validation);
    }

    @Override
    public void process(Element element, EComponentWithViewSupportHolder holder) {
        // current it has no explicit jop, just mark class as presenter
    }
}
