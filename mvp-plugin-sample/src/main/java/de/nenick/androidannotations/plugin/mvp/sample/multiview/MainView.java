package de.nenick.androidannotations.plugin.mvp.sample.multiview;

import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.MvpViewCallback;
import de.nenick.androidannotations.plugin.mvp.R;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;

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
