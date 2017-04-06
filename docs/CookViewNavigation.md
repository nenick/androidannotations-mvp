# View Navigation


### Navigate to other views?

**Presenters knows when to start another view.** 

When navigating to another views often it includes some logic (e.g. User must confirm to leave) which should always be handled by presenters.

## Example

#### Presenter starts another presenter

    @EMvpPresenter
    @EActivity(R.layout.activity_my)
    public class MyActivity extends AppCompatActivity implements MyActivityView.Callback {
    
        @MvpView
        MyActivityView myView;
    
        @MvpActivity
        MyOtherActivity otherActivity;
    
        @Override
        public void onEvent() {
            otherActivitiy.intent(this).start()
        }
    }

#### Presenter switch fragments
    
    @EMvpPresenter
    @EActivity(R.layout.activity_my)
    public class MyActivity extends AppCompatActivity implements MyActivityView.Callback {
    
        @MvpView
        MyActivityView myView;
    
        @MvpFragment
        InitialFragment intialFragment;
        
        @MvpFragment
        OtherFragment otherFragment;
    
        @AfterViews
        void initView() {
            myView.showContainerFragment(intialFragment);
        }
    
        @Override
        public void onEvent() {
            myView.showContainerFragment(otherFragment);
        }
    }
