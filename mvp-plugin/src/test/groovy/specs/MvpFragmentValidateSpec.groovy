package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import de.nenick.androidannotations.plugin.mvp.MvpFragment
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification
import tools.builder.ActivityBuilder
import tools.builder.FragmentBuilder

class MvpFragmentValidateSpec extends BaseSpecification {


    public static final String MAIN_FRAGMENT = "MainFragment"
    public static final String MAIN_ACTIVITY = "MainActivity"
    public static final String SECOND_FRAGMENT = "SecondFragment"

    def "Accept in Activity with @EMvpPresenter"() {
        given:
        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class, "R.layout.fragment_main")
                .annotate(EMvpPresenter.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainFragmentClass, "myFragment", MvpFragment.class)

        androidProjectWith(mainActivityClass, mainFragmentClass)

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor(MAIN_ACTIVITY)
        assert containsGeneratedClassFor(MAIN_FRAGMENT)
    }

    def "Accept in Fragment with @EMvpPresenter"() {
        given:
        def secondFragmentClass = fragment(SECOND_FRAGMENT)
                .annotate(EFragment.class, "R.layout.fragment_main")
                .annotate(EMvpPresenter.class)

        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class, "R.layout.fragment_main")
                .annotate(EMvpPresenter.class)
                .with(secondFragmentClass, "myFragment", MvpFragment.class)

        androidProjectWith(mainFragmentClass, secondFragmentClass)

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor(MAIN_FRAGMENT)
        assert containsGeneratedClassFor(SECOND_FRAGMENT)
    }

    def "Invalidate when missing @EMvpPresenter"() {
        given:
        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class, "R.layout.fragment_main")
                .annotate(EMvpPresenter.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .with(mainFragmentClass, "myFragment", MvpFragment.class)

        androidProjectWith(mainActivityClass, mainFragmentClass)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpFragment can only be used in a class annotated with @de.nenick.androidannotations.plugin.mvp.EMvpPresenter.")
        assert ex.message.contains('Element myFragment invalidated by MvpFragmentHandler')
    }

    def "Invalidate when reference miss @EMvpFragment"() {
        given:
        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class, "R.layout.fragment_main")

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainFragmentClass, "myFragment", MvpFragment.class)

        androidProjectWith(mainActivityClass, mainFragmentClass)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpFragment can only be used on an element annotated with @de.nenick.androidannotations.plugin.mvp.EMvpPresenter")
        assert ex.message.contains('Element myFragment invalidated by MvpFragmentHandler')
    }

    def "Invalidate when reference miss @EFragment"() {
        given:
        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EMvpPresenter.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainFragmentClass, "myFragment", MvpFragment.class)

        androidProjectWith(mainActivityClass, mainFragmentClass)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpFragment can only be used on an element annotated with @org.androidannotations.annotations.EFragment")
        assert ex.message.contains('Element myFragment invalidated by MvpFragmentHandler')
    }

    private androidProjectWith(ActivityBuilder mainActivityClass, FragmentBuilder mainFragmentClass) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest(mainActivityClass))
                .with(layout("activity_main"))
                .with(layout("fragment_main"))
                .with(mainActivityClass)
                .with(mainFragmentClass)
                .create()
    }

    private androidProjectWith(FragmentBuilder mainFragmentClass, FragmentBuilder secondFragmentClass) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("fragment_main"))
                .with(mainFragmentClass)
                .with(secondFragmentClass)
                .create()
    }
}

