package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification

class EMvpPresenterFragmentSpec extends BaseSpecification {

    def "Accept with @Fragment"() {
        given:
        def mainFragmentClass = fragment("MainFragment")
                .annotate(EFragment.class, "R.layout.fragment_main")
                .annotate(EMvpPresenter.class)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(mainFragmentClass)
                .with(layout("fragment_main"))
                .create()

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor("MainFragment")
    }

    def "Invalidate when missing @EFragment"() {
        given:
        def mainFragmentClass = fragment("MainFragment")
                .annotate(EMvpPresenter.class)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(mainFragmentClass)
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

