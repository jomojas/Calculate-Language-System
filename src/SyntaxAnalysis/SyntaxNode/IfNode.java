package SyntaxAnalysis.SyntaxNode;

public class IfNode extends SyntaxNode{
	public SyntaxNode condition;	// arithOrStringOrBoolExpr
	public SyntaxNode thenBlock;	// block -> exprList 
	public SyntaxNode elseBlock;	// block -> exprList (can be null)
	
	public IfNode(SyntaxNode condition, SyntaxNode thenBlock, SyntaxNode elseBlock) {
		this(condition, thenBlock);
		this.elseBlock = elseBlock;
	}
	
	public IfNode(SyntaxNode condition, SyntaxNode thenBlock) {
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
