package assignment.mvc;

import assignment.Statistic;
import lib.synchronization.QueueMonitor;

public interface Model {
    QueueMonitor<Statistic> getState();

    void addObserver(ModelObserver obs);

    void update();
}
