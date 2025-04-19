package SyntaxAnalysis.SyntaxNode;

public class ProgramNode extends SyntaxNode{
	public ExprListNode exprList;

    public ProgramNode() {
        this.exprList = new ExprListNode(); // Start with empty list
    }

    public void addExpr(SyntaxNode expr) {
        exprList.addExpr(expr);
    }

    @Override
    public String toString(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("ProgramNode\n");
        sb.append(exprList.toString(indent + "\t"));
        return sb.toString();
    }
}
