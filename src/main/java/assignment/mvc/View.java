package assignment.mvc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class View extends JFrame implements ActionListener, ModelObserver {

    private Controller controller;
    private JTextField state;

    public View(Controller controller) {
        super("My View");

        this.controller = controller;

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

    @Override
    public void modelUpdated(Model model) {
        try {
            System.out.println("[View] model updated => updating the view");
            SwingUtilities.invokeLater(() -> {
                // update the view using model state
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

