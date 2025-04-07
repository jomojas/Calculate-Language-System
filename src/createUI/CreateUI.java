package createUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class CreateUI {
    private JTextArea codeArea, outputArea;
    public File currentFile;
    public boolean hasChanged = false;	// Checking any change on currentFile

    public CreateUI() {
        JFrame frame = new JFrame("Calculate Language System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Panel with Buttons
        JPanel buttonPanel = new JPanel();
        String[] buttonNames = {"New", "Save", "Open", "Run", "Save As"};
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            buttonPanel.add(button);
            button.addActionListener(e -> handleButtonClick(name));  // Using method reference
        }
        frame.add(buttonPanel, BorderLayout.NORTH);

        // Text Areas
        codeArea = new JTextArea(15, 60);
        outputArea = new JTextArea(10, 60);
        outputArea.setEditable(false);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(codeArea), new JScrollPane(outputArea));
        splitPane.setResizeWeight(0.7); // 70% for code, 30% for output
        frame.add(splitPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void handleButtonClick(String command) {
        switch (command) {
            case "Run":
                outputArea.setText("Executing...\n" + codeArea.getText());
                break;
            case "New":
                HandleNewFunction newFunction = new HandleNewFunction(codeArea, outputArea);
                newFunction.execute(currentFile, hasChanged);
                break;
            case "Save":
                JOptionPane.showMessageDialog(null, "Save function not implemented yet!");
                break;
            case "Open":
                JOptionPane.showMessageDialog(null, "Open function not implemented yet!");
                break;
            case "Save As":
                JOptionPane.showMessageDialog(null, "Save As function not implemented yet!");
                break;
        }
    }
}
