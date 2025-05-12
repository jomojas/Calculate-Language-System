package SemanticAnalysis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReadIntWindow {
    private JDialog dialog;
    private JTextField inputField;
    private JButton okButton;
    private Integer value = null;

    public ReadIntWindow() {
        createWindow();
    }

    private void createWindow() {
        dialog = new JDialog((Frame) null, "Enter an Integer", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null); // Center the dialog

        JLabel label = new JLabel("Please enter an integer:");
        inputField = new JTextField(15);
        okButton = new JButton("OK");

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(label);
        inputPanel.add(inputField);
        inputPanel.add(okButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(inputPanel, BorderLayout.CENTER);

        // Disable closing the window with the close (X) button
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                // Disable close operation
            }
        });

        // Handle OK button click
        okButton.addActionListener(e -> {
            String input = inputField.getText().trim();
            try {
                value = Integer.parseInt(input);
                dialog.dispose(); // close only if valid
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Invalid input. Please enter a valid integer.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                inputField.setText("");
            }
        });

        // Enter key triggers OK
        inputField.addActionListener(e -> okButton.doClick());

        dialog.setVisible(true); // block until closed
    }

    public Integer getValue() {
        return value;
    }
}
