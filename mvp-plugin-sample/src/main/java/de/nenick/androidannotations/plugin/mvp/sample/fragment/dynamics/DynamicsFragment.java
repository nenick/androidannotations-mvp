package de.nenick.androidannotations.plugin.mvp.sample.fragment.dynamics;

import android.support.v4.app.Fragment;
import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.MvpView;
import de.nenick.androidannotations.plugin.mvp.sample.R;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EMvpPresenter
@EFragment(R.layout.activity_main)
public class DynamicsFragment extends Fragment implements DynamicsFragmentView.Callback {

    @MvpView
    DynamicsFragmentView view;

    void addMessage(String message) {
        view.addMessage(message);
    }

    @Override
    public void onClickButton() {
       view.showMessage("Button click feedback!");
    }

    @AfterViews
    void afterView() {
        view.showMessage("Fragment dynamic! ");
    }
}
