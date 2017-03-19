package de.nenick.androidannotations.plugin.mvp.sample.multiview;

import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.MvpCallback;
import de.nenick.androidannotations.plugin.mvp.sample.R;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;

@EBean
@EMvpView
class MultiView {

    interface Callback {
        void onClickButton();
    }

    @MvpCallback
    MultiView.Callback callback;

    @Click(R.id.button)
    void onClickButton() {
        callback.onClickButton();
    }
}
