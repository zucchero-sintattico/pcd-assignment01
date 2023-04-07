package assignment.mvc.controller;

import assignment.algorithm.AlgorithmConfiguration;
import assignment.mvc.View;

public interface Controller {

    void setView(View view);

    void startAlgorithm();

    void stopAlgorithm();
}
