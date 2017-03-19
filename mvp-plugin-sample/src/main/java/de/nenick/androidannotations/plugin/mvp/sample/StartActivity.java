package de.nenick.androidannotations.plugin.mvp.sample;

import android.support.v7.app.AppCompatActivity;
import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import de.nenick.androidannotations.plugin.mvp.sample.activity.ActivitySample_;
import de.nenick.androidannotations.plugin.mvp.sample.fragment.dynamics.DynamicsActivity_;
import de.nenick.androidannotations.plugin.mvp.sample.fragment.statics.StaticsActivity_;
import de.nenick.androidannotations.plugin.mvp.sample.multiview.MultiActivity_;
import org.androidannotations.annotations.EActivity;

@EMvpPresenter
@EActivity(R.layout.activity_start)
public class StartActivity extends AppCompatActivity implements StartView.Callback {

    @MvpView
    StartView startView;

    @Override
    public void onActivityExample() {
        ActivitySample_.intent(this).start();
    }

    @Override
    public void onDynamicFragmentExample() {
        DynamicsActivity_.intent(this).start();
    }

    @Override
    public void onStaticFragmentExample() {
        StaticsActivity_.intent(this).start();
    }

    @Override
    public void onMultiViewExample() {
        MultiActivity_.intent(this).start();
    }
}
