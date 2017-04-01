package de.nenick.androidannotations.plugin.mvp.utils;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

/**
 * Utils for element type arguments.
 */
public final class JTypeArguments {

    private JTypeArguments() {
        // mark this class as static utility class
    }

    /**
     * Get the first elements type argument.
     * <p>
     * <pre>
     *     ClassWithTypeArgument{@literal <}MyTypeArgument{@literal <} variable;
     * </pre>
     * <p>
     * Typically call it `genericTypeArgument((DeclaredType) element.asType())`.
     *
     * @param element Element with generic type arguments.
     * @return Type argument.
     */
    public static TypeMirror genericTypeArgument(Element element) {
        List<? extends TypeMirror> typeArguments = typeArguments((DeclaredType) element.asType());
        return firstTypeArgument(typeArguments);
    }

    private static List<? extends TypeMirror> typeArguments(DeclaredType type) {
        return type.getTypeArguments();
    }

    private static TypeMirror firstTypeArgument(List<? extends TypeMirror> elementTypes) {
        return elementTypes.get(0);
    }

}
