package assignment.mvc;

import assignment.algorithm.AlgorithmConfiguration;
import assignment.algorithm.AlgorithmStatus;
import assignment.algorithm.AssignmentAlgorithm;
import assignment.logger.Logger;
import assignment.logger.LoggerMonitor;
import assignment.mvc.model.Model;

import java.nio.file.Path;

public class ControllerImpl implements Controller {

    public static final int NUMBER_OF_PATH_PRODUCER = 1;
    public static final int NUMBER_OF_PATH_CONSUMER = 5;
    public static final int NUMBER_OF_STATISTICS_CONSUMER = 1;
    private Logger logger = LoggerMonitor.getInstance();
    private Path path;
    private Model model;
    private final AlgorithmConfiguration config;
    private AssignmentAlgorithm algorithm;
    private AlgorithmStatus status;
    private View view;


    public ControllerImpl(Model model, Path path) {
        this.model = model;
        this.model.registerOnTopNChange((topN) -> this.view.updateTopN(topN));
        this.model.registerOnDistributionChange((distribution) -> this.view.updateDistribution(distribution));
        this.model.registerOnNumberOfFilesChange((numberOfFiles) -> this.view.updateNumberOfFiles(numberOfFiles));

        this.config = AlgorithmConfiguration.builder()
                .withNumberOfPathProducer(NUMBER_OF_PATH_PRODUCER)
                .withNumberOfPathConsumer(NUMBER_OF_PATH_CONSUMER)
                .withNumberOfStatisticsConsumer(NUMBER_OF_STATISTICS_CONSUMER)
                .build();
        this.path = path;
    }
    @Override
    public void processEvent(String actionCommand) {
        switch (actionCommand) {
            case "start":
                startAlgorithm(this.config);
                this.status = AlgorithmStatus.RUNNING;
                this.view.updateAlgorithmStatus(this.status);
                break;
            case "stop":
                stopAlgorithm();
                this.status = AlgorithmStatus.STOPPED;
                this.view.updateAlgorithmStatus(this.status);
                break;
            default:
                this.logger.log("Unknown action command: " + actionCommand);
        }
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void startAlgorithm(AlgorithmConfiguration configuration) {
        this.algorithm = new AssignmentAlgorithm(this.model, this.path, configuration);
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
