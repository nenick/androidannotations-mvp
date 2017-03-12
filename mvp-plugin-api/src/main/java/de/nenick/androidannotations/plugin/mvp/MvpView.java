package de.nenick.androidannotations.plugin.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject a MVP View instance.
 *
 * <pre>
 * &#64;EActivity(R.layout.activity_main)
 * &#64;EMvpPresenter
 * public class MainActivity {
 *
 *   &#64;MvpView
 *   MyView myView;
 *
 * }
 * </pre>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface MvpView {
}
