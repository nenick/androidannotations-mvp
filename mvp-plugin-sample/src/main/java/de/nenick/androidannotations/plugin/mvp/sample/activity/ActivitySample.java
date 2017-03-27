package de.nenick.androidannotations.plugin.mvp.sample.activity;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import de.nenick.androidannotations.plugin.mvp.sample.R;


@EMvpPresenter
@EActivity(R.layout.activity_main)
public class ActivitySample extends AppCompatActivity implements ActivitySampleView.Callback {

    @MvpView
    ActivitySampleView myView;

    @AfterViews
    void initView() {
        myView.showMessage("It's connected!");
    }

    @Override
    public void onClickButton() {
        myView.showMessage("Button click feedback!");
    }
}
