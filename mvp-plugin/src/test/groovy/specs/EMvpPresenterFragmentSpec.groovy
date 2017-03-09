package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification

class EMvpPresenterFragmentSpec extends BaseSpecification {

    def "@EMvpPresenter with @Fragment"() {
        given:
        def mainFragment = fragment("MainFragment")
                .annotate(EFragment.class, "R.layout.fragment_main")
                .annotate(EMvpPresenter.class)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(mainFragment)
                .with(layout("fragment_main"))
                .create()

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClass("MainFragment")
    }

    def "Just @EMvpPresenter"() {
        given:
        def mainFragment = fragment("MainFragment")
                .annotate(EMvpPresenter.class)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(mainFragment)
                .with(layout("fragment_main"))
                .create()

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("EMvpPresenter can only be used in a class annotated with @org.androidannotations.annotations.EActivity, @interface org.androidannotations.annotations.EFragment.")
        assert ex.message.contains('MainFragment invalidated by EMvpPresenterHandler')
    }

}

