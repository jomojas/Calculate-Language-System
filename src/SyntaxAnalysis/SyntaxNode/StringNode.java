package SyntaxAnalysis.SyntaxNode;

public class StringNode extends SyntaxNode{
	private String value;

    public StringNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString(String indent) {
        return indent + "StringNode: " + value + "\n";
    }
}
