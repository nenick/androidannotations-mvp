# AndroidAnnotations MVP Plugin

Plugin provides annotations for Model-View-Presenter (MVP) pattern.

## Annotations

Annotation | Description
---|---
EMvpPresenter | Enhance an Activity or Fragment to be a Presenter.
EMvpView | Enhance an Bean class to be a View.
MvpView | Injects corresponding View instance.
MvpViewCallback | Injects corresponding Presenter instance.
MvpFragment | Injects Fragment Presenter instance.

#### No Model Annotation

Current there is no idea how a Model annotation could help.
  Please tell if you have any suggestions.

## Activity/Fragment is Presenter

Most guides and blog articles follow the approach to call Activity and Fragment the View.
And suggest to create a new Presenter class. Activities and Fragments contains so much control
logic that we often need to delegate lifecycle events (onResume, ...) to the Presenter class.
For some features (start new View, ...) you need again an Activity or Fragment instance.

For detailed discussion about this Topic see also:

* http://stackoverflow.com/questions/41770844/android-mvp-and-framework-specific-code
* http://www.techyourchance.com/activities-android/
* http://www.techyourchance.com/mvp-mvc-android-1/

## How? Why? Help!

Some suggestions for questions asked by others.
If you miss any question then feel free to open a issue ticket.

Questions | Suggestion | Related
---|---|---
Who should manage Fragments? | **Presenters should hold each other, view should show them.** Its common that presenters need to exchange information so they need direct contact. Presenter could say where but only the View know how the Fragment View will be added.
Navigate to other views? | **Presenters are able to start another View.** Equal whether  Navigating to another View often has some logic (e.g. User must confirm to leave) which should always be handled by Presenters.

## Contribute

After you clone the project ..
You need a device or emulator for instrumentation tests

### Command Line

Run inside project root `./gradlew build` to check that all is working.

* mvp-plugin-api
    * build plugin api
* mvp-plugin
    * build plugin
* mvp-plugin-test
    * build module with plugin
    * run lint checks
    * run unit tests
    * run instrumentation tests
