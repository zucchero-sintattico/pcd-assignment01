package assignment.mvc;

import assignment.Statistic;
import assignment.algorithm.AlgorithmStatus;

import javax.swing.*;
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
    public ViewImpl(Controller controller) {
        super("Assignment 1 view");

        this.controller = controller;

        setSize(200, 400);
        setResizable(true);

        JButton button1 = new JButton("Start");
        button1.addActionListener(this);

        JButton button2 = new JButton("Stop");
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
    public void actionPerformed(ActionEvent e) {
        try {
            controller.processEvent(e.getActionCommand());
        } catch (Exception ex) {
        }
    }
}
