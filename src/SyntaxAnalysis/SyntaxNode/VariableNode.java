package SyntaxAnalysis.SyntaxNode;

public class VariableNode extends SyntaxNode{
	public String variableName;  // Name of the variable
	public int row, col;	// position of current variable

    public VariableNode(String variableName, int row, int col) {
        this.variableName = variableName;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString(String indent) {
        return indent + "VariableNode: " + variableName + "\n";
    }
}
