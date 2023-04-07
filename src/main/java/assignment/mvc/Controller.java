package assignment.mvc;

import assignment.algorithm.AlgorithmConfiguration;

public interface Controller {

    void setView(View view);

    void startAlgorithm();

    void stopAlgorithm();
}
