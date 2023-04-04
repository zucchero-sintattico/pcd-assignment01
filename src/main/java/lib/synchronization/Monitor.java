package lib.synchronization;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public abstract class Monitor {
    private final Lock mutex = new ReentrantLock();
    protected final Condition newCondition() {
        return mutex.newCondition();
    }

    protected final void monitored(final Runnable f) {
        this.monitored(() -> {
            f.run();
            return null;
        });
    }

    protected final <A> A monitored(final Supplier<A> f) {
        mutex.lock();
        try {
            return f.get();
        } finally {
            mutex.unlock();
        }
    }
}