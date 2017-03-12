# Activity/Fragment as Presenter?

Most guides and blog articles follow the approach to call Activity and Fragment the View.
And suggest to create a new Presenter class. Activities and Fragments contains so much control
logic that we often need to delegate lifecycle events (onResume, ...) to the Presenter class.
For some features (start new View, ...) you need again an Activity or Fragment instance.

For detailed discussion about this Topic see also:

* http://stackoverflow.com/questions/41770844/android-mvp-and-framework-specific-code
* http://www.techyourchance.com/activities-android/
* http://www.techyourchance.com/mvp-mvc-android-1/