package de.nenick.androidannotations.plugin.mvp.sample.fragment.statics;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.sample.R;

@EMvpPresenter
@EActivity(R.layout.activity_main_fragment_statics)
public class StaticsActivity extends AppCompatActivity {

    @FragmentById(R.id.main_fragment)
    StaticsFragment mainFragment;

    @AfterViews
    void initView() {
        mainFragment.addMessage("It's connected!");
    }
}
