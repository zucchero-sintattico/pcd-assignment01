package lib.mvc;

import lib.mvc.view.AbstractView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimpleViewImpl extends AbstractView<SimpleController> implements SimpleView {

    private final JFrame view = new JFrame();
    private final JTextField state = new JTextField(10);

    private int counterUpdate = 0;
    public SimpleViewImpl() {
        view.setTitle("Simple View");
        view.setSize(400, 400);
        view.setResizable(false);

        final JButton button1 = new JButton("Button 1");
        button1.addActionListener(e -> {
            getController().doAction();
        });

        state.setText("Update: " + counterUpdate);
        final JPanel panel = new JPanel();
        panel.add(button1);
        panel.add(state);
        view.setLayout(new BorderLayout());
        view.add(panel, BorderLayout.NORTH);

        view.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(-1);
            }
        });
    }
    @Override
    public void update() {
        counterUpdate++;
        state.setText("Update: " + counterUpdate);
    }

    @Override
    public void show() {
        this.view.setVisible(true);
    }
}
