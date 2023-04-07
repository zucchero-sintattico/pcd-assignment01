package assignment.mvc.listeners;

import assignment.mvc.Range;
import lib.architecture.Consumer;

import java.util.Map;

public interface DistributionChangeListener extends Consumer<Map<Range, Integer>> {
}
