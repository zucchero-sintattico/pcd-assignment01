package assignment.mvc;

import assignment.logger.LoggerMonitor;
import assignment.Statistic;
import lib.synchronization.QueueMonitor;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
    private List<ModelObserver> observers;
    private QueueMonitor<Statistic> queueMonitor;

    public ModelImpl() {
        this.observers = new ArrayList<>();
        this.queueMonitor = new QueueMonitor<>();
    }

    @Override
    public QueueMonitor<Statistic> getState() {
        return this.queueMonitor;
    }

    @Override
    public void addObserver(ModelObserver obs) {
        this.observers.add(obs);
    }

    @Override
    public void update() {
        LoggerMonitor.getInstance().log("Model.update");
        for (ModelObserver obs : this.observers) {
            obs.modelUpdated(this);
        }
    }
}

