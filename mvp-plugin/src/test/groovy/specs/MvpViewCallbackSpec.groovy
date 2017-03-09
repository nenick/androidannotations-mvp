package specs

import de.nenick.androidannotations.plugin.mvp.EMvpView
import de.nenick.androidannotations.plugin.mvp.MvpViewCallback
import org.androidannotations.annotations.EBean
import tools.BaseSpecification

class MvpViewCallbackSpec extends BaseSpecification {

    def "@MvpViewCallback in @EMvpView"() {
        given:

        def presenter = presenter("IPresenter")

        def mainView = view("MainView")
                .annotate(EBean.class)
                .annotate(EMvpView.class)
                .with(presenter, "myCallback", MvpViewCallback.class)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("androidannotations_needs_generated_r_class"))
                .with(mainView)
                .with(presenter)
                .create()

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClass("MainView")
    }

    def "@MvpViewCallback missing @EMvpView"() {
        given:

        def presenter = presenter("IPresenter")

        def mainView = view("MainView")
                .annotate(EBean.class)
                .with(presenter, "myCallback", MvpViewCallback.class)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("androidannotations_needs_generated_r_class"))
                .with(mainView)
                .with(presenter)
                .create()

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpViewCallback can only be used in a class annotated with @de.nenick.androidannotations.plugin.mvp.EMvpView.")
        assert ex.message.contains('Element myCallback invalidated by MvpViewCallbackHandler')
    }

}

