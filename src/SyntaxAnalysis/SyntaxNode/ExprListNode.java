package SyntaxAnalysis.SyntaxNode;

import java.util.*;
//import java.util.List;

public class ExprListNode extends SyntaxNode{
	private final List<SyntaxNode> expressions;
	
	public ExprListNode() {
	    this.expressions = new ArrayList<>();
	}
	
	public void addExpr(SyntaxNode expr) {
	    expressions.add(expr);
	}
	
	@Override
	public String toString(String indent) {
	    StringBuilder sb = new StringBuilder();
	    sb.append(indent).append("ExprListNode\n");
	    for (SyntaxNode expr : expressions) {
	        sb.append(expr.toString(indent + "\t"));
	    }
	    return sb.toString();
	}
}
