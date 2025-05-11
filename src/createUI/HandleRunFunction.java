package createUI;

import java.util.ArrayList;
import LexicalAnalysis.SeparateToken;
import LexicalAnalysis.Token;
import MyException.MyException;
import SemanticAnalysis.Interpret;
import SyntaxAnalysis.*;
import SyntaxAnalysis.SyntaxNode.ProgramNode;
import SyntaxAnalysis.SyntaxNode.SyntaxNode;
import main.Main;

public class HandleRunFunction {
	private CreateUI ui;
	private SeparateToken separateToken;
	private String code;
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private String SyntaxTree;
	private ProgramNode root;
	
	public HandleRunFunction() {
		this.ui = Main.ui;
	}
	
	public void execute() {
		try {
			ui.outputArea.setText("");
			code = ui.codeArea.getText();
			Parser pa = new Parser(code);
			root = pa.returnSyntaxTree();
			Interpret interpreter = new Interpret(ui.outputArea);
			interpreter.executeProgram(root);
//			displaySyntaxTree(SyntaxTree);
		} catch(MyException ex) {
			String errorMessage = ex.msg;
			ui.outputArea.setText(errorMessage);
		}
		
	}
	
	public void displaySyntaxTree(String SyntaxTree) {
//		ui.outputArea.setText(SyntaxTree);
		System.out.println(SyntaxTree);
	}
}



