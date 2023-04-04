package assignment;

import java.nio.file.Path;

public class Statistic{
    public int count;
    public Path filename;

    public Statistic(Path file, int count) {
        this.count = count;
        this.filename = file;
    }

}

