package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import de.nenick.androidannotations.plugin.mvp.MvpActivity
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification
import tools.builder.ActivityBuilder
import tools.builder.FragmentBuilder

class MvpActivityGenerateSpec extends BaseSpecification {

    public static final String MAIN_ACTIVITY = "MainActivity"
    public static final String MAIN_FRAGMENT = "MainFragment"

    def "Generated activity onCreate injects ActivityLauncher"() {
        given:
        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)

        mainActivityClass
                .with(activityLauncher(mainActivityClass), "otherActivity", MvpActivity.class)

        androidProjectWith(mainActivityClass)
        run(assembleDebugTask)

        when:
        def mainActivity = activityInstance(MAIN_ACTIVITY)

        then:
        assert mainActivity.getOtherActivityLauncher() == null

        when:
        mainActivity.onCreate()

        then:
        assert mainActivity.getOtherActivityLauncher() != null
    }

    def "Generated fragment onCreate injects ActivityLauncher"() {
        given:
        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)

        def mainFragmentClass = fragment(MAIN_FRAGMENT)
                .annotate(EFragment.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(activityLauncher(mainActivityClass), "otherActivity", MvpActivity.class)

        androidProjectWith(mainActivityClass, mainFragmentClass)
        run(assembleDebugTask)

        when:
        def mainFragment = fragmentInstance(MAIN_FRAGMENT)

        then:
        assert mainFragment.getOtherActivityLauncher() == null

        when:
        mainFragment.onCreate()

        then:
        assert mainFragment.getOtherActivityLauncher() != null
    }

    def "Generated activity launcher creates generated IntentBuilder"() {
        given:
        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)

        mainActivityClass
                .with(activityLauncher(mainActivityClass), "otherActivity", MvpActivity.class)

        androidProjectWith(mainActivityClass)
        run(assembleDebugTask)
        def mainActivity = activityInstance(MAIN_ACTIVITY)
        mainActivity.onCreate()

        when:
        def activityLauncher = mainActivity.getOtherActivityLauncher()
        def intentBuilder = activityLauncher.intent(mainActivity.instance);

        then:
        assert intentBuilder.class.name == 'de.nenick.test.application.MainActivity_$IntentBuilder_'
    }

    private androidProjectWith(ActivityBuilder mainActivityClass, FragmentBuilder mainFragmentClass) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest(mainActivityClass))
                .with(layout("activity_main"))
                .with(mainActivityClass)
                .with(mainFragmentClass)
                .create()
    }

    private androidProjectWith(ActivityBuilder mainActivityClass) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest(mainActivityClass))
                .with(layout("activity_main"))
                .with(mainActivityClass)
                .create()
    }

}

