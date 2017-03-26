package specs

import de.nenick.androidannotations.plugin.mvp.MvpActivity
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification
import tools.builder.ActivityBuilder
import tools.builder.FragmentBuilder
import tools.builder.ViewBuilder

class MvpActivityValidateSpec extends BaseSpecification {

    public static final String MAIN_FRAGMENT = "MainFragment"
    public static final String MAIN_ACTIVITY = "MainActivity"
    public static final String MAIN_VIEW = "MainView"
    public static final String OTHER_ACTIVITY = "OtherActivity"

    def "Accept in Activity with @EActivity"() {
        given:
        def otherActivityClass = activity(OTHER_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .with(activityLauncher(otherActivityClass), "otherActivity", MvpActivity.class)

        androidProjectWith(mainActivityClass, otherActivityClass)

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor(MAIN_ACTIVITY)
        assert containsGeneratedClassFor(OTHER_ACTIVITY)
    }

    def "Accept in Fragment with @EFragment"() {
        given:
        def otherActivityClass = activity(OTHER_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")

        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class, "R.layout.fragment_main")
                .with(activityLauncher(otherActivityClass), "otherActivity", MvpActivity.class)

        androidProjectWith(otherActivityClass, mainFragmentClass)

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor(MAIN_FRAGMENT)
        assert containsGeneratedClassFor(OTHER_ACTIVITY)
    }

    def "Invalidate in other then @EActivity or @EFragment"() {
        given:
        def otherActivityClass = activity(OTHER_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")

        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .with(activityLauncher(otherActivityClass), "otherActivity", MvpActivity.class)

        androidProjectWith(otherActivityClass, mainViewClass)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpActivity can only be used in a class annotated with @org.androidannotations.annotations.EActivity, @interface org.androidannotations.annotations.EFragment.")
        assert ex.message.contains('Element otherActivity invalidated by MvpActivityHandler')
    }

    def "Invalidate field type other than ActivityLauncher"() {
        given:
        def otherActivityClass = activity(OTHER_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")

        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class, "R.layout.fragment_main")
                .with(otherActivityClass, "otherActivity", MvpActivity.class)

        androidProjectWith(otherActivityClass, mainFragmentClass)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        assert ex.message.contains("MvpActivity can only be used on an element that extends one of the following classes: [de.nenick.androidannotations.plugin.mvp.ActivityLauncher]")
        assert ex.message.contains('Element otherActivity invalidated by MvpActivityHandler')
    }

    // TODO test that field can not be private

    private androidProjectWith(ActivityBuilder mainActivityClass, ActivityBuilder otherActivityClass) {
        def manifest = androidManifest(mainActivityClass)
            .with(otherActivityClass)

        androidProjectBuilder()
                .with(gradleScript())
                .with(manifest)
                .with(layout("activity_main"))
                .with(mainActivityClass)
                .with(otherActivityClass)
                .create()
    }

    private androidProjectWith(ActivityBuilder mainActivityClass, ViewBuilder mainViewClass) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest(mainActivityClass))
                .with(layout("activity_main"))
                .with(mainActivityClass)
                .with(mainViewClass)
                .create()
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
}

