package de.nenick.androidannotations.plugin.mvp.sample.activity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class MainActivityTest {

    @Mock
    protected MainView mainView;

    @InjectMocks
    protected MainActivity mainActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldShowInitialMessage() throws Exception {
        mainActivity.initView();
        verify(mainView).showMessage("It's connected!");
    }

    @Test
    public void shouldGiveButtonClickFeedback() throws Exception {
        mainActivity.onClickButton();
        verify(mainView).showMessage("Button click feedback!");
    }
}