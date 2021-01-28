package minichess.board;

import java.awt.*;
import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class BoardFrame extends JFrame {

    Component component;

    public BoardFrame() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Mini Chess");
        showMessageDialog(null, "The game Ends when the Leader reaches the middle point");
        this.setResizable(false);

        component = new Board();

        this.add(component, BorderLayout.CENTER);

        this.setLocation(500, 80);
        this.pack();
        this.setVisible(true);
    }
}

