package lib.mvc;

public class TestGUI {
    public static void main(String[] args) {
        final SimpleController controller = new SimpleControllerImpl();
        final SimpleView view = new SimpleViewImpl();

        view.setController(controller);
        controller.setView(view);

        view.show();
    }
}
