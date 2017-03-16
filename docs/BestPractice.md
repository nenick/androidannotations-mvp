

## Best Practices with this MVP Plugin

Some suggestions for questions asked by others.
If you miss any question then feel free to open a issue ticket.


### Who should manage Fragments?
**Presenters should manage his fragments, view should show them.** Its common that presenters need to exchange information so they need direct contact. Presenter could say where but only the View know how the Fragment View will be added.

### Navigate to other views?
**Presenters are able to start another View.** Equal whether  Navigating to another View often has some logic (e.g. User must confirm to leave) which should always be handled by Presenters.

