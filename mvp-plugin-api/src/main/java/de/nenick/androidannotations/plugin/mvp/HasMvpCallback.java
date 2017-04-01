package de.nenick.androidannotations.plugin.mvp;

/**
 * Marks an class to require a callback instance.
 */
public interface HasMvpCallback<T> {
    void setCallback(T viewCallback);
}
