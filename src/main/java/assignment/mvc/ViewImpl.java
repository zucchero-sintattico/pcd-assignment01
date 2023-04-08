package assignment.mvc;

import assignment.Statistic;
import assignment.algorithm.AlgorithmStatus;
import assignment.mvc.controller.Controller;
import assignment.mvc.model.Range;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

public class ViewImpl extends JFrame implements ActionListener, View{

    private Controller controller;
    private JTextField state;
    private int numberOfRanges = 0;
    private int maxNumberOfLines = 0;
    private int topNFilesNumber = 0;
    private int x; // il valore fornito dal panel A
    private JLabel numberOfFilesLabel;
    private JLabel statusLabel;
    private JButton startButton;
    private JButton stopButton;

    public ViewImpl() {
        super("My View");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel preferencesPanel = new JPanel();
        preferencesPanel.setLayout(new GridLayout(3, 2));

        JLabel nOfRangesLabel = new JLabel("Number of ranges:");
        JTextField nOfRangesText = new JTextField();
        nOfRangesText.addActionListener(e -> {
            try {
                numberOfRanges = Integer.parseInt(nOfRangesText.getText());
            } catch (NumberFormatException ex) {
                // handle exception
            }
        });

        JLabel maxLinesLabel = new JLabel("Max number of lines:");
        JTextField maxLinesText = new JTextField();
        maxLinesText.addActionListener(e -> {
            try {
                maxNumberOfLines = Integer.parseInt(maxLinesText.getText());
            } catch (NumberFormatException ex) {
                // handle exception
            }
        });

        JLabel topNLabel = new JLabel("Top N files number:");
        JTextField topNText = new JTextField();
        topNText.addActionListener(e -> {
            try {
                topNFilesNumber = Integer.parseInt(topNText.getText());
            } catch (NumberFormatException ex) {
                // handle exception
            }
        });

        preferencesPanel.add(nOfRangesLabel);
        preferencesPanel.add(nOfRangesText);
        preferencesPanel.add(maxLinesLabel);
        preferencesPanel.add(maxLinesText);
        preferencesPanel.add(topNLabel);
        preferencesPanel.add(topNText);


        JPanel resultsPanel = new JPanel();
        resultsPanel.setBackground(Color.GREEN);
        resultsPanel.setPreferredSize(new Dimension(400, 100));

        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.BLUE);
        statusPanel.setPreferredSize(new Dimension(400, 100));

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // creo il panel B
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(x, 2)); // x righe e 2 colonne
        resultsPanel.setBorder(new TitledBorder("Panel B"));
        for (int i = 0; i < x; i++) {
            // aggiungo una coppia di JLabel e JText per ogni riga
            JLabel label = new JLabel("Label " + (i + 1));
            JTextField text = new JTextField(10);
            resultsPanel.add(label);
            resultsPanel.add(text);
        }

        // creo il panel C
        statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // allineo i componenti a destra
        statusPanel.setBorder(new TitledBorder("Panel C"));

        numberOfFilesLabel = new JLabel("0");

        // creo il riquadro status
        statusLabel = new JLabel("Status: ");
        statusLabel.setOpaque(true); // rendo opaco il label per mostrare il colore di sfondo
        statusLabel.setBackground(Color.GREEN); // imposto il colore verde iniziale
        // creo i bottoni start e stop
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        // aggiungo un listener ai bottoni per cambiare il colore del riquadro status
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setBackground(Color.GREEN); // verde se start
                controller.startAlgorithm();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setBackground(Color.RED); // rosso se stop
                controller.stopAlgorithm();
            }
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
        setVisible(true); // rendo visibile il frame


        add(preferencesPanel);
        add(resultsPanel);
        add(statusPanel);

        setVisible(true);



        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(-1);
            }
        });
    }



    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void updateAlgorithmStatus(AlgorithmStatus status) {
        if (status == AlgorithmStatus.RUNNING) {
            statusLabel.setBackground(Color.GREEN);
        } else if (status == AlgorithmStatus.STOPPED) {
            statusLabel.setBackground(Color.RED);
        }
    }

    @Override
    public void updateTopN(List<Statistic> stats) {
        SwingUtilities.invokeLater(() -> {

        });
    }

    @Override
    public void updateDistribution(Map<Range, Integer> distribution) {

    }

    @Override
    public void updateNumberOfFiles(int numberOfFiles) {
        SwingUtilities.invokeLater(() -> {
            numberOfFilesLabel.setText(String.valueOf(numberOfFiles));
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}



