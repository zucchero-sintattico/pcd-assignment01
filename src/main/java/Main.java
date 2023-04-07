import assignment.algorithm.AlgorithmConfiguration;
import assignment.algorithm.AssignmentAlgorithm;
import assignment.mvc.Model;
import assignment.mvc.ModelConfiguration;
import assignment.mvc.ModelImpl;

public class Main {
    public static void main(String[] args) {

        final AlgorithmConfiguration configuration = AlgorithmConfiguration.builder()
                .withNumberOfPathProducer(1)
                .withNumberOfPathConsumer(5)
                .withNumberOfStatisticsConsumer(1)
                .build();

        final String path = "src/main/java/";

        final ModelConfiguration modelConfiguration = new ModelConfiguration(10, 5, 1000);
        final Model model = new ModelImpl(modelConfiguration);
        final AssignmentAlgorithm algorithm = new AssignmentAlgorithm(model, path, configuration);

        algorithm.start();
        try {
            algorithm.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

