package specs

import de.nenick.androidannotations.plugin.mvp.EMvpView
import de.nenick.androidannotations.plugin.mvp.HasMvpCallback
import de.nenick.androidannotations.plugin.mvp.MvpCallback
import org.androidannotations.annotations.EBean
import tools.BaseSpecification
import tools.builder.InterfaceBuilder
import tools.builder.ViewBuilder

class MvpCallbackGenerateSpec extends BaseSpecification {


    public static final String CALLBACK = "Callback"
    public static final String MAIN_VIEW = "MainView"

    def "Generated view with callback injection implements HasMvpCallback"() {
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

