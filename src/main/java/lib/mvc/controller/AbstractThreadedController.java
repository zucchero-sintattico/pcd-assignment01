package lib.mvc.controller;

import lib.mvc.controller.executor.ControllerThread;
import lib.mvc.controller.executor.WithControllerThread;
import lib.mvc.view.View;

public abstract class AbstractThreadedController<V extends View> implements Controller, WithControllerThread {
    private View view;
    @Override
    public final void setView(final View view) {
        this.view = view;
    }
    @Override
    public V getView() {
        return (V) this.view;
    }

    // WithControllerThread
    private final ControllerThread controllerThread = new ControllerThread();
    @Override
    public ControllerThread getControllerThread() {
        return this.controllerThread;
    }
}
