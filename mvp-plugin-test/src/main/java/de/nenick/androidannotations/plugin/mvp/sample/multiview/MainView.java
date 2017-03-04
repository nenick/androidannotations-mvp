package de.nenick.androidannotations.plugin.mvp.sample.multiview;

import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.MvpViewCallback;
import de.nenick.androidannotations.plugin.mvp.R;

@EBean
@EMvpView
class MainView {

    interface Callback {
        void onClickButton();
    }

    @MvpViewCallback
    MainView.Callback callback;

    @Click(R.id.button)
    void onClickButton() {
        callback.onClickButton();
    }
}
