package assignment.mvc;

import assignment.mvc.controller.Controller;
import assignment.mvc.controller.ControllerImpl;
import assignment.mvc.model.Model;
import assignment.mvc.model.ModelConfiguration;
import assignment.mvc.model.ModelImpl;

public class TestGUI {
    static public void main(String[] args) {

        final ModelConfiguration modelConfiguration = new ModelConfiguration(10, 5, 1000);
        final Model model = new ModelImpl();
        model.setConfiguration(modelConfiguration);
        final String path = "../test-repo";

        Controller controller = new ControllerImpl(model);
        ViewImpl view = new ViewImpl();
        view.setController(controller);
        controller.setView(view);
        view.setVisible(true);
    }
}

