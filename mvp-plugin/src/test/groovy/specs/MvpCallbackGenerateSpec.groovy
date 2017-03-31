package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import de.nenick.androidannotations.plugin.mvp.EMvpView
import de.nenick.androidannotations.plugin.mvp.HasMvpCallback
import de.nenick.androidannotations.plugin.mvp.MvpCallback
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification
import tools.builder.FragmentBuilder
import tools.builder.InterfaceBuilder
import tools.builder.ViewBuilder

class MvpCallbackGenerateSpec extends BaseSpecification {


    public static final String CALLBACK = "Callback"
    public static final String MAIN_VIEW = "MainView"
    public static final String MAIN_FRAGMENT = "MainFragment"

    def "Generated view implements HasMvpCallback"() {
        given:
        def callbackInterface = callback(CALLBACK)

        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)
                .with(callbackInterface, "myCallback", MvpCallback.class)

        androidProjectWith(mainViewClass, callbackInterface)
        run(assembleDebugTask)

        when:
        def mainView = viewInstance(MAIN_VIEW)

        then:
        assert mainView.hasInterface(HasMvpCallback.class)
    }

    def "Generated fragment implements HasMvpCallback"() {
        given:
        def callbackInterface = callback(CALLBACK)

        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class)
                .annotate(EMvpPresenter.class)
                .with(callbackInterface, "myCallback", MvpCallback.class)

        androidProjectWith(mainFragmentClass, callbackInterface)
        run(assembleDebugTask)

        when:
        def mainView = fragmentInstance(MAIN_FRAGMENT)

        then:
        assert mainView.hasInterface(HasMvpCallback.class)
    }

    def "Generated class injects callback instance"() {
        given:
        def callbackInterface = callback(CALLBACK)

        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class)
                .annotate(EMvpPresenter.class)
                .impl(callbackInterface)

        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)
                .with(callbackInterface, "myCallback", MvpCallback.class)

        androidProjectWith(mainFragmentClass, mainViewClass, callbackInterface)
        run(assembleDebugTask)

        when:
        def mainView = viewInstance(MAIN_VIEW)
        def mainFragment = fragmentInstance(MAIN_FRAGMENT).instance
        mainView.setCallback(mainFragment)

        then:
        assert mainView.getCallback() == mainFragment
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

    private androidProjectWith(FragmentBuilder mainFragmentClass, ViewBuilder mainViewClass, InterfaceBuilder callbackInterface) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("androidannotations_needs_generated_r_class"))
                .with(mainFragmentClass)
                .with(mainViewClass)
                .with(callbackInterface)
                .create()
    }
}

