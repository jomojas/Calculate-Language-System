package SyntaxAnalysis.SyntaxNode;

public class CosNode extends SyntaxNode{
	public SyntaxNode expression;  // The expression to be calculated

    public CosNode(SyntaxNode expression) {
        this.expression = expression;
    }

    @Override
    public String toString(String indent) {
        return indent + "CosNode\n" +
               expression.toString(indent + "\t");
    }
}
