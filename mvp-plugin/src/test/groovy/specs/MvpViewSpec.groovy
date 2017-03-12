package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import de.nenick.androidannotations.plugin.mvp.EMvpView
import de.nenick.androidannotations.plugin.mvp.HasMvpViewType
import de.nenick.androidannotations.plugin.mvp.MvpView
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification
import tools.builder.ActivityBuilder
import tools.builder.FragmentBuilder
import tools.builder.ViewBuilder

class MvpViewSpec extends BaseSpecification {


    public static final String MAIN_VIEW = "MainView"
    public static final String MAIN_ACTIVITY = "MainActivity"
    public static final String MAIN_FRAGMENT = "MainFragment"

    def "Accept in Activity with @EMvpPresenter"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainViewClass, "myView", MvpView.class)

        androidProjectWith(mainActivityClass, mainViewClass)

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor(MAIN_VIEW)
        assert containsGeneratedClassFor(MAIN_ACTIVITY)
    }

    def "Accept in Fragment with @EMvpPresenter"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class, "R.layout.fragment_main")
                .annotate(EMvpPresenter.class)
                .with(mainViewClass, "myView", MvpView.class)

        androidProjectWith(mainFragmentClass, mainViewClass)

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor(MAIN_VIEW)
        assert containsGeneratedClassFor(MAIN_FRAGMENT)
    }

    def "Invalidate when missing @EMvpPresenter"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .with(mainViewClass, "myView", MvpView.class)

        androidProjectWith(mainActivityClass, mainViewClass)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpView can only be used in a class annotated with @de.nenick.androidannotations.plugin.mvp.EMvpPresenter.")
        assert ex.message.contains('Element myView invalidated by MvpViewHandler')
    }

    def "Invalidate when reference miss @EMvpView"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainViewClass, "myView", MvpView.class)

        androidProjectWith(mainActivityClass, mainViewClass)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpView can only be used on an element annotated with @de.nenick.androidannotations.plugin.mvp.EMvpView")
        assert ex.message.contains('Element myView invalidated by MvpViewHandler')
    }

    def "Invalidate when reference miss @EBean"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EMvpView.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainViewClass, "myView", MvpView.class)

        androidProjectWith(mainActivityClass, mainViewClass)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpView can only be used on an element annotated with @org.androidannotations.annotations.EBean")
        assert ex.message.contains('Element myView invalidated by MvpViewHandler')
    }

    def "Call onCreate with empty view does not throw"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainViewClass, "myView", MvpView.class)

        androidProjectWith(mainActivityClass, mainViewClass)

        run(assembleDebugTask)

        def mainActivity = activityInstance(MAIN_ACTIVITY)

        when:
        mainActivity.onCreate()

        then:
        noExceptionThrown()
    }

    private androidProjectWith(ActivityBuilder mainActivityClass, ViewBuilder mainViewClass) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest(mainActivityClass))
                .with(layout("activity_main"))
                .with(mainActivityClass)
                .with(mainViewClass)
                .create()
    }

    private androidProjectWith(FragmentBuilder mainFragmentClass, ViewBuilder mainViewClass) {

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("fragment_main"))
                .with(mainFragmentClass)
                .with(mainViewClass)
                .create()
    }
}

