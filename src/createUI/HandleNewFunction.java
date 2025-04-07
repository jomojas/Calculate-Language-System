package createUI;

import main.Main;
import MyException.MyException;
import java.io.*;
import javax.swing.*;

public class HandleNewFunction {
    private CreateUI ui = null;

    // Constructor to initialize the JTextArea objects
    public HandleNewFunction() {
        this.ui = Main.ui;
    }

    // Method to handle the "New" button click operation
    public void execute() {
    	try {
    		if (ui.codeArea.getText().trim().isEmpty()) {
				return;
			}
    	    if (ui.currentFile == null) { // First-time save (Save As)
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
    	            saveFile(fileToSave, ui.codeArea.getText());
    	            ui.currentFile = fileToSave; // Update currentFile after saving
    	        }
    	    } else { // File already exists (Save, not Save As)
    	        saveFile(ui.currentFile, ui.codeArea.getText());
    	    }
    	} catch (MyException ex) {
    	    JOptionPane.showMessageDialog(
    	        null, 
    	        "Error saving file: " + ex.getMessage(), 
    	        "Error", 
    	        JOptionPane.ERROR_MESSAGE
    	    );
    	} finally {
    		// Clear the content of codeArea and outputArea
    		ui.codeArea.setText("");
    		ui.outputArea.setText("");
    	}
    }

	// Helper method to save file content
	private void saveFile(File file, String content) throws MyException {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	        writer.write(content);
	        JOptionPane.showMessageDialog(null, "File saved successfully!");
	    } catch (IOException ex) {
	    	throw new MyException("Error Saving file");
	    }
	}
}

