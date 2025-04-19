package SyntaxAnalysis.SyntaxNode;

public class TanNode extends SyntaxNode{
	public SyntaxNode expression;  // The expression to be calculated

    public TanNode(SyntaxNode expression) {
        this.expression = expression;
    }

    @Override
    public String toString(String indent) {
        return indent + "TanNode\n" +
               expression.toString(indent + "\t");
    }
}
