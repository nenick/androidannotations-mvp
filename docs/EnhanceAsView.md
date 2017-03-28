# Enhance as View

To start using MVP features in a Bean class, annotate it with `@EMvpView`:

```
@EBean
@EMvpView
public class MyView {

}
```

## Inject Presenter Callback<a name="presenter_callback"></a>

With `@MvpCallback` you can automatically let inject the back reference to the presenter instance.

```
@EBean
@EMvpView
public class MyView {

    interface Callback {
        void onSomeEvent()
    }

    @MvpCallback
    Callback callback;
}

@EActivity
@EMvpPresenter
public class AnyPresenter implements MyView.Callback {

    @Override
    void onSomeEvent() {
        // do something here
    }

}

```