package de.nenick.androidannotations.plugin.mvp.sample.multiview;

import android.widget.TextView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.sample.R;

@EBean
@EMvpView
class MultiSubView {

    @ViewById(R.id.textView)
    TextView textView;

    void showMessage(String message) {
        textView.setText(message);
    }
}
