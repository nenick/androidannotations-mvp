package de.nenick.androidannotations.plugin.mvp.sample.fragment.dynamics;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.MvpFragment;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import de.nenick.androidannotations.plugin.mvp.R;

@EMvpPresenter
@EActivity(R.layout.activity_main_fragment_dynamics)
public class MainActivity extends AppCompatActivity implements MainActivityView.Callback {

    @MvpView
    MainActivityView myView;

    @MvpFragment
    MainFragment mainFragment;

    @AfterViews
    void initView() {
        myView.showContainerFragment(mainFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainFragment.addMessage("It's connected!");
    }
}
