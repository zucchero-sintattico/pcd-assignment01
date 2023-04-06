import assignment.AlgorithmConfiguration;
import assignment.AssignmentAlgorithm;

public class Main {
    public static void main(String[] args) {

        final AlgorithmConfiguration configuration = AlgorithmConfiguration.builder()
                .withNumberOfPathProducer(1)
                .withNumberOfPathConsumer(5)
                .withNumberOfStatisticsConsumer(1)
                .build();

        final String path = "src/main/java/";
        final AssignmentAlgorithm algorithm = new AssignmentAlgorithm(path, configuration);

        algorithm.start();
        try {
            algorithm.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

