package assignment.mvc.controller;

import assignment.algorithm.AlgorithmConfiguration;
import assignment.mvc.View;
import assignment.mvc.model.ModelConfiguration;

import java.nio.file.Path;

public interface Controller {

    void setView(View view);

    void startAlgorithm(Path path, int topN, int nOfIntervals, int maxL);

    void stopAlgorithm();

}
