package assignment.mvc;

import assignment.Statistic;
import lib.architecture.Consumer;

import java.util.List;

public interface Model {
    List<Statistic> getState();

    void addStatistic(Statistic statistic);

    void registerOnTopNChange(Consumer<List<Statistic>> consumer);

    void registerOnDistributionChange(Consumer<List<Statistic>> consumer);

}
