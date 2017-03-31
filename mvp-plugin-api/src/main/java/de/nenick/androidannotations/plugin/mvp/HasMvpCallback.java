package de.nenick.androidannotations.plugin.mvp;

public interface HasMvpCallback<T> {
    void setCallback(T viewCallback);
}
