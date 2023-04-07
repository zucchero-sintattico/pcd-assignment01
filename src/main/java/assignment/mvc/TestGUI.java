package assignment.mvc;

public class TestGUI {
    static public void main(String[] args) {

        Model model = new ModelImpl();
        Controller controller = new ControllerImpl(model);
        ViewImpl view = new ViewImpl();
        view.setController(controller);
        controller.setView(view);
        view.setVisible(true);


    }
}
