# Manage fragments

We differentiate between two different approaches for fragments.

#### Static fragments 

This kind of fragments which are already added by your layout files.
AndroidAnnotations already provide [annotations to inject static fragments](TODO url here).

    @FragmentById(R.id.myFragment)
    MyFragment myFragment;

#### Dynamic fragments 

This fragments are added and replaced dynamically at runtime.
Common this are build through static methods `new MyFragment_.IntentBuilder_(context)`.

## Who should manage fragments?

**Presenters should manage his fragments, view should show them.**
 
Fragments are presenters and it's common that presenters need to exchange information. 
If you don't use any bus system (e.g EventBus) then parent and child presenters should directly call another.
But child presenters should not call directly another child presenter, always take the ways through parent presenter.
Presenter could say where but only the View know how the fragment view will be added.

## Example

#### Presenter

    @EMvpPresenter
    @EActivity(R.layout.activity_my)
    public class MyActivity extends AppCompatActivity implements MyActivityView.Callback {
    
        @MvpView
        MyActivityView myView;
    
        @MvpFragment
        MyFragment myFragment;
    
        @AfterViews
        void initView() {
            myView.showContainerFragment(myFragment);
        }
    
        @Override
        protected void onResume() {
            super.onResume();
            myFragment.addMessage("It's connected!");
        }
    }

#### View

    @EBean
    @EMvpView
    class MyActivityView {
    
        interface Callback {
            FragmentManager getSupportFragmentManager();
        }
    
        @MvpCallback
        Callback callback;
    
        @ViewById(R.id.container)
        FrameLayout container;
    
        void showContainerFragment(Fragment fragment) {
            callback.getSupportFragmentManager().beginTransaction().add(container.getId(), fragment, null).commit();
            callback.getSupportFragmentManager().executePendingTransactions();
        }
    }
