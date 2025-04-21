package SyntaxAnalysis.SyntaxNode;

//import LexicalAnalysis.TokenType;

public class BinaryOpNode extends SyntaxNode{
	public String operator;	// COMP(<, >, <=, >=, ==, !=) ARITH(+, -, *, /, %) LOGIC(&, |) EXP(^)
    public SyntaxNode left;
    public SyntaxNode right;

    public BinaryOpNode(String operator, SyntaxNode left, SyntaxNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
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
