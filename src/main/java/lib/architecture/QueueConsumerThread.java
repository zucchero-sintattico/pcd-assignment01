package lib.architecture;

import lib.synchronization.QueueMonitor;

/**
 * Thread that consumes values from a queue monitor.
 * @param <T> Type of values to consume.
 * @see QueueMonitor
 */
public abstract class QueueConsumerThread<T> extends Thread implements QueueConsumer<T> {
    private final QueueMonitor<T> queueMonitor;

    public QueueConsumerThread(final QueueMonitor<T> queueMonitor) {
        this.queueMonitor = queueMonitor;
    }

    @Override
    public final void run() {
        while (this.queueMonitor.isOpen()) {
            this.queueMonitor.dequeue().ifPresent(this::consume);
        }
        this.onQueueClosed();
    }
}
