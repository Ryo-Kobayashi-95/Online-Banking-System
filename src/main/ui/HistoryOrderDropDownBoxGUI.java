package ui;

import javax.swing.*;
import java.awt.*;

public class HistoryOrderDropDownBoxGUI extends JFrame {
    public HistoryOrderDropDownBoxGUI() {
        // Create an array of items for the JComboBox
        String[] items = {"Item 1", "Item 2", "Item 3"};

        // Create the JComboBox with the items array
        JComboBox<String> comboBox = new JComboBox<>(items);

        // Set the preferred size of the JComboBox
        comboBox.setPreferredSize(new Dimension(200, 30));

        // Add the JComboBox to the JFrame
        add(comboBox);

        // Set the JFrame properties
        setTitle("My Frame");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
