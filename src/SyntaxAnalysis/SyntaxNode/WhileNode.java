package SyntaxAnalysis.SyntaxNode;

public class WhileNode extends SyntaxNode{
	public SyntaxNode condition;	// arithOrStringOrBoolExpr
	public ExprListNode body;			// block (exprList)
	
	public WhileNode(SyntaxNode condition, ExprListNode body) {
		this.condition = condition;
		this.body = body;
	}
	
	@Override
	public String toString(String indent) {
		StringBuilder sb = new StringBuilder();
		sb.append(indent).append("WhileNode\n");
        sb.append(indent).append("\tCondition:\n");
        sb.append(condition.toString(indent + "\t\t"));
        sb.append(indent).append("\tBody:\n");
        sb.append(body.toString(indent + "\t\t"));
        return sb.toString();
	}
}
