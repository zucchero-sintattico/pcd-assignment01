package lib.synchronization;

import java.util.concurrent.locks.Condition;

public class ActionBarrier extends Monitor implements Barrier {
    private final Runnable action;
    private final Condition completed = newCondition();
    private final int n;
    private int arrived = 0;

    public ActionBarrier(final int n, final Runnable action) {
        this.n = n;
        this.action = action;
    }

    @Override
    public void hitAndWaitAll() {
        monitored(() -> {
            this.arrived++;
            if (this.arrived == this.n) {
                this.completed.notifyAll();
                this.action.run();
            } else {
                try {
                    completed.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
