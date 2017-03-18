package de.nenick.androidannotations.plugin.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject View Callback instance.
 *
 * <pre>
 * &#64;EBean
 * &#64;EMvpView
 * public class MainView {
 *
 *   interface Callback {
 *     void onEvent();
 *   }
 *
 *   &#64;MvpCallback
 *   Callback callback;
 *
 * }
 * </pre>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface MvpCallback {
}
