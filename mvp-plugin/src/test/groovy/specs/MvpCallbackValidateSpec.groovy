package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import de.nenick.androidannotations.plugin.mvp.EMvpView
import de.nenick.androidannotations.plugin.mvp.MvpCallback
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification
import tools.builder.ActivityBuilder
import tools.builder.FragmentBuilder
import tools.builder.InterfaceBuilder
import tools.builder.ViewBuilder

class MvpCallbackValidateSpec extends BaseSpecification {



    public static final String MAIN_ACTIVITY = "MainFragment"
    public static final String MAIN_FRAGMENT = "MainFragment"
    public static final String MAIN_VIEW = "MainView"
    public static final String CALLBACK = "Callback"

    def "Accept in @EMvpView"() {
        given:
        def callbackInterface = callback(CALLBACK)

        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)
                .with(callbackInterface, "myCallback", MvpCallback.class)

        androidProjectWith(mainViewClass, callbackInterface)

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor(MAIN_VIEW)
    }

    def "Accept in Fragments with @EMvpPresenter"() {
        given:
        def callbackInterface = callback(CALLBACK)

        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class)
                .annotate(EMvpPresenter.class)
                .with(callbackInterface, "myCallback", MvpCallback.class)

        androidProjectWith(mainFragmentClass, callbackInterface)

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor(MAIN_FRAGMENT)
    }

    def "Invalidate in Activity with @EMvpPresenter"() {
        given:
        def callbackInterface = callback(CALLBACK)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class)
                .annotate(EMvpPresenter.class)
                .with(callbackInterface, "myCallback", MvpCallback.class)

        androidProjectWith(mainActivityClass, callbackInterface)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpCallback can only be used in a class annotated with @de.nenick.androidannotations.plugin.mvp.EMvpView, @interface org.androidannotations.annotations.EFragment.")
        assert ex.message.contains('Element myCallback invalidated by MvpCallbackHandler')
    }

    def "Invalidate when missing @EMvpView"() {
        given:
        def callbackInterface = callback(CALLBACK)

        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .with(callbackInterface, "myCallback", MvpCallback.class)

        androidProjectWith(mainViewClass, callbackInterface)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpCallback can only be used in a class annotated with @de.nenick.androidannotations.plugin.mvp.EMvpView, @interface de.nenick.androidannotations.plugin.mvp.EMvpPresenter.")
        assert ex.message.contains('Element myCallback invalidated by MvpCallbackHandler')
    }

    def "Invalidate when missing @EMvpPresenter"() {
        given:
        def callbackInterface = callback(CALLBACK)

        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class)
                .with(callbackInterface, "myCallback", MvpCallback.class)

        androidProjectWith(mainFragmentClass, callbackInterface)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpCallback can only be used in a class annotated with @de.nenick.androidannotations.plugin.mvp.EMvpView, @interface de.nenick.androidannotations.plugin.mvp.EMvpPresenter.")
        assert ex.message.contains('Element myCallback invalidated by MvpCallbackHandler')
    }

    // TODO test that field can not be private

    private androidProjectWith(ActivityBuilder mainActivityClass, InterfaceBuilder callbackInterface) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest(mainActivityClass))
                .with(mainActivityClass)
                .with(layout("activity_main"))
                .with(callbackInterface)
                .create()
    }

    private androidProjectWith(FragmentBuilder mainFragmentClass, InterfaceBuilder callbackInterface) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("androidannotations_needs_generated_r_class"))
                .with(mainFragmentClass)
                .with(callbackInterface)
                .create()
    }

    private androidProjectWith(ViewBuilder mainViewClass, InterfaceBuilder callbackInterface) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("androidannotations_needs_generated_r_class"))
                .with(mainViewClass)
                .with(callbackInterface)
                .create()
    }

}

