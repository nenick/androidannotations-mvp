package de.nenick.androidannotations.plugin.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject view instance.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface MvpFragment {
}
