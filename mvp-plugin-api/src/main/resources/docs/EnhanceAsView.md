# Enhance as View

To start using MVP features in a Bean class, annotate it with `@EMvpView`:

```
@EBean
@EMvpView
public class MyView {

}
```

## MVP Annotations

You can use all annotations for [@EBean](https://github.com/androidannotations/androidannotations/wiki/Enhance-custom-classes).

And additional MVP annotations:

```
@MvpViewCallback
AnyFragment anyFragment;

@MvpView
MyView myView;
```