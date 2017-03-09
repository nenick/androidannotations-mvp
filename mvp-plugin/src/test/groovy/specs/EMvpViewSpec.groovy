package specs

import de.nenick.androidannotations.plugin.mvp.EMvpView
import org.androidannotations.annotations.EBean
import tools.BaseSpecification

class EMvpViewSpec extends BaseSpecification {

    def "@EMvpView with @EBean"() {
        given:
        def mainView = view("MainView")
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("android_annotations_need_an_generated_r_class"))
                .with(mainView)
                .create()

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClass("MainView")
    }

    def "Just @EMvpView"() {
        given:
        def mainView = view("MainView")
                .annotate(EMvpView.class)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("android_annotations_need_an_generated_r_class"))
                .with(mainView)
                .create()

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("EMvpView can only be used in a class annotated with @org.androidannotations.annotations.EBean.")
        assert ex.message.contains('MainView invalidated by EMvpViewHandler')
    }
}

