package SyntaxAnalysis.SyntaxNode;

public class PrintNode extends SyntaxNode {
	public SyntaxNode expression;  // The expression to be printed

    public PrintNode(SyntaxNode expression) {
        this.expression = expression;
    }

    @Override
    public String toString(String indent) {
        return indent + "PrintNode\n" +
               expression.toString(indent + "\t");
    }
}
