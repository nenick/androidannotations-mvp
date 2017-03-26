package specs

import de.nenick.androidannotations.plugin.mvp.EMvpView
import de.nenick.androidannotations.plugin.mvp.HasMvpCallback
import de.nenick.androidannotations.plugin.mvp.MvpCallback
import org.androidannotations.annotations.EBean
import tools.BaseSpecification
import tools.builder.InterfaceBuilder
import tools.builder.ViewBuilder

class MvpCallbackValidateSpec extends BaseSpecification {


    public static final String CALLBACK = "Callback"
    public static final String MAIN_VIEW = "MainView"

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
        assert ex.message.contains("MvpCallback can only be used in a class annotated with @de.nenick.androidannotations.plugin.mvp.EMvpView.")
        assert ex.message.contains('Element myCallback invalidated by MvpCallbackHandler')
    }

    // TODO test that field can not be private

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

