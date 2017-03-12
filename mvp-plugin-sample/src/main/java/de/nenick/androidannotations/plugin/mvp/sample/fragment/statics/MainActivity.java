package de.nenick.androidannotations.plugin.mvp.sample.fragment.statics;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;
import de.nenick.androidannotations.plugin.mvp.R;

@EMvpPresenter
@EActivity(R.layout.activity_main_fragment_statics)
public class MainActivity extends AppCompatActivity {

    @FragmentById(R.id.main_fragment)
    MainFragment mainFragment;

    @AfterViews
    void initView() {
        mainFragment.addMessage("It's connected!");
    }
}
