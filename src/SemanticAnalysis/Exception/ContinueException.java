package SemanticAnalysis.Exception;

import SyntaxAnalysis.SyntaxNode.*;

public class ContinueException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SyntaxNode node;
	
	public ContinueException() {}
	
	public ContinueException(SyntaxNode node) {
		this.node = node;
	}
}
