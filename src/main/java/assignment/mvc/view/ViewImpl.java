package assignment.mvc.view;

import assignment.Statistic;
import assignment.algorithm.AlgorithmStatus;
import assignment.mvc.controller.Controller;
import assignment.mvc.model.Range;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.Integer.parseInt;

public class ViewImpl extends JFrame implements View {

    private final JLabel numberOfFilesLabel = new JLabel("Founded files: 0");
    private final JLabel statusLabel = new JLabel("Status: Stopped");
    private final JList<String> topNList = new JList<>();
    private final JList<String> distributionList = new JList<>();
    private Controller controller;
    private Path selectedPath;

    public ViewImpl() {
        super("My View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        final JPanel preferencesPanel = new JPanel();
        preferencesPanel.setLayout(new GridLayout(4, 2));

        final JLabel filePathLabel = new JLabel("File path:");
        final JButton filePathButton = new JButton("Browse");
        filePathButton.addActionListener(e -> {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            UIManager.put("FileChooser.readOnly", Boolean.TRUE);
            fileChooser.showOpenDialog(this);
            selectedPath = fileChooser.getSelectedFile().toPath();
            filePathLabel.setText("File path: " + selectedPath);
        });

        final JLabel nOfRangesLabel = new JLabel("Number of ranges:");
        final JTextField nOfRangesText = new JTextField("5");

        final JLabel maxLinesLabel = new JLabel("Max number of lines:");
        final JTextField maxLinesText = new JTextField("100");

        final JLabel topNLabel = new JLabel("Top N files number:");
        final JTextField topNText = new JTextField("10");

        preferencesPanel.add(filePathLabel);
        preferencesPanel.add(filePathButton);
        preferencesPanel.add(nOfRangesLabel);
        preferencesPanel.add(nOfRangesText);
        preferencesPanel.add(maxLinesLabel);
        preferencesPanel.add(maxLinesText);
        preferencesPanel.add(topNLabel);
        preferencesPanel.add(topNText);

        final JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(0, 2)); // x righe e 2 colonne
        resultsPanel.setBorder(new TitledBorder("Results Panel"));
        resultsPanel.setPreferredSize(new Dimension(400, 100));
        resultsPanel.add(topNList);
        resultsPanel.add(distributionList);


        final JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(400, 100));
        statusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // allineo i componenti a destra
        statusPanel.setBorder(new TitledBorder("Status Panel"));


        // creo il riquadro status
        statusLabel.setOpaque(true); // rendo opaco il label per mostrare il colore di sfondo
        this.updateAlgorithmStatus(AlgorithmStatus.IDLE);

        // creo i bottoni start e stop
        final JButton startButton = new JButton("Start");
        final JButton stopButton = new JButton("Stop");

        final Supplier<Boolean> canStart = () -> this.selectedPath != null &&
                !maxLinesText.getText().equals("") &&
                !nOfRangesText.getText().equals("") &&
                !topNText.getText().equals("");

        // aggiungo un listener ai bottoni per cambiare il colore del riquadro status
        startButton.addActionListener(e -> {
            if (canStart.get()) {
                numberOfFilesLabel.setText("Founded files: 0");
                topNList.setListData(new String[0]);
                distributionList.setListData(new String[0]);
                controller.startAlgorithm(
                        this.selectedPath,
                        parseInt(topNText.getText()),
                        parseInt(nOfRangesText.getText()),
                        parseInt(maxLinesText.getText()));
            }
        });
        stopButton.addActionListener(e -> {
            controller.stopAlgorithm();
        });

        // aggiungo i componenti al panel C
        statusPanel.add(numberOfFilesLabel);
        statusPanel.add(statusLabel);
        statusPanel.add(startButton);
        statusPanel.add(stopButton);

        // aggiungo i panel al frame
        add(preferencesPanel, BorderLayout.NORTH);
        add(resultsPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        pack(); // adatto la dimensione del frame ai componenti
        add(preferencesPanel);
        add(resultsPanel);
        add(statusPanel);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void updateAlgorithmStatus(final AlgorithmStatus status) {
        SwingUtilities.invokeLater(() -> {
            switch (status) {
                case IDLE:
                    statusLabel.setText("Status: Idle");
                    statusLabel.setBackground(Color.LIGHT_GRAY);
                    break;
                case RUNNING:
                    statusLabel.setText("Status: Running");
                    statusLabel.setBackground(Color.GREEN);
                    break;
                case STOPPED:
                    statusLabel.setText("Status: Stopped");
                    statusLabel.setBackground(Color.RED);
                    break;
                case FINISHED:
                    statusLabel.setText("Status: Finished");
                    statusLabel.setBackground(Color.ORANGE);
                    break;
            }
        });
    }

    @Override
    public void updateTopN(List<Statistic> stats) {
        final String[] formatted = stats.stream()
                .map(x -> x.linesCount + " - " + x.file.toString().replace(this.selectedPath.toString(), "")).toArray(String[]::new);
        SwingUtilities.invokeLater(() -> {
            topNList.setListData(formatted);
        });
    }

    @Override
    public void updateDistribution(Map<Range, Integer> distribution) {
        SwingUtilities.invokeLater(() -> {
            distributionList.setListData(
                    distribution.entrySet().stream()
                            .map(x -> x.getKey().toString() + " : " + x.getValue())
                            .toArray(String[]::new)
            );
        });

    }

    @Override
    public void updateNumberOfFiles(int numberOfFiles) {
        SwingUtilities.invokeLater(() -> {
            numberOfFilesLabel.setText("Founded files: " + numberOfFiles);
        });
    }

    @Override
    public void start() {
        this.setVisible(true);
    }

}



