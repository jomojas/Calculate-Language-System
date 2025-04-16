package createUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.File;

public class CreateUI {
    public JTextArea codeArea, outputArea;
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
            button.addActionListener(e -> handleButtonClick(name));
        }
        frame.add(buttonPanel, BorderLayout.NORTH);

        // Text Areas
        codeArea = new JTextArea(15, 60);
        outputArea = new JTextArea(10, 60);
        outputArea.setEditable(false);
        
        // Add line numbers
        JScrollPane scrollPane = new JScrollPane(codeArea);
        TextLineNumber lineNumber = new TextLineNumber(codeArea);
        scrollPane.setRowHeaderView(lineNumber);
        
        // Monitor the change in codeArea
        codeArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { hasChanged = true; }
            public void removeUpdate(DocumentEvent e) { hasChanged = true; }
            public void changedUpdate(DocumentEvent e) { hasChanged = true; }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                scrollPane, new JScrollPane(outputArea));
        splitPane.setResizeWeight(0.7); // 70% for code, 30% for output
        frame.add(splitPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void handleButtonClick(String command) {
        switch (command) {
            case "Run":
                HandleRunFunction runFunction = new HandleRunFunction();
                runFunction.execute();
                break;
            case "New":
                HandleNewFunction newFunction = new HandleNewFunction();
                newFunction.execute();
                break;
            case "Save":
                HandleSaveFunction saveFunction = new HandleSaveFunction();
                saveFunction.execute();
                break;
            case "Open":
                HandleOpenFunction openFunction = new HandleOpenFunction();
                openFunction.execute();
                break;
            case "Save As":
                HandleSaveasFunction saveasFunction = new HandleSaveasFunction();
                saveasFunction.execute();
                break;
        }
    }
}
