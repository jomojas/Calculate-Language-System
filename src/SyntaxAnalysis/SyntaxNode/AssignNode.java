package SyntaxAnalysis.SyntaxNode;

public class AssignNode extends SyntaxNode{
	public String variableName;		// String
    public SyntaxNode expression;	// expression can be arithmetic, string, boolean, etc.
    public boolean isDeclaration;	// Check whether it is declaration

    public AssignNode(String variableName, SyntaxNode expression, boolean isDeclaration) {
        this.variableName = variableName;
        this.expression = expression;
        this.isDeclaration = isDeclaration;
    }

    @Override
    public String toString(String indent) {
        return indent + "AssignNode\n" +
               indent + "\tVariable: " + variableName + "\n" +
               indent + "\tExpression:\n" + expression.toString(indent + "\t\t");
    }
}
