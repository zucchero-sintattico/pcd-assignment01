package assignment.mvc;

import assignment.logger.LoggerMonitor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ViewImpl extends JFrame implements ActionListener, View {

    private Controller controller;
    private JTextField state;

    public ViewImpl() {
        super("My View");


        setSize(400, 60);
        setResizable(false);

        JButton button1 = new JButton("ButtonText");
        button1.addActionListener(this);

        JButton button2 = new JButton("Event #2");
        button2.addActionListener(this);

        state = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(button1);
        panel.add(button2);
        panel.add(state);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(-1);
            }
        });
    }

    public void actionPerformed(ActionEvent ev) {
        controller.processEvent(ev.getActionCommand());
    }

    public void update(Model model) {
        LoggerMonitor.getInstance().log("View.update");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}

