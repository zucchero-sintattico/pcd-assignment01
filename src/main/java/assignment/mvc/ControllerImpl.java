package assignment.mvc;

import assignment.algorithm.AlgorithmConfiguration;
import assignment.algorithm.AlgorithmConfigurationBuilder;
import assignment.algorithm.AssignmentAlgorithm;
import assignment.logger.Logger;
import assignment.logger.LoggerMonitor;

import java.nio.file.Path;

public class ControllerImpl implements Controller{

    private Logger logger = LoggerMonitor.getInstance();
    private Path path;
    private Model model;
    private final AlgorithmConfiguration config;

    private AssignmentAlgorithm algorithm;
    public ControllerImpl(Model model, Path path) {
        this.model = model;
        this.config = AlgorithmConfiguration.builder()
                .withNumberOfPathProducer(1)
                .withNumberOfPathConsumer(5)
                .withNumberOfStatisticsConsumer(1)
                .build();
        this.path = path;
    }
    @Override
    public void processEvent(String actionCommand) {
        switch (actionCommand) {
            case "start":
                startAlgorithm(this.config);
                break;
            case "stop":
                stopAlgorithm();
                break;
            default:
                this.logger.log("Unknown action command: " + actionCommand);
        }
    }

    @Override
    public void setView(View view) {

    }

    @Override
    public void startAlgorithm(AlgorithmConfiguration configuration) {
        this.algorithm = new AssignmentAlgorithm(null, this.path, configuration);
    }

    @Override
    public void stopAlgorithm() {
        try {
            this.algorithm.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
