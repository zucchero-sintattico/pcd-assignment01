package assignment.mvc;

public class ControllerImpl implements Controller, ModelObserver {

    private Model model;
    private ViewImpl view;

    public ControllerImpl(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void processEvent(String actionCommand) {
        new Thread(() -> {
            try {
                System.out.println("[Controller] Processing the event " + actionCommand + " ...");
                Thread.sleep(1000);
                model.update();
                System.out.println("[Controller] Processing the event done.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    @Override
    public void setView(ViewImpl view) {
        this.view = view;
    }

    @Override
    public void modelUpdated(Model model) {
        try {
            System.out.println("[Controller] model updated => updating the view");
            this.view.update(model);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
