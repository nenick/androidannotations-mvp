package de.nenick.androidannotations.plugin.mvp.sample.activity;

import android.view.View;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ActivityViewFieldTest {
    @Test
    public void shouldAddViewField() throws Exception {
        //Field view = searchClassField(MainActivity_.class, "myView");
        //assertNotNull(view);
        //assertEquals(view.getType(), MainView.class);
    }

    private Field searchClassField(Class clazz, String fieldName) {
        Field view = null;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field: declaredFields) {
            if(field.getName().equals(fieldName)) {
                view = field;
            }
        }
        return view;
    }
}