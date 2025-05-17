package SyntaxAnalysis.SyntaxNode;

import java.util.*;

public class DeclareNode extends SyntaxNode{
	private String type;	// "int" "bool" "string" "float"
	public List<SyntaxNode> children;	// Assign or ID
	public int row, col;	// position of key word(int, bool, string, float)

    public DeclareNode(String type, int row, int col) {
    	this.type = type;
        this.children = new ArrayList<>();
        this.row = row;
        this.col = col;
    }

    public void add(SyntaxNode node) {
        children.add(node);
    }

    public String getType() {
    	return type;
    }
    
    @Override
    public String toString(String indent) {
    	StringBuilder sb = new StringBuilder();
        sb.append(indent).append("DeclareNode (").append(type).append(")\n");
        for (SyntaxNode child : children) {
            sb.append(child.toString(indent + "\t"));
        }
        return sb.toString();
    }
}
