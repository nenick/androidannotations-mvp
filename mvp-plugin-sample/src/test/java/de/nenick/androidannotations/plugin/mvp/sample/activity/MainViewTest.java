package de.nenick.androidannotations.plugin.mvp.sample.activity;

import android.widget.TextView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class MainViewTest {

    private static final String TEST_MESSAGE = "Some test message!";

    @Mock
    protected MainView.Callback callback;

    @Mock
    protected TextView textView;

    @InjectMocks
    protected MainView mainView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldDelegateButtonClick() throws Exception {
        mainView.onClickButton();
        verify(callback).onClickButton();
    }

    @Test
    public void shouldShowGivenMessage() throws Exception {
        mainView.showMessage(TEST_MESSAGE);
        verify(textView).setText(TEST_MESSAGE);
    }
}