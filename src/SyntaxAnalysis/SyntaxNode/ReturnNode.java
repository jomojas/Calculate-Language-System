package SyntaxAnalysis.SyntaxNode;

public class ReturnNode extends SyntaxNode{
	public SyntaxNode returnExpr; // arithOrStringOrBoolExpr (cannot be null)

    public ReturnNode(SyntaxNode returnExpr) {
        this.returnExpr = returnExpr;
    }

    @Override
    public String toString(String indent) {
            return indent + "ReturnNode\n" + returnExpr.toString(indent + "\t");
    }
}
