package assignment;

import lib.architecture.QueueProducerThread;
import lib.synchronization.QueueMonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileAnalyzer extends QueueProducerThread<Path> {


    public FileAnalyzer(QueueMonitor<Path> queue, Path path) {
        super(queue, path);
    }

    @Override
    public void produce(Path value) {
        super.produce(value);
    }

    @Override
    public void run() {
        try {
            Files.walk(this.path).filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach(f -> {
                        produce(f);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

