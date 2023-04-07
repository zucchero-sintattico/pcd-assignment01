package assignment.mvc;

import assignment.Statistic;
import assignment.mvc.listeners.DistributionChangeListener;
import assignment.mvc.listeners.NumberOfFilesChangeListener;
import assignment.mvc.listeners.TopChangeListener;

import java.util.List;
import java.util.Map;

public interface Model {
    Integer getNumberOfFiles();
    List<Statistic> getTop();
    Map<Range, Integer> getDistribution();

    void addStatistic(Statistic statistic);
    void registerOnNumberOfFilesChange(NumberOfFilesChangeListener listener);
    void registerOnTopNChange(TopChangeListener listener);
    void registerOnDistributionChange(DistributionChangeListener listener);
}
