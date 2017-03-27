package de.nenick.androidannotations.plugin.mvp.sample.multiview;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import de.nenick.androidannotations.plugin.mvp.sample.R;


@EMvpPresenter
@EActivity(R.layout.activity_main)
public class MultiActivity extends AppCompatActivity implements MultiView.Callback {

    @MvpView
    MultiView myView;

    @MvpView
    MultiSubView mySubView;

    @AfterViews
    void initView() {
        mySubView.showMessage("It's connected!");
    }

    @Override
    public void onClickButton() {
        mySubView.showMessage("Button click feedback!");
    }
}
