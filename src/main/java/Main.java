import assignment.algorithm.AlgorithmConfiguration;
import assignment.algorithm.AssignmentAlgorithm;
import assignment.mvc.model.Model;
import assignment.mvc.model.ModelConfiguration;
import assignment.mvc.model.ModelImpl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        final AlgorithmConfiguration configuration = AlgorithmConfiguration.builder()
                .withNumberOfPathProducer(1)
                .withNumberOfPathConsumer(5)
                .withNumberOfStatisticsConsumer(1)
                .build();

        final Path path = Paths.get("src/main/java/");

        final ModelConfiguration modelConfiguration = new ModelConfiguration(10, 5, 1000);
        final Model model = new ModelImpl();

        model.setConfiguration(modelConfiguration);
        final AssignmentAlgorithm algorithm = new AssignmentAlgorithm(model, path, configuration);

        algorithm.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        algorithm.stop();
        try {
            algorithm.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Done");
        System.out.println("Total number of files: " + model.getNumberOfFiles());
        System.out.println("Top stats: " + model.getTop());
        System.out.println("Distribution: " + model.getDistribution());

    }
}

