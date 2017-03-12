package de.nenick.androidannotations.plugin.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enhance an EActivity or EFragment as the Presenter class.
 *
 * <pre>
 * &#64;EActivity(R.layout.activity_main)
 * &#64;EMvpPresenter
 * public class MainActivity {
 * }
 * </pre>
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface EMvpPresenter {
}
