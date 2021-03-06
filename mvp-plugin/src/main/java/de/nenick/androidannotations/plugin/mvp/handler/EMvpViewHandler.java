package de.nenick.androidannotations.plugin.mvp.handler;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.EBean;
import org.androidannotations.holder.EComponentHolder;

import javax.lang.model.element.Element;

import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.utils.PluginBaseAnnotationHandler;
import de.nenick.androidannotations.plugin.mvp.utils.PluginLists;

/**
 * Handler for @{@link EMvpView} annotation.
 */
public class EMvpViewHandler extends PluginBaseAnnotationHandler<EComponentHolder> {

    public EMvpViewHandler(AndroidAnnotationsEnvironment environment) {
        super(EMvpView.class, environment);
    }

    /**
     * - Expect annotated class has also an {@link EBean} annotation.
     */
    @Override
    public void validate(Element element, ElementValidation validation) {
        validatorHelper.hasOneOfAnnotations(element, element, PluginLists.singleton(EBean.class), validation);
    }

    @Override
    public void process(Element element, EComponentHolder holder) {
        // current it has no explicit jop, just mark class as view
    }
}
