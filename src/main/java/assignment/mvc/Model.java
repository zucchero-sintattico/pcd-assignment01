package assignment.mvc;

import assignment.Statistic;
import com.sun.tools.javac.util.Pair;
import lib.architecture.Consumer;

import java.util.List;
import java.util.Map;
import java.util.Objects;

interface NumberOfFilesChangeListener extends Consumer<Integer> {}
interface TopChangeListener extends Consumer<List<Statistic>> {}
interface DistributionChangeListener extends Consumer<Map<Range, Integer>> {}

public interface Model {
    Integer getNumberOfFiles();
    List<Statistic> getTop();
    Map<Range, Integer> getDistribution();

    void addStatistic(Statistic statistic);
    void registerOnNumberOfFilesChange(NumberOfFilesChangeListener listener);
    void registerOnTopNChange(TopChangeListener listener);
    void registerOnDistributionChange(DistributionChangeListener listener);
}
