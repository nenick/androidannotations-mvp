package de.nenick.androidannotations.plugin.mvp.sample.fragment.statics;

import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.MvpCallback;
import de.nenick.androidannotations.plugin.mvp.sample.R;

@EBean
@EMvpView
class StaticsFragmentView {

    interface Callback {
        void onClickButton();
    }

    @MvpCallback
    StaticsFragmentView.Callback callback;

    @ViewById(R.id.textView)
    TextView textView;

    @Click(R.id.button)
    void onClickButton() {
        callback.onClickButton();
    }

    void showMessage(String message) {
        textView.setText(message);
    }

    void addMessage(String message) {
        showMessage(textView.getText() + message);
    }
}
