package assignment.mvc;

import assignment.Statistic;
import com.sun.tools.javac.util.Pair;
import lib.architecture.Consumer;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface Model {
    Integer getNumberOfFiles();
    List<Statistic> getTop();
    Map<Range, Integer> getDistribution();

    void addStatistic(Statistic statistic);
    void registerOnTopNChange(Consumer<List<Statistic>> consumer);
    void registerOnDistributionChange(Consumer<List<Statistic>> consumer);

}
