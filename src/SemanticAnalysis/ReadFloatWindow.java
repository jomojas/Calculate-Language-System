package SemanticAnalysis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReadFloatWindow {
    private JDialog dialog;
    private JTextField inputField;
    private JButton okButton;
    private Double value = null;

    public ReadFloatWindow() {
        createWindow();
    }

    private void createWindow() {
        dialog = new JDialog((Frame) null, "Enter a Float Number", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null); // Center the dialog

        JLabel label = new JLabel("Please enter a Float number:");
        inputField = new JTextField(15);
        okButton = new JButton("OK");

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(label);
        inputPanel.add(inputField);
        inputPanel.add(okButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(inputPanel, BorderLayout.CENTER);

        // Disable window closing with X button
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                // Do nothing (disables close button)
            }
        });

        // Handle OK button click
        okButton.addActionListener(e -> {
            String input = inputField.getText().trim();
            try {
                value = Double.parseDouble(input);
                dialog.dispose(); // close dialog only on valid input
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Invalid input. Please enter a valid number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                inputField.setText("");
            }
        });

        // Pressing Enter also triggers OK
        inputField.addActionListener(e -> okButton.doClick());

        dialog.setVisible(true); // blocks until dialog is closed
    }

    public Double getValue() {
        return value;
    }
}

