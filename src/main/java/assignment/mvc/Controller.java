package assignment.mvc;

import assignment.algorithm.AlgorithmConfiguration;

public interface Controller {
    void processEvent(String actionCommand);

    void setView(ViewImpl view);

    void startAlgorithm(AlgorithmConfiguration configuration);

    void stopAlgorithm();
}
