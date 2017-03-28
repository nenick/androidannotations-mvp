# Available Annotations

List of all the annotations provided by this Plugin, with links to their documentation.

## Enhanced components

Annotation | Short Description
---|---
[@EMvpPresenter](EnhanceAsPresenter.md) | Enhance activity or fragment class as presenter.
[@EMvpView](EnhanceAsView.md) | Enhance bean class as view.

## Inject components

Annotation | Short Description
---|---
[@MvpActivity](EnhanceAsPresenter.md) | Inject new non static activity intent builder.
[@MvpFragment](EnhanceAsPresenter.md) | Inject new fragment instance enhanced with `@EMvpPresenter`.
[@MvpView](EnhanceAsPresenter.md) | Inject new view instance enhanced with `@EMvpView`.
[@MvpCallback](EnhanceAsView.md#presenter_callback) View | Inject existing presenter instance for view.
[@MvpCallback](EnhanceAsPresenter.md) Presenter | Inject existing parent presenter instance for fragment.