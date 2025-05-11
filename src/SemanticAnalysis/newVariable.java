package SemanticAnalysis;

import SyntaxAnalysis.SyntaxNode.SyntaxNode;

public class newVariable {
	private VariableType type;
	private String name;
	private SyntaxNode node;
	
	public newVariable(VariableType variableType, String variableName, SyntaxNode variableNode) {
		this.type = variableType;
		this.name = variableName;
		this.node = variableNode;
	}
}
