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
    private JLabel statusLabel;
    private JButton startButton;
    private JButton stopButton;

    public ViewImpl(Controller controller) {
        super("My View");

        this.controller = controller;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panelA = new JPanel();
        panelA.setLayout(new GridLayout(3, 2));

        JLabel label1 = new JLabel("Number of ranges:");
        JTextField textField1 = new JTextField();
        textField1.addActionListener(e -> {
            try {
                numberOfRanges = Integer.parseInt(textField1.getText());
            } catch (NumberFormatException ex) {
                // handle exception
            }
        });

        JLabel label2 = new JLabel("Max number of lines:");
        JTextField textField2 = new JTextField();
        textField2.addActionListener(e -> {
            try {
                maxNumberOfLines = Integer.parseInt(textField2.getText());
            } catch (NumberFormatException ex) {
                // handle exception
            }
        });

        JLabel label3 = new JLabel("Top N files number:");
        JTextField textField3 = new JTextField();
        textField3.addActionListener(e -> {
            try {
                topNFilesNumber = Integer.parseInt(textField3.getText());
            } catch (NumberFormatException ex) {
                // handle exception
            }
        });

        panelA.add(label1);
        panelA.add(textField1);
        panelA.add(label2);
        panelA.add(textField2);
        panelA.add(label3);
        panelA.add(textField3);


        JPanel panelB = new JPanel();
        panelB.setBackground(Color.GREEN);
        panelB.setPreferredSize(new Dimension(400, 100));

        JPanel panelC = new JPanel();
        panelC.setBackground(Color.BLUE);
        panelC.setPreferredSize(new Dimension(400, 100));

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // creo il panel B
        panelB = new JPanel();
        panelB.setLayout(new GridLayout(x, 2)); // x righe e 2 colonne
        panelB.setBorder(new TitledBorder("Panel B"));
        for (int i = 0; i < x; i++) {
            // aggiungo una coppia di JLabel e JText per ogni riga
            JLabel label = new JLabel("Label " + (i + 1));
            JTextField text = new JTextField(10);
            panelB.add(label);
            panelB.add(text);
        }

        // creo il panel C
        panelC = new JPanel();
        panelC.setLayout(new FlowLayout(FlowLayout.RIGHT)); // allineo i componenti a destra
        panelC.setBorder(new TitledBorder("Panel C"));
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
        panelC.add(statusLabel);
        panelC.add(startButton);
        panelC.add(stopButton);

        // aggiungo i panel al frame
        add(panelA, BorderLayout.NORTH);
        add(panelB, BorderLayout.CENTER);
        add(panelC, BorderLayout.SOUTH);

        pack(); // adatto la dimensione del frame ai componenti
        setVisible(true); // rendo visibile il frame


        add(panelA);
        add(panelB);
        add(panelC);

        setVisible(true);



        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(-1);
            }
        });
    }

       public void update(AlgorithmStatus status) {
            if (status == AlgorithmStatus.RUNNING) {
                statusLabel.setBackground(Color.GREEN);
            } else if (status == AlgorithmStatus.STOPPED) {
                statusLabel.setBackground(Color.RED);
            }
}

    @Override
    public void setController(Controller controller) {

    }

    @Override
    public void updateAlgorithmStatus(AlgorithmStatus status) {

    }

    @Override
    public void updateTopN(List<Statistic> stats) {

    }

    @Override
    public void updateDistribution(Map<Range, Integer> distribution) {

    }

    @Override
    public void updateNumberOfFiles(int numberOfFiles) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}



