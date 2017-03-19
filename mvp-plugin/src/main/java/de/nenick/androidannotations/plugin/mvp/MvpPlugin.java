package de.nenick.androidannotations.plugin.mvp;


import de.nenick.androidannotations.plugin.mvp.handler.EMvpPresenterHandler;
import de.nenick.androidannotations.plugin.mvp.handler.EMvpViewHandler;
import de.nenick.androidannotations.plugin.mvp.handler.MvpActivityHandler;
import de.nenick.androidannotations.plugin.mvp.handler.MvpFragmentHandler;
import de.nenick.androidannotations.plugin.mvp.handler.MvpCallbackHandler;
import de.nenick.androidannotations.plugin.mvp.handler.MvpViewHandler;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.handler.AnnotationHandler;
import org.androidannotations.plugin.AndroidAnnotationsPlugin;

import java.util.ArrayList;
import java.util.List;

public class MvpPlugin extends AndroidAnnotationsPlugin {

    @Override
    public String getName() {
        return "mvp-plugin";
    }

    @Override
    public List<AnnotationHandler<?>> getHandlers(AndroidAnnotationsEnvironment androidAnnotationEnv) {
        List<AnnotationHandler<?>> annotationHandlers = new ArrayList<>();
        annotationHandlers.add(new EMvpPresenterHandler(androidAnnotationEnv));
        annotationHandlers.add(new EMvpViewHandler(androidAnnotationEnv));
        annotationHandlers.add(new MvpCallbackHandler(androidAnnotationEnv));
        annotationHandlers.add(new MvpViewHandler(androidAnnotationEnv));
        annotationHandlers.add(new MvpFragmentHandler(androidAnnotationEnv));
        annotationHandlers.add(new MvpActivityHandler(androidAnnotationEnv));
        return annotationHandlers;
    }
}
