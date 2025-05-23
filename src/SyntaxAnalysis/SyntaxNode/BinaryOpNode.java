package SyntaxAnalysis.SyntaxNode;

//import LexicalAnalysis.TokenType;

public class BinaryOpNode extends SyntaxNode{
	public String operator;	// COMP(<, >, <=, >=, ==, !=) ARITH(+, -, *, /, %) LOGIC(&, |) EXP(^)
    public SyntaxNode left;
    public SyntaxNode right;
    public int row, col; 	// position of operator

    public BinaryOpNode(String operator, SyntaxNode left, SyntaxNode right, int row, int col) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("BinaryOpNode: ").append(operator).append("\n");
        sb.append(left.toString(indent + "\t"));
        sb.append(right.toString(indent + "\t"));
        return sb.toString();
    }
}
