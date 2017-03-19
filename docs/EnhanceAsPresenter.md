# Enhance as Presenter

## Enhance Activities

> Since MVP Plugin v1.0.0

To start using MVP features in a activity, annotate it with `@EMvpPresenter`:

```
@EActivity
@EMvpPresenter
public class MyActivity extends Activity {

}
```

## Enhance Fragments

> Since MVP Plugin v1.0.0

To start using MVP features in a fragment, annotate it with `@EMvpPresenter`:

```
@EFragment
@EMvpPresenter
public class MyFragment extends Fragment {

}
```

## Start other Activities

> Since MVP Plugin v1.0.0

You can inject an instance of an activity builder to launch activities without static method access.

```
@MvpActivity
ActivityLauncher<AnyActivity_.IntentBuilder_> anyActivity;

void onEvent() {
    anyActivity.intent(this).start();
}    
```

You can use all common builder functions from origin AndroidAnnotations to build intent and start activity with it.

## Inject Fragments (other Presenters)

> Since MVP Plugin v1.0.0

You can inject new instance of a fragment annotated with `@EMvpPresenter`.
If you have declared your fragments in your layouts then AndroidAnnotations provide similar functionality with annotations like [@FragmentById (TODO link)]().

Origin AndroidAnnotations doesn't provide annotations to inject new fragment instances.
 Mostly because creating new fragment instance needs extra logic sometime e.g. setting some initial values.
 But often it is enough to just build it, so we can show it anytime if we want.

```
@MvpFragment
AnyFragment anyFragment;
```

## Inject Views

You can inject new instance of a bean annotated with `@EMvpView`.

```
@MvpView
MyView myView;
```