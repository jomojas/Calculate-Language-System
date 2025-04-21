package createUI;

import java.util.ArrayList;
import LexicalAnalysis.SeparateToken;
import LexicalAnalysis.Token;
import MyException.MyException;
import SyntaxAnalysis.*;
import main.Main;

public class HandleRunFunction {
	private CreateUI ui;
	private SeparateToken separateToken;
	private String code;
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private String SyntaxTree;
	
	public HandleRunFunction() {
		this.ui = Main.ui;
	}
	
	public void execute() {
//		try {
//			code = ui.codeArea.getText();
//			separateToken = new SeparateToken(code);
//			tokens = separateToken.getTokens();
//			displayTokens();
//		} catch(MyException ex) {
//			String errorMessage = ex.msg;
//			ui.outputArea.setText(errorMessage);
//		}
		
		try {
			code = ui.codeArea.getText();
			Parser pa = new Parser(code);
//			separateToken = new SeparateToken(code);
//			tokens = separateToken.getTokens();
			SyntaxTree = pa.returnSyntaxTree();
			displaySyntaxTree(SyntaxTree);
		} catch(MyException ex) {
			String errorMessage = ex.msg;
			ui.outputArea.setText(errorMessage);
		}
		
	}
	
	// Display tokens on outputArea
//	private void displayTokens() {
//		StringBuffer tokenString = new StringBuffer();
//		for (Token singleToken : tokens) {
//			tokenString.append(singleToken.getType())
//			.append('\t').append(singleToken.getRow())
//			.append('\t').append(singleToken.getCol())
//			.append('\t').append(singleToken.getValue())
//			.append('\n');
//		}
//		ui.outputArea.setText(tokenString.toString());
//	}
	
	public void displaySyntaxTree(String SyntaxTree) {
//		ui.outputArea.setText(SyntaxTree);
		System.out.println(SyntaxTree);
	}
}



