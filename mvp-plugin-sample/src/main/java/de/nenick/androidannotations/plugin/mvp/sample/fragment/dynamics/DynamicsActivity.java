package de.nenick.androidannotations.plugin.mvp.sample.fragment.dynamics;

import android.support.v7.app.AppCompatActivity;
import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.MvpFragment;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import de.nenick.androidannotations.plugin.mvp.sample.R;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EMvpPresenter
@EActivity(R.layout.activity_main_fragment_dynamics)
public class DynamicsActivity extends AppCompatActivity implements DynamicsActivityView.Callback {

    @MvpView
    DynamicsActivityView myView;

    @MvpFragment
    DynamicsFragment mainFragment;

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
