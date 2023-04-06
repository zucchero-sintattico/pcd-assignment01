package assignment.agents;

import assignment.logger.Logger;
import assignment.logger.LoggerMonitor;
import lib.architecture.QueueProducerThread;
import lib.synchronization.QueueMonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Scan the given path and enqueue all the java files found.
 * Once the scan is finished, close the queue.
 */
public class PathProducer extends QueueProducerThread<Path> {

    private final Logger logger = LoggerMonitor.getInstance();
    private final Path path;

    public PathProducer(QueueMonitor<Path> queue, Path path) {
        super(queue);
        this.path = path;
    }

    @Override
    public void run() {
        this.logger.log("Starting File Analyzer");
        try (Stream<Path> pathStream = Files.walk(this.path)) {
            pathStream.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach((f) -> {
                        this.produce(f);
                        this.logger.log("Produced " + f);
                    });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            this.closeQueue();
            LoggerMonitor.getInstance().log("Finished Reading File");
        }
    }
}
