package createUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class HandleNewFunction {
  	private JTextArea codeArea;
    private JTextArea outputArea;

    // Constructor to initialize the JTextArea objects
    public HandleNewFunction(JTextArea codeArea, JTextArea outputArea) {
        this.codeArea = codeArea;
        this.outputArea = outputArea;
    }

    // Method to handle the "New" button click operation
    public void execute(File currentFile, boolean hasChanged) {
    	try {
    	    if (currentFile == null) { // First-time save (Save As)
    	        JFileChooser fileChooser = new JFileChooser();
    	        fileChooser.setDialogTitle("Save As");
    	        fileChooser.setSelectedFile(new File("Untitled.txt")); // Default filename
    	        
    	        int result = fileChooser.showSaveDialog(null);
    	        
    	        if (result == JFileChooser.APPROVE_OPTION) {
    	            File fileToSave = fileChooser.getSelectedFile();
    	            
    	            // Ensure file has .txt extension (optional)
    	            if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
    	                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
    	            }
    	            
    	            // Check if file exists and confirm overwrite
    	            if (fileToSave.exists()) {
    	                int confirm = JOptionPane.showConfirmDialog(
    	                    null, 
    	                    "File already exists. Overwrite?", 
    	                    "Confirm", 
    	                    JOptionPane.YES_NO_OPTION
    	                );
    	                
    	                if (confirm != JOptionPane.YES_OPTION) {
    	                    return; // User canceled overwrite
    	                }
    	            }
    	            
    	            // Save the file
    	            saveFile(fileToSave, codeArea.getText());
    	            currentFile = fileToSave; // Update currentFile after saving
    	        }
    	    } else { // File already exists (Save, not Save As)
    	        saveFile(currentFile, codeArea.getText());
    	    }
    	} catch (IOException ex) {
    	    JOptionPane.showMessageDialog(
    	        null, 
    	        "Error saving file: " + ex.getMessage(), 
    	        "Error", 
    	        JOptionPane.ERROR_MESSAGE
    	    );
    	}
    }

	// Helper method to save file content
	private void saveFile(File file, String content) throws IOException {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	        writer.write(content);
	        JOptionPane.showMessageDialog(null, "File saved successfully!");
	    }
	}
}

