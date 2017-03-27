package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import org.androidannotations.annotations.EActivity
import tools.BaseSpecification
import tools.builder.ActivityBuilder

class EMvpPresenterActivityValidateSpec extends BaseSpecification {


    public static final String MAIN_ACTIVITY = "MainActivity"

    def "Accept with @EActivity"() {
        given:

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)

        androidProjectWith(mainActivityClass)

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClassFor(MAIN_ACTIVITY)
    }

    def "Invalidate when missing @EActivity"() {
        given:
        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EMvpPresenter.class)

        androidProjectWith(mainActivityClass)

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        // Alternative syntax: def ex = thrown(InvalidDeviceException)
        assert ex.message.contains("EMvpPresenter can only be used in a class annotated with @org.androidannotations.annotations.EActivity, @interface org.androidannotations.annotations.EFragment.")
        assert ex.message.contains('MainActivity invalidated by EMvpPresenterHandler')
    }

    private androidProjectWith(ActivityBuilder mainActivityClass) {
        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest(mainActivityClass))
                .with(mainActivityClass)
                .with(layout("activity_main"))
                .create()
    }
}

