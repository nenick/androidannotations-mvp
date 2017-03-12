package de.nenick.androidannotations.plugin.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enhance a simple EBean as the View class.
 *
 * <pre>
 * &#64;EBean
 * &#64;EMvpView
 * public class MainView {
 * }
 * </pre>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface EMvpView {

}
