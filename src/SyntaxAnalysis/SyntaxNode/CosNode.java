package SyntaxAnalysis.SyntaxNode;

public class CosNode extends SyntaxNode{
	public SyntaxNode expression;  // The expression to be calculated
	public int row, col; 	// position of expression

    public CosNode(SyntaxNode expression, int row, int col) {
        this.expression = expression;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString(String indent) {
        return indent + "CosNode\n" +
               expression.toString(indent + "\t");
    }
}
