package specs

import de.nenick.androidannotations.plugin.mvp.EMvpView
import org.androidannotations.annotations.EBean
import tools.BaseSpecification
import tools.builder.ViewBuilder

class EMvpViewSpec extends BaseSpecification {


    public static final String MAIN_VIEW = "MainView"

    def "Accept with @EBean"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        androidProjectWith(mainViewClass)

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor(MAIN_VIEW)
    }

    private androidProjectWith(ViewBuilder mainViewClass) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("android_annotations_need_an_generated_r_class"))
                .with(mainViewClass)
                .create()
    }

    def "Invalidate when missing @EBean"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EMvpView.class)

        androidProjectWith(mainViewClass)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("EMvpView can only be used in a class annotated with @org.androidannotations.annotations.EBean.")
        assert ex.message.contains('MainView invalidated by EMvpViewHandler')
    }
}

