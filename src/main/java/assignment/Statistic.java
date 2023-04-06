package assignment;

import java.nio.file.Path;

/**
 * Class for storing information about file and number of lines in it.
 */
public final class Statistic{
    public final Path file;
    public final int linesCount;

    public Statistic(final Path file, final int linesCount) {
        this.linesCount = linesCount;
        this.file = file;
    }
}

