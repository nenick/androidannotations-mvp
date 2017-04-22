# AndroidAnnotations MVP Plugin

[![CircleCI branch](https://img.shields.io/circleci/project/github/nenick/androidannotations-mvp/master.svg)](https://circleci.com/gh/nenick/androidannotations-mvp)
[![Codacy grade](https://img.shields.io/codacy/grade/cd0e4c895cb3452885b838f8a1aef25c.svg)](https://www.codacy.com/app/nico_kuechler/androidannotations-mvp)
[![Codacy branch coverage](https://img.shields.io/codacy/coverage/cd0e4c895cb3452885b838f8a1aef25c/master.svg)](https://www.codacy.com/app/nico_kuechler/androidannotations-mvp)

Plugin provides annotations for Model-View-Presenter (MVP) pattern.
This plugin follows the approach that [activities and fragments are presenters](http://www.techyourchance.com/activities-android/).


## Installation

Add AndroidAnnotations and MVP plugin to your android module.

```
repositories {
    maven {
        url "http://dl.bintray.com/nenick/maven"
    }
}

def AndroidAnnotations = 4.3.0
def AndroidAnnotationsMvp = 1.0.0 

deoendencies {
    apt "org.androidannotations:androidannotations:$AndroidAnnotations"
    compile "org.androidannotations:androidannotations:$AndroidAnnotations"
    
    apt "de.nenick:androidannotations-mvp:$AndroidAnnotationsMvp"
    compile "de.nenick:androidannotations-mvp-api:$AndroidAnnotationsMvp"
}
```

## Usage

### Enhance components

Base annotations to enable MVP plugin features.

Annotation | Short Description
---|---
[@EMvpPresenter](EnhanceAsPresenter.md) | Enhance activity or fragment class as presenter.
[@EMvpView](EnhanceAsView.md) | Enhance bean class as view.

### Inject components

Annotation | Short Description
---|---
[@MvpActivity](docs/EnhanceAsPresenter.md) | Inject new non static MVP presenter activity intent builder.
[@MvpFragment](docs/EnhanceAsPresenter.md) | Inject new MVP presenter fragment instance.
[@MvpView](docs/EnhanceAsPresenter.md) | Inject new MVP view instance.
[@MvpCallback](docs/EnhanceAsView.md#presenter_callback) View | Inject existing MVP presenter instance for MVP view.
[@MvpCallback](docs/EnhanceAsPresenter.md) Presenter | Inject existing parent MVP presenter instance for MVP presenter fragment.

### Cookbook 

* [Show fragments](docs/CookManageFragments.md)
* [Navigation to another screen](docs/CookViewNavigation.md)

### ShowCase

Example how code may separated ... 

<table>
    <tr>
        <th>Typical activity class</td>
        <th>MVP Presenter</td>
        <th>MVP View</td>
    </tr>
    <tr>
<td><pre>

    @EActivity(R.layout.activity-my.xml)
    class MyActivity extends Activity {

        @ViewById(R.id.myEditText)
        EditText editText;

        @ViewById(R.id.myTextView)
        TextView textView;

        @ViewById(R.id.fragmentContainer)
        Layout container;

        @Click(R.id.myButton)
        onTextInput() {
            String text = editText.getText();
            // .. do some computing with text
            textView.setText(text)
        }

        @Click(R.id.myButtonNav)
        onNavigation() {
            MySecondActivity_.intent(this).start()
        }

        @AfterViews
        showFragment() {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();   

            Fragment myFragment = new MyFragment();
            ft.add(container.getId(), myFragment , "my-fragment");
            ft.commit();
        }
    }

</pre></td>
<td><pre>

    @EMvpPresenter
    @EActivity(R.layout.activity-my.xml)
    class MyActivity extends Activity implements MyView.Callback {

        @MvpView
        MyView = view;

        @MvpActivity
        ActivityLauncher<MySecondActivity_.IntentBuilder_> mySecondActivity;

        @MvpFragment
        MyFragment fragment;

        @AfterViews
        void showFragment() {
            view.show(fragment);
        }

        @Override
        void onTextInput(String text) {
            // .. do some computing with text
            view.showComputingResult(text)
        }

        @Override
        void onNavigation() {
            mySecondActivity.intent(this).start();
        }
    }

</pre></td>
<td><pre>

    @EMvpView
    @EBean
    class MyView {

        interface Callback {
            void onTextInput(String text);
            void onNavigation();
            FragmentManager getFragmentManager();
        }

        @ViewById(R.id.myEditText)
        EditText editText;

        @ViewById(R.id.myTextView)
        TextView textView;

        @ViewById(R.id.fragmentContainer)
        Layout container;

        @MvpCallback
        Callback callback;

        @Click(R.id.myButton)
        void onMyButton() {
            callback.onTextInput(editText.getText());
        }

        void showComputingResult(String text) {
            textView.setText(text)
        }

        void show(Fragment fragment) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();   
            ft.add(container.getId(), fragment, "my-fragment");
            ft.commit();
        }
    }
    
</pre></td>
</tr>
</table>

## Support

For any issue or question please [open an issue](https://github.com/nenick/androidannotations-mvp/issues/new) for support.

## Contributing

Please contribute using [Github Flow](https://guides.github.com/introduction/flow/). 
Create a branch, add commits, and [open a pull request](https://github.com/nenick/androidannotations-mvp/compare/).

### Build Instructions

Build and run MVP plugin tests

* from project root execute `./gradlew check`
* from project root execute `./gradlew connectedCheck` (needs android device or emulator)

Release MVP plugin

* update `RELEASE_PLAN.md` with new release information
* from project root execute  `./gradlew release -Prelease.version=X.X.X`
* after release is done, update `README.md` with latest project version number
