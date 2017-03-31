package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import de.nenick.androidannotations.plugin.mvp.MvpFragment
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EFragment
import tools.BaseSpecification
import tools.builder.ActivityBuilder
import tools.builder.FragmentBuilder

class MvpFragmentGenerateSpec extends BaseSpecification {


    public static final String MAIN_FRAGMENT = "MainFragment"
    public static final String MAIN_ACTIVITY = "MainActivity"

    def "Generated activity onCreate create fragment instance"() {
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
        def mainActivity = activityInstance(MAIN_ACTIVITY)

        then:
        mainActivity.getMyFragment() == null

        when:
        mainActivity.onCreate()

        then:
        mainActivity.getMyFragment() != null
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

