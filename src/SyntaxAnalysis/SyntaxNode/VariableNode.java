package SyntaxAnalysis.SyntaxNode;

public class VariableNode extends SyntaxNode{
	public String variableName;  // Name of the variable

    public VariableNode(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString(String indent) {
        return indent + "VariableNode: " + variableName + "\n";
    }
}
