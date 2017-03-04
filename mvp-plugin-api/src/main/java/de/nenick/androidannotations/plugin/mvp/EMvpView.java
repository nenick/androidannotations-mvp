package de.nenick.androidannotations.plugin.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enhance class for elementary view operations.
 *
 * Available annotations:
 * - @ViewById
 * - @ViewByName
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface EMvpView {

}
