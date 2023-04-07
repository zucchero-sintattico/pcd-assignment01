package assignment.mvc;

import java.awt.event.ActionEvent;

public interface View {
    void update(Model model);

    void setController(Controller controller);

    void actionPerformed(ActionEvent actionCommand);
}
