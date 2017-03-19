package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import de.nenick.androidannotations.plugin.mvp.MvpActivity
import org.androidannotations.annotations.EActivity
import tools.BaseSpecification
import tools.builder.ActivityBuilder

class MvpActivityGenerateSpec extends BaseSpecification {

    public static final String MAIN_ACTIVITY = "MainActivity"

    def "Call onCreate injects ActivityLauncher"() {
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

    private androidProjectWith(ActivityBuilder mainActivityClass) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest(mainActivityClass))
                .with(layout("activity_main"))
                .with(mainActivityClass)
                .create()
    }

}

