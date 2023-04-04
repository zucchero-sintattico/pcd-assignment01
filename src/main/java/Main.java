import gov.nasa.jpf.vm.Verify;
import lib.synchronization.ActionBarrier;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        final ActionBarrier barrier = new ActionBarrier(2, () -> {
            System.out.println("Barrier completed");
        });
        final Set<Thread> threads = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            threads.add(new Thread(() -> {
                System.out.println("Hitting barrier");
                barrier.hitAndWaitAll();
            }));
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

