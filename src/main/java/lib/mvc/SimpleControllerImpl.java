package lib.mvc;

import lib.mvc.controller.AbstractThreadedController;

public class SimpleControllerImpl extends AbstractThreadedController<SimpleView> implements SimpleController {
    @Override
    public void doAction() {
        this.getControllerThread().execute(() -> {
            System.out.println("Executing controller on ControllerThread");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Controller finished executing on ControllerThread");
            this.getView().update();
        });
    }
}
