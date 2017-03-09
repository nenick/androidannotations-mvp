package specs

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import org.androidannotations.annotations.EActivity
import tools.BaseSpecification

import tools.builder.ActivityBuilder

class EMvpPresenterActivitySpec extends BaseSpecification {

    def "@EMvpPresenter with @EActivity"() {
        given:
        projectWith(activity("MainActivity")
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class))

        when:
        run(assembleDebugTask)

        then:
        assert containsGeneratedClass("MainActivity")
    }

    def "Just @EMvpPresenter"() {
        given:
        projectWith(activity("MainActivity")
                .annotate(EMvpPresenter.class))

        when:
        run(assembleDebugTask)

        then:
        Exception ex = thrown()
        // Alternative syntax: def ex = thrown(InvalidDeviceException)
        assert ex.message.contains("EMvpPresenter can only be used in a class annotated with @org.androidannotations.annotations.EActivity, @interface org.androidannotations.annotations.EFragment.")
        assert ex.message.contains('MainActivity invalidated by EMvpPresenterHandler')
    }

    private void projectWith(ActivityBuilder mainActivity) {
        def androidManifest = androidManifest()
                .with(mainActivity)

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest)
                .with(mainActivity)
                .with(layout("activity_main"))
                .create()
    }
}

