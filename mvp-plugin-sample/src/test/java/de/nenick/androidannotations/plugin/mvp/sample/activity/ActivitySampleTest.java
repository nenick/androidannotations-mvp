package de.nenick.androidannotations.plugin.mvp.sample.activity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class ActivitySampleTest {

    @Mock
    protected ActivitySampleView activitySampleView;

    @InjectMocks
    protected ActivitySample activitySample;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldShowInitialMessage() throws Exception {
        activitySample.initView();
        verify(activitySampleView).showMessage("It's connected!");
    }

    @Test
    public void shouldGiveButtonClickFeedback() throws Exception {
        activitySample.onClickButton();
        verify(activitySampleView).showMessage("Button click feedback!");
    }
}