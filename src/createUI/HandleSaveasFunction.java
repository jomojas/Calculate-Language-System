package createUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import MyException.MyException;
import main.Main;

public class HandleSaveasFunction {
	private CreateUI ui;
	
	public HandleSaveasFunction() {
		this.ui = Main.ui;
	}
	
	public void execute() {
		try {
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
		} catch (MyException ex) {
			JOptionPane.showMessageDialog(
	    	        null, 
	    	        "Error saving file: " + ex.getMessage(), 
	    	        "Error", 
	    	        JOptionPane.ERROR_MESSAGE
	    	    );
		}
	}


	private void saveFile(File file, String content) throws MyException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	        writer.write(content);
	        JOptionPane.showMessageDialog(null, "File saved successfully!");
	    } catch (IOException ex) {
	    	throw new MyException("Error Saving file");
	    }
	}
}