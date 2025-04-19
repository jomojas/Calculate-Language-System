package SyntaxAnalysis.SyntaxNode;

public class BoolNode extends SyntaxNode{
	private boolean value;

    public BoolNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString(String indent) {
        return indent + "BoolNode: " + value + "\n";
    }
}
