package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import de.nenick.androidannotations.plugin.mvp.EMvpView
import de.nenick.androidannotations.plugin.mvp.MvpView
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification

class MvpViewSpec extends BaseSpecification {

    def "@MvpView for @EMvpView"() {
        given:
        def mainView = view("MainView")
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        def mainActivity = activity("MainActivity")
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainView, "myView", MvpView.class)

        def androidManifest = androidManifest()
                .with(mainActivity)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest)
                .with(layout("activity_main"))
                .with(mainActivity)
                .with(mainView)
                .create()

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClass("MainView")
    }

    def "@MvpView missing @EMvpView"() {
        given:
        def mainView = view("MainView")
                .annotate(EBean.class)

        def mainActivity = activity("MainActivity")
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainView, "myView", MvpView.class)

        def androidManifest = androidManifest()
                .with(mainActivity)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest)
                .with(layout("activity_main"))
                .with(mainActivity)
                .with(mainView)
                .create()

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpView can only be used on an element annotated with @de.nenick.androidannotations.plugin.mvp.EMvpView")
        assert ex.message.contains('Element myView invalidated by MvpViewHandler')
    }

    def "@MvpView missing @EBean"() {
        given:
        def mainView = view("MainView")
                .annotate(EMvpView.class)

        def mainActivity = activity("MainActivity")
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainView, "myView", MvpView.class)

        def androidManifest = androidManifest()
                .with(mainActivity)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest)
                .with(layout("activity_main"))
                .with(mainActivity)
                .with(mainView)
                .create()

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpView can only be used on an element annotated with @org.androidannotations.annotations.EBean")
        assert ex.message.contains('Element myView invalidated by MvpViewHandler')
    }

    def "@MvpView in Fragment"() {
        given:
        def mainView = view("MainView")
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        def mainFragment = fragment("MainFragment")
                .annotate(EFragment.class, "R.layout.fragment_main")
                .annotate(EMvpPresenter.class)
                .with(mainView, "myView", MvpView.class)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("fragment_main"))
                .with(mainFragment)
                .with(mainView)
                .create()

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClass("MainView")
    }

    def "@MvpView in Activity"() {
        given:
        def mainView = view("MainView")
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        def mainFragment = activity("MainActivity")
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainView, "myView", MvpView.class)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("activity_main"))
                .with(mainFragment)
                .with(mainView)
                .create()

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClass("MainView")
    }
}

