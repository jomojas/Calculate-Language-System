package SyntaxAnalysis.SyntaxNode;

public class IfNode extends SyntaxNode{
	public SyntaxNode condition;	// arithOrStringOrBoolExpr
	public ExprListNode thenBlock;	// block -> exprList 
	public ExprListNode elseBlock;	// block -> exprList (can be null)
	
	public IfNode(SyntaxNode condition, ExprListNode thenBlock, ExprListNode elseBlock) {
		this(condition, thenBlock);
		this.elseBlock = elseBlock;
	}
	
	public IfNode(SyntaxNode condition, ExprListNode thenBlock) {
		this.condition = condition;
		this.thenBlock = thenBlock;
	}
	
	@Override
	public String toString(String indent) {
		 StringBuilder sb = new StringBuilder();
	        sb.append(indent).append("IfNode\n");
	        sb.append(indent).append("\tCondition:\n");
	        sb.append(condition.toString(indent + "\t\t"));
	        sb.append(indent).append("\tThenBlock:\n");
	        sb.append(thenBlock.toString(indent + "\t\t"));
	        if (elseBlock != null) {
	            sb.append(indent).append("\tElseBlock:\n");
	            sb.append(elseBlock.toString(indent + "\t\t"));
	        }
	     return sb.toString();
	}
}
