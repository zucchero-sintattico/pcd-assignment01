package lib.architecture;

import assignment.Statistic;
import lib.synchronization.QueueMonitor;

import javax.swing.text.html.Option;
import java.util.Optional;

public abstract class QueueConsumerThread<T> extends Thread implements QueueConsumer<T> {
    private final QueueMonitor<T> queueMonitor;

    public QueueConsumerThread(final QueueMonitor<T> queueMonitor) {
        this.queueMonitor = queueMonitor;
    }

    @Override
    public final void run() {
        while (queueMonitor.isOpen()) {
            queueMonitor.dequeue().ifPresent(this::consume);
        }
        onQueueClosed();
    }
}
