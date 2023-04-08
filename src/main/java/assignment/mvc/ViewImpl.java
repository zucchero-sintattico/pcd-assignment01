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

    private AlgorithmStatus status;
    private List<Statistic> topN;
    private Map<Range, Integer> distribution;

    private int numberOfFiles;

    public ViewImpl(Controller controller) {
        super("Assignment 1 view");

        this.controller = controller;

        setSize(200, 400);
        setResizable(true);

        JButton button1 = new JButton("start");
        button1.addActionListener(this);

        JButton button2 = new JButton("stop");
        button2.addActionListener(this);

        JTextField NumberOfRanges = new JTextField(10);
        JTextField maxRange = new JTextField(10);


        state = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(button1);
        panel.add(button2);
        panel.add(NumberOfRanges);
        panel.add(maxRange);
        panel.add(state);
        //text under the button
        panel.add(new JLabel("Number of files: " + numberOfFiles));

        setLayout(new BorderLayout());
        add(panel,BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(-1);
            }
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void updateAlgorithmStatus(AlgorithmStatus status) {
        this.status = status;
    }

    @Override
    public void updateTopN(List<Statistic> stats) {
        this.topN = stats;
    }

    @Override
    public void updateDistribution(Map<Range, Integer> distribution) {
        this.distribution = distribution;
    }

    @Override
    public void updateNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            System.out.println("Action performed: " + e.getActionCommand());
            if (e.getActionCommand().equals("start")) {
                controller.startAlgorithm();
            } else if (e.getActionCommand().equals("stop")){
                controller.stopAlgorithm();
            }

        } catch (Exception ex) {
        }
    }
}


///////////////////////////////////////////////////////////////////////////////////////////
/*
package pcd.lab04.gui4_mvc_nodeadlock;

        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.WindowAdapter;
        import java.awt.event.WindowEvent;

        import javax.swing.*;
        import javax.swing.border.TitledBorder;

class MyView extends JFrame implements ActionListener, ModelObserver {

    private MyController controller;
    private JTextField state;
    private int numberOfRanges = 0;
    private int maxNumberOfLines = 0;
    private int topNFilesNumber = 0;
    private int x; // il valore fornito dal panel A
    private JLabel statusLabel;
    private JButton startButton;
    private JButton stopButton;

    public MyView(MyController controller) {
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
            }
        });
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setBackground(Color.RED); // rosso se stop
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

    public void actionPerformed(ActionEvent ev) {
        try {
            controller.processEvent(ev.getActionCommand());
        } catch (Exception ex) {
        }
    }

    @Override
    public void modelUpdated(MyModel model) {
        try {
            System.out.println("[View] model updated => updating the view");
            SwingUtilities.invokeLater(() -> {
                state.setText("state: " + model.getState());
            });
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
*/


