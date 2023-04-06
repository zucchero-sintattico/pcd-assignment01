package assignment.mvc;

public interface View {
    void update();

    void setController(Controller controller);

    void actionPerformed(String actionCommand);
}
