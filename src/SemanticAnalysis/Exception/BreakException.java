package SemanticAnalysis.Exception;

import SyntaxAnalysis.SyntaxNode.*;

public class BreakException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SyntaxNode node;
	
	public BreakException() {}
	
	public BreakException(SyntaxNode node) {
		this.node = node;
	}
}
