package lib.synchronization;

import lib.utils.CloseableQueue;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;

public class QueueMonitor<T> extends Monitor implements CloseableQueue<T> {
    private final Queue<T> queue = new ConcurrentLinkedQueue<>();
    private final Condition notEmpty = newCondition();
    private boolean open = true;

    @Override
    public void enqueue(final T value) {
        monitored(() -> {
            queue.add(value);
            notEmpty.signal();
        });
    }

    @Override
    public Optional<T> dequeue() {
        return monitored(() -> {
            while (queue.isEmpty() && open) {
                try {
                    notEmpty.await();
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return this.isOpen() ? Optional.of(queue.remove()) : Optional.empty();
        });
    }

    @Override
    public void close() {
        monitored(() -> {
            open = false;
            notEmpty.signalAll();
        });
    }

    @Override
    public boolean isOpen() {
        return monitored(() -> open || !this.queue.isEmpty());
    }
}
