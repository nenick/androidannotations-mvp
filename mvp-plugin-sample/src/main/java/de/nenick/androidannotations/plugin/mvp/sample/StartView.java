package de.nenick.androidannotations.plugin.mvp.sample;

import android.support.v7.app.AppCompatActivity;
import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.MvpCallback;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;

@EBean
@EMvpView
public class StartView extends AppCompatActivity {

    interface Callback {
        void onActivityExample();

        void onDynamicFragmentExample();

        void onStaticFragmentExample();

        void onMultiViewExample();
    }

    @MvpCallback
    Callback callback;

    @Click(R.id.btn_activity_example)
    void onActivityExample() {
        callback.onActivityExample();
    }

    @Click(R.id.btn_dynamic_fragment_example)
    void onDynamicFragmentExample() {
        callback.onDynamicFragmentExample();
    }

    @Click(R.id.btn_static_fragment_example)
    void onStaticFragmentExample() {
        callback.onStaticFragmentExample();
    }

    @Click(R.id.btn_multi_view_example)
    void onMultiViewExample() {
        callback.onMultiViewExample();
    }
}
