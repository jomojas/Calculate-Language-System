package SyntaxAnalysis.SyntaxNode;

public abstract class SyntaxNode {
	public abstract String toString(String indent);

    @Override
    public String toString() {
        return toString("");
    }
}
