[![CircleCI branch](https://img.shields.io/circleci/project/github/nenick/androidannotations-mvp/master.svg)](https://circleci.com/gh/nenick/androidannotations-mvp)
[![Codacy grade](https://img.shields.io/codacy/grade/cd0e4c895cb3452885b838f8a1aef25c.svg)](https://www.codacy.com/app/nico_kuechler/androidannotations-mvp)
[![Codacy branch coverage](https://img.shields.io/codacy/coverage/cd0e4c895cb3452885b838f8a1aef25c/master.svg)](https://www.codacy.com/app/nico_kuechler/androidannotations-mvp)

# AndroidAnnotations MVP Plugin

Plugin provides annotations for Model-View-Presenter (MVP) pattern.
This plugin follows the approach that [activities/fragments are the presenters](http://www.techyourchance.com/activities-android/).

## Installation

Add AndroidAnnotations and MVP plugin to your android module.

```
repositories {
    maven {
        url "PLUGIN NOT YET RELEASED"
    }
}

def androidannotations = 4.2.0
def androidannotations-mvp = 1.0.0 

deoendencies {
    apt "org.androidannotations:androidannotations:$androidannotations"
    compile "org.androidannotations:androidannotations:$androidannotations"
    
    apt "de.nenick:androidannotations-mvp:$androidannotations-mvp"
    compile "de.nenick:androidannotations-mvp-api:$androidannotations-mvp"
}
```

## Usage

### Enhance components

Base annotations to enable MVP class injections.

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

[How thing could be done.](docs/BestPractice.md)

## Support

For any issue or question please [open an issue](https://github.com/nenick/androidannotations-mvp/issues/new) for support.

## Contributing

Please contribute using [Github Flow](https://guides.github.com/introduction/flow/). 
Create a branch, add commits, and [open a pull request](https://github.com/nenick/androidannotations-mvp/compare/).

### Build Instructions

Test and build MVP plugin at command line

* from project root execute `./gradlew check`
* from project root execute `./gradlew connectedCheck` (needs android device or emulator)

Release MVP plugin

* update RELEASE_PLAN.md with new release information
* from project root execute  `./gradlew release -Prelease.version=X.X.X` and push back to github
* after release is done, update README.md with latest project version number