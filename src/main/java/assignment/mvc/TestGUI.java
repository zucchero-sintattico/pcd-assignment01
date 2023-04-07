package assignment.mvc;

import assignment.mvc.model.Model;
import assignment.mvc.model.ModelConfiguration;
import assignment.mvc.model.ModelImpl;

import java.nio.file.Paths;

public class TestGUI {
    static public void main(String[] args) {

        final ModelConfiguration modelConfiguration = new ModelConfiguration(10, 5, 1000);
        final Model model = new ModelImpl();
        model.setConfiguration(modelConfiguration);
        final String path = "src/main/java/";

        Controller controller = new ControllerImpl(model, Paths.get(path));
        ViewImpl view = new ViewImpl(controller);
        view.setController(controller);
        controller.setView(view);
        view.setVisible(true);


    }
}
