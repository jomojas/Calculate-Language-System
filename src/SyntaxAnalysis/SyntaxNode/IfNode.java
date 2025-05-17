package SyntaxAnalysis.SyntaxNode;

public class IfNode extends SyntaxNode{
	public SyntaxNode condition;	// arithOrStringOrBoolExpr
	public int row, col; 	// position of condition
	public ExprListNode thenBlock;	// block -> exprList 
	public ExprListNode elseBlock;	// block -> exprList (can be null)
	
	public IfNode(SyntaxNode condition, ExprListNode thenBlock, ExprListNode elseBlock, int row, int col) {
		this(condition, thenBlock, row, col);
		this.elseBlock = elseBlock;
		this.row = row;
		this.col = col;
	}
	
	public IfNode(SyntaxNode condition, ExprListNode thenBlock, int row, int col) {
		this.condition = condition;
		this.thenBlock = thenBlock;
		this.row = row;
		this.col = col;
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
