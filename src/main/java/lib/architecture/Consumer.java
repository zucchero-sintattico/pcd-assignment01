package lib.architecture;

import java.io.IOException;

public interface Consumer<T> {
    void consume(final T value);
}
