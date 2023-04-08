package assignment.mvc;

import assignment.mvc.controller.Controller;
import assignment.mvc.controller.ControllerImpl;
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

////////////////////////////////////////////////////7
/*
package pcd.lab04.gui4_mvc_nodeadlock;

public class TestGUI {
    static public void main(String[] args){

        MyModel model = new MyModel();
        MyController controller = new MyController(model);
        MyView view = new MyView(controller);
        model.addObserver(view);
        view.setVisible(true);
        //view resizing
        view.setSize(400, 400);
        view.setResizable(true);


        new MyAgent(model).start();

    }

}
*/