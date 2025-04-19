package SyntaxAnalysis.SyntaxNode;

public class SinNode extends SyntaxNode{
	public SyntaxNode expression;  // The expression to be calculated

    public SinNode(SyntaxNode expression) {
        this.expression = expression;
    }

    @Override
    public String toString(String indent) {
        return indent + "SinNode\n" +
               expression.toString(indent + "\t");
    }
}
