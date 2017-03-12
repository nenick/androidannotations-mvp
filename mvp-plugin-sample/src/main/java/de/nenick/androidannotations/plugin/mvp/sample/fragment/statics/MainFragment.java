package de.nenick.androidannotations.plugin.mvp.sample.fragment.statics;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import de.nenick.androidannotations.plugin.mvp.R;

@EMvpPresenter
@EFragment(R.layout.activity_main)
public class MainFragment extends Fragment implements MainFragmentView.Callback {

    @MvpView
    MainFragmentView view;

    void addMessage(String message) {
        view.addMessage(message);
    }

    @Override
    public void onClickButton() {
        view.showMessage("Button click feedback!");
    }

    @AfterViews
    void afterView() {
        view.showMessage("Fragment static! ");
    }
}
