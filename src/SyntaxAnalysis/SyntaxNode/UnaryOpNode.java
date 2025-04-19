package SyntaxAnalysis.SyntaxNode;

public class UnaryOpNode extends SyntaxNode{
	public String operator;      // e.g. "+", "-", "!"
	public SyntaxNode operand;    // The expression the operator is applied to
	
	public UnaryOpNode(String operator, SyntaxNode operand) {
	    this.operator = operator;
	    this.operand = operand;
	}
	
	@Override
	public String toString(String indent) {
	    StringBuilder sb = new StringBuilder();
	    sb.append(indent).append("UnaryNode: ").append(operator).append("\n");
	    sb.append(operand.toString(indent + "\t"));
	        return sb.toString();
	}
}
