package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import de.nenick.androidannotations.plugin.mvp.MvpFragment
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification

class MvpFragmentSpec extends BaseSpecification {

    def "@MvpFragment in Activity with @EMvpPresenter"() {
        given:
        def mainFragment = fragment("MainFragment")
                .annotate(EFragment.class, "R.layout.fragment_main")
                .annotate(EMvpPresenter.class)

        def mainActivity = activity("MainActivity")
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainFragment, "myFragment", MvpFragment.class)

        def androidManifest = androidManifest()
                .with(mainActivity)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest)
                .with(layout("activity_main"))
                .with(layout("fragment_main"))
                .with(mainActivity)
                .with(mainFragment)
                .create()

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClass("MainActivity")
    }

    def "@MvpFragment missing @EMvpFragment"() {
        given:
        def mainFragment = fragment("MainFragment")
                .annotate(EFragment.class, "R.layout.fragment_main")

        def mainActivity = activity("MainActivity")
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainFragment, "myFragment", MvpFragment.class)

        def androidManifest = androidManifest()
                .with(mainActivity)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest)
                .with(layout("activity_main"))
                .with(layout("fragment_main"))
                .with(mainActivity)
                .with(mainFragment)
                .create()

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpFragment can only be used on an element annotated with @de.nenick.androidannotations.plugin.mvp.EMvpPresenter")
        assert ex.message.contains('Element myFragment invalidated by MvpFragmentHandler')
    }

    def "@MvpFragment missing @EFragment"() {
        given:
        def mainFragment = fragment("MainFragment")
                .annotate(EMvpPresenter.class)

        def mainActivity = activity("MainActivity")
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainFragment, "myFragment", MvpFragment.class)

        def androidManifest = androidManifest()
                .with(mainActivity)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest)
                .with(layout("activity_main"))
                .with(layout("fragment_main"))
                .with(mainActivity)
                .with(mainFragment)
                .create()

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpFragment can only be used on an element annotated with @org.androidannotations.annotations.EFragment")
        assert ex.message.contains('Element myFragment invalidated by MvpFragmentHandler')
    }

}

