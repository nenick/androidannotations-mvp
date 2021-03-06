package de.nenick.androidannotations.plugin.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject a Fragment Presenter instance.
 *
 * <pre>
 * &#64;EMvpPresenter
 * &#64;EActivity(R.layout.activity_main)
 * public class MainActivity extends Activity {
 *
 *   &#64;MvpFragment
 *   MyFragment myFragment;
 *
 * }
 * </pre>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface MvpFragment {
}
