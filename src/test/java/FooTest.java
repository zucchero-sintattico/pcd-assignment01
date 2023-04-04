import lib.synchronization.ActionBarrier;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FooTest {
    @Test
    public void testActionBarrier() {
        final ActionBarrier barrier = new ActionBarrier(2, () -> {
            assertTrue(true);
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
