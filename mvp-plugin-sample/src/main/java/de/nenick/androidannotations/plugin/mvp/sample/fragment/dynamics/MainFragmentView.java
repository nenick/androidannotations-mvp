package de.nenick.androidannotations.plugin.mvp.sample.fragment.dynamics;

import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.MvpViewCallback;
import de.nenick.androidannotations.plugin.mvp.R;

@EBean
@EMvpView
class MainFragmentView {


    interface Callback {
        void onClickButton();
    }

    @MvpViewCallback
    MainFragmentView.Callback callback;

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
