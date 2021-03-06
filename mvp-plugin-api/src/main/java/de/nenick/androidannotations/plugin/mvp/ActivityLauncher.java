package de.nenick.androidannotations.plugin.mvp;

import android.content.Context;

import org.androidannotations.api.builder.IntentBuilder;

/**
 * Non static access to {@link IntentBuilder}.
 */
public interface ActivityLauncher<A extends IntentBuilder> {
    A intent(Context context);
}
