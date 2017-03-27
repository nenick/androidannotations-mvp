package de.nenick.androidannotations.plugin.mvp.sample.activity;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class ActivitySampleViewTest {

    private static final String TEST_MESSAGE = "Some test message!";

    @Mock
    protected ActivitySampleView.Callback callback;

    @Mock
    protected TextView textView;

    @InjectMocks
    protected ActivitySampleView activitySampleView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldDelegateButtonClick() throws Exception {
        activitySampleView.onClickButton();
        verify(callback).onClickButton();
    }

    @Test
    public void shouldShowGivenMessage() throws Exception {
        activitySampleView.showMessage(TEST_MESSAGE);
        verify(textView).setText(TEST_MESSAGE);
    }
}