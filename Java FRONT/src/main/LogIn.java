package main;

import javax.swing.*;
import java.awt.*;

public class LogIn {
    private JFrame frame;
    private JPanel panel;
    private JLabel nameLabel = new JLabel("Name:");
    private JTextField nameTextField = new JTextField();
    private JButton createButton = new JButton("Create new game");

    public LogIn(int width, int height){
        frame = new JFrame("LogIn Screen");
        panel = new JPanel();

        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        panel.add(nameLabel);
        nameTextField.setSize(20,20);
        panel.add(nameTextField);
        panel.add(createButton);
        frame.add(panel);
    }
}
