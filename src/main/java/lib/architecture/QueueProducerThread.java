package lib.architecture;

import lib.synchronization.QueueMonitor;

import java.nio.file.Path;

public abstract class QueueProducerThread<T> extends Thread implements Producer<T> {
    private final QueueMonitor<T> queue;
    protected final Path path;

    public QueueProducerThread(final QueueMonitor<T> queue, final Path path) {
        this.queue = queue;
        this.path = path;
    }

    @Override
    public void produce(T value) {
        this.queue.enqueue(value);
    }

    final protected void closeQueue() {
        this.queue.close();
    }

    @Override
    abstract public void run();
}
