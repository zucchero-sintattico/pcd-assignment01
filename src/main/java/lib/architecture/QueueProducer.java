package lib.architecture;

import lib.utils.QueueMonitor;

public abstract class QueueProducer<T> extends Thread implements Producer<T> {
    private final QueueMonitor<T> queue;
    public QueueProducer(final QueueMonitor<T> queue) {
        this.queue = queue;
    }

    @Override
    public void produce(T value) {
        this.queue.enqueue(value);
    }

    final protected void complete() {
        this.queue.close();
    }

    @Override
    abstract public void run();
}
