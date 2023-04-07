package lib.mvc.view;

import lib.mvc.controller.Controller;

public interface View {
    void setController(final Controller controller);
    <C extends Controller> C getController();

    void show();
}
