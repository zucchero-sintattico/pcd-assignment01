package lib.mvc.view;

import lib.mvc.controller.Controller;

public abstract class AbstractView<C extends Controller> implements View {
    private Controller controller;
    @Override
    public final void setController(final Controller controller) {
        this.controller = controller;
    }
    @Override
    public C getController() {
        return (C) this.controller;
    }
}
