package lib.mvc.controller.executor;

import lib.architecture.QueueConsumerThread;
import lib.synchronization.QueueMonitor;


public class ExecutorThread extends QueueConsumerThread<Runnable> {
    public ExecutorThread(final QueueMonitor<Runnable> queueMonitor) {
        super(queueMonitor);
        super.start();
    }
    @Override
    public void consume(final Runnable task) {
        task.run();
    }
    @Override
    public void onQueueClosed() {
        System.out.println("Executor completed");
    }
}
