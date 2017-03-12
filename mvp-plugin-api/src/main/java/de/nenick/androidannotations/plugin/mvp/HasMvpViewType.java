package de.nenick.androidannotations.plugin.mvp;

public interface HasMvpViewType<T> {
    void setViewCallback(T viewCallback);
}
