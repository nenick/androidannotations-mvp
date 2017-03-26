package de.nenick.androidannotations.plugin.mvp.utils;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

/**
 * Custom utils for generic type arguments.
 */
public class PluginTypeArguements {

    /**
     * Get first type argument.
     *
     * @param element Element with generic type arguments.
     * @return Type element.
     */
    public static TypeMirror getTypeElement(Element element) {
        return ((DeclaredType) element.asType()).getTypeArguments().get(0);
    }

}
