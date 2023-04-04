package lib.synchronization;

import lib.utils.CloseableQueue;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;

public class QueueMonitor<T> extends Monitor implements CloseableQueue<T> {
    private final Queue<T> queue = new ArrayDeque<>();
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
            return open ? Optional.of(queue.remove()) : Optional.empty();
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
        return monitored(() -> open || !queue.isEmpty());
    }
}
