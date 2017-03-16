package de.nenick.androidannotations.plugin.mvp.sample.fragment.dynamics;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import de.nenick.androidannotations.plugin.mvp.EMvpView;
import de.nenick.androidannotations.plugin.mvp.MvpViewCallback;
import de.nenick.androidannotations.plugin.mvp.R;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

@EBean
@EMvpView
class MainActivityView {

    interface Callback {
        FragmentManager getSupportFragmentManager();
    }

    @MvpViewCallback
    Callback callback;

    @ViewById(R.id.container)
    FrameLayout container;

    void showContainerFragment(Fragment fragment) {
        callback.getSupportFragmentManager().beginTransaction().add(container.getId(), fragment, null).commit();
        callback.getSupportFragmentManager().executePendingTransactions();
    }
}
