# Enhance as Presenter

## Enhance Activities

To start using MVP features in a activity, annotate it with `@EMvpPresenter`:

```
@EActivity
@EMvpPresenter
public class MyActivity extends Activity {

}
```

## Enhance Fragments

To start using MVP features in a fragment, annotate it with `@EMvpPresenter`:

```
@EFragment
@EMvpPresenter
public class MyFragment extends Fragment {

}
```

## MVP Annotations

You can use all annotations for [@EActivity](https://github.com/androidannotations/androidannotations/wiki/Enhance-activities) or [@EFragment](https://github.com/androidannotations/androidannotations/wiki/Enhance-Fragments).

And additional MVP annotations:

```
@MvpFragment
AnyFragment anyFragment;

@MvpView
MyView myView;
```