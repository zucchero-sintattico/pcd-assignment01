package mvc;

public class ModelImpl implements Model {
    private ModelObserver observer;

    public ModelImpl() {
    }

    public void setObserver(ModelObserver observer) {
        this.observer = observer;
    }

    @Override
    public String getState() {
        return "";
    }
}

