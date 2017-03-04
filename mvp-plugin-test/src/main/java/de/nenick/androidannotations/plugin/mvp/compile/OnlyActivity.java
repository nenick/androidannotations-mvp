package de.nenick.androidannotations.plugin.mvp.compile;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.R;

/**
 * This is not a real sample, just checks it would compile.
 */
@EMvpPresenter
@EActivity(R.layout.activity_main)
public class OnlyActivity extends AppCompatActivity {

}
