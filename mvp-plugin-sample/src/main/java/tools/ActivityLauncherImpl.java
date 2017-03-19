package tools;

import android.content.Context;

import org.androidannotations.api.builder.IntentBuilder;

import de.nenick.androidannotations.plugin.mvp.ActivityLauncher;
import de.nenick.androidannotations.plugin.mvp.sample.activity.ActivitySample_;

public class ActivityLauncherImpl<A extends IntentBuilder> implements ActivityLauncher<A> {

    @Override
    public A intent(Context context) {
        return (A) ActivitySample_.intent(context);
    }
}
