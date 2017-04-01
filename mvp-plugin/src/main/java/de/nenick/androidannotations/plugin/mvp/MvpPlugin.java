package de.nenick.androidannotations.plugin.mvp;


import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.handler.AnnotationHandler;
import org.androidannotations.plugin.AndroidAnnotationsPlugin;

import java.util.ArrayList;
import java.util.List;

import de.nenick.androidannotations.plugin.mvp.handler.EMvpPresenterHandler;
import de.nenick.androidannotations.plugin.mvp.handler.EMvpViewHandler;
import de.nenick.androidannotations.plugin.mvp.handler.MvpActivityHandler;
import de.nenick.androidannotations.plugin.mvp.handler.MvpCallbackHandler;
import de.nenick.androidannotations.plugin.mvp.handler.MvpFragmentHandler;
import de.nenick.androidannotations.plugin.mvp.handler.MvpViewHandler;

/**
 * Register plugin annotations handler.
 */
public class MvpPlugin extends AndroidAnnotationsPlugin {

    @Override
    public String getName() {
        return "mvp-plugin";
    }

    @Override
    public List<AnnotationHandler<?>> getHandlers(AndroidAnnotationsEnvironment environment) {
        List<AnnotationHandler<?>> annotationHandlers = new ArrayList<>();
        annotationHandlers.add(new EMvpPresenterHandler(environment));
        annotationHandlers.add(new EMvpViewHandler(environment));
        annotationHandlers.add(new MvpCallbackHandler(environment));
        annotationHandlers.add(new MvpViewHandler(environment));
        annotationHandlers.add(new MvpFragmentHandler(environment));
        annotationHandlers.add(new MvpActivityHandler(environment));
        return annotationHandlers;
    }
}
