package de.nenick.androidannotations.plugin.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject an Activity launcher.
 *
 * <pre>
 * {@literal @}EMvpPresenter
 * {@literal @}EActivity(R.layout.activity_main)
 * public class MainActivity extends Activity {
 *
 *   {@literal @}MvpActivity
 *   ActivityLauncher{@literal <}OtherActivity_.IntentBuilder_{@literal >} otherActivity;
 *
 *   void onEvent() {
 *      otherActivity.intent(this).start();
 *   }
 * }
 * </pre>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface MvpActivity {
}
