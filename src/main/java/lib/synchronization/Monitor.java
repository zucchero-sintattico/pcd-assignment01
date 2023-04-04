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
        mutex.lock();
        f.run();
        mutex.unlock();
    }

    protected final <A> A monitored(final Supplier<A> f) {
        mutex.lock();
        final A res = f.get();
        mutex.unlock();
        return res;
    }
}