package assignment;

import lib.architecture.QueueProducerThread;
import lib.synchronization.QueueMonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class PathProducer extends QueueProducerThread<Path> {

    private final Path path;

    public PathProducer(QueueMonitor<Path> queue, Path path) {
        super(queue);
        this.path = path;
    }

    @Override
    public void run() {
        Logger.getInstance().log("Starting File Analyzer");
        try (Stream<Path> pathStream = Files.walk(this.path)) {
            pathStream.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach((f) -> {
                        this.produce(f);
                        Logger.getInstance().log("Produced " + f);
                    });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            this.closeQueue();
            Logger.getInstance().log("Finished Reading File");
        }
    }
}

