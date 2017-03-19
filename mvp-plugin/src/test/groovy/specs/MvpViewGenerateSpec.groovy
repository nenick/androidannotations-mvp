package specs

import android.widget.TextView
import de.nenick.androidannotations.plugin.mvp.EMvpPresenter
import de.nenick.androidannotations.plugin.mvp.EMvpView
import de.nenick.androidannotations.plugin.mvp.HasMvpCallback
import de.nenick.androidannotations.plugin.mvp.MvpView
import de.nenick.test.application.MainView
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.ViewById
import org.androidannotations.api.view.HasViews
import org.androidannotations.api.view.OnViewChangedListener
import tools.BaseSpecification
import tools.builder.ActivityBuilder
import tools.builder.FragmentBuilder
import tools.builder.ViewBuilder

class MvpViewGenerateSpec extends BaseSpecification {


    public static final String MAIN_VIEW = "MainView"
    public static final String MAIN_ACTIVITY = "MainActivity"

    def "Test view mock simulates view with view injection"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)
                .with(TextView.class, "textView", ViewById.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainViewClass, "myView", MvpView.class)

        androidProjectWith(mainActivityClass, mainViewClass)
        run(assembleDebugTask)

        when:
        def mainView = viewInstance(MAIN_VIEW)

        then:
        assert mainView.hasInterface(OnViewChangedListener.class)
        assert ViewMock.class.interfaces.contains(OnViewChangedListener.class)
    }

    def "Call onCreate when view implements no interfaces does not throw"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainViewClass, "myView", MvpView.class)

        androidProjectWith(mainActivityClass, mainViewClass)
        run(assembleDebugTask)

        when:
        def mainView = viewInstance(MAIN_VIEW)

        then:
        assert !mainView.hasInterface(HasViews.class)
        assert !mainView.hasInterface(HasMvpCallback.class)

        when:
        activityInstance(MAIN_ACTIVITY).onCreate()

        then:
        noExceptionThrown()
    }

    def "Call onCreate injects view instance"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainViewClass, "myView", MvpView.class)

        androidProjectWith(mainActivityClass, mainViewClass)
        run(assembleDebugTask)

        when:
        def mainActivity = activityInstance(MAIN_ACTIVITY)

        then:
        assert mainActivity.getMyView() == null

        when:
        mainActivity.onCreate()

        then:
        assert mainActivity.getMyView() != null
    }

    def "Call onViewChanged updates view instance"() {
        given:
        def mainViewClass = view(MAIN_VIEW)
                .annotate(EBean.class)
                .annotate(EMvpView.class)

        def mainActivityClass = activity(MAIN_ACTIVITY)
                .annotate(EActivity.class, "R.layout.activity_main")
                .annotate(EMvpPresenter.class)
                .with(mainViewClass, "myView", MvpView.class)

        androidProjectWith(mainActivityClass, mainViewClass)
        run(assembleDebugTask)
        def mock = new ViewMock()

        when:
        def mainActivity = activityInstance(MAIN_ACTIVITY)
        mainActivity.setMyView(mock)

        then:
        assert !mock.onViewChangedCalled

        when:
        mainActivity.onViewChanged()

        then:
        assert mock.onViewChangedCalled
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

    private androidProjectWith(FragmentBuilder mainFragmentClass, ViewBuilder mainViewClass) {

        androidProjectBuilder()
                .with(gradleScript())
                .with(androidManifest())
                .with(layout("fragment_main"))
                .with(mainFragmentClass)
                .with(mainViewClass)
                .create()
    }

    class ViewMock extends MainView implements OnViewChangedListener {

        boolean onViewChangedCalled

        @Override
        void onViewChanged(HasViews hasViews) {
            onViewChangedCalled = true
        }
    }
}

