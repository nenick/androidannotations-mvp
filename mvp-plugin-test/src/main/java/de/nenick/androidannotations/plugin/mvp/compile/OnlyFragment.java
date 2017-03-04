package de.nenick.androidannotations.plugin.mvp.compile;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.R;

/**
 * This is not a real sample, just checks it would compile.
 */
@EMvpPresenter
@EFragment(R.layout.activity_main)
public class OnlyFragment extends Fragment {

}
