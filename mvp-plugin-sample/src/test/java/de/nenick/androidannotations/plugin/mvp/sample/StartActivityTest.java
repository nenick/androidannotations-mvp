package de.nenick.androidannotations.plugin.mvp.sample;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.nenick.androidannotations.plugin.mvp.ActivityLauncher;
import de.nenick.androidannotations.plugin.mvp.sample.activity.ActivitySample_;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StartActivityTest {

    @Mock
    ActivitySample_.IntentBuilder_ intentBuilder;

    @Mock
    ActivityLauncher<ActivitySample_.IntentBuilder_> activitySample;

    @InjectMocks
    protected StartActivity startActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldStartActivitySample() {
        when(activitySample.intent(startActivity)).thenReturn(intentBuilder);
        startActivity.onActivityExample();
        verify(intentBuilder).start();
    }
}