package de.nenick.androidannotations.plugin.mvp.sample;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;

import de.nenick.androidannotations.plugin.mvp.ActivityLauncher;
import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.MvpActivity;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import de.nenick.androidannotations.plugin.mvp.sample.activity.ActivitySample_;
import de.nenick.androidannotations.plugin.mvp.sample.fragment.dynamics.DynamicsActivity_;
import de.nenick.androidannotations.plugin.mvp.sample.fragment.statics.StaticsActivity_;
import de.nenick.androidannotations.plugin.mvp.sample.multiview.MultiActivity_;

@EMvpPresenter
@EActivity(R.layout.activity_start)
public class StartActivity extends AppCompatActivity implements StartView.Callback {

    @MvpView
    StartView startView;

    @MvpActivity
    ActivityLauncher<ActivitySample_.IntentBuilder_> activitySample;

    @MvpActivity
    ActivityLauncher<DynamicsActivity_.IntentBuilder_> dynamicFragmentSample;

    @MvpActivity
    ActivityLauncher<StaticsActivity_.IntentBuilder_> staticFragmentSample;

    @MvpActivity
    ActivityLauncher<MultiActivity_.IntentBuilder_> multiViewSample;

    @Override
    public void onActivityExample() {
        activitySample.intent(this).start();
    }

    @Override
    public void onDynamicFragmentExample() {
        dynamicFragmentSample.intent(this).start();
    }

    @Override
    public void onStaticFragmentExample() {
        staticFragmentSample.intent(this).start();
    }

    @Override
    public void onMultiViewExample() {
        multiViewSample.intent(this).start();
    }
}
