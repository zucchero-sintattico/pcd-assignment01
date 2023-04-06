package assignment.mvc;

public interface Controller {
    void processEvent(String actionCommand);

    void setView(ViewImpl view);
}
