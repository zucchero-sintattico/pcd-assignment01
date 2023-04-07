package lib.mvc.controller.executor;

import lib.synchronization.QueueMonitor;

public class ControllerThread {
    private final QueueMonitor<Runnable> executorQueue;
    private final ExecutorThread executor;
    public ControllerThread() {
        this.executorQueue = new QueueMonitor<>();
        this.executor = new ExecutorThread(this.executorQueue);
    }
    public void execute(final Runnable task) {
        executorQueue.enqueue(task);
    }
    public void start() {
        this.executor.start();
    }
}

/*
* EDT --invoke--> View
* View --invoke--> Controller
* Controller --delegate--> CT
* CT --invoke--> Model
* CT --invoke--> View.update
* */