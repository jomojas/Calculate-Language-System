package createUI;

import java.io.*;

import javax.swing.*;

import main.Main;

public class HandleOpenFunction {
	private CreateUI ui;
	
	public HandleOpenFunction() {
		this.ui = Main.ui;
	}
	
	public void execute() {
		 // Check if codeArea has content
        if (!ui.codeArea.getText().trim().isEmpty()) {
            int choice = JOptionPane.showConfirmDialog(
                null,
                "Do you want to save the current file before opening a new one?",
                "Save File",
                JOptionPane.YES_NO_CANCEL_OPTION
            );

            if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                return; // Cancelled, do nothing
            } else if (choice == JOptionPane.YES_OPTION) {
                // Call your save function
                new HandleSaveasFunction().execute();
            }
            // If NO: continue to open a new file
        }
        
        // Display the content of file on codeArea
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open File");

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                // Read file content
                StringBuilder content = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                }

                // Update codeArea and currentFile
                ui.codeArea.setText(content.toString());
                ui.currentFile = selectedFile;

            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Error opening file: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
	}
}
