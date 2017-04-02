package de.nenick.androidannotations.plugin.mvp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private final Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void useAppContext() {
        assertEquals("Dummy check","de.nenick.androidannotations.plugin.mvp.sample", appContext.getPackageName());
    }
}
