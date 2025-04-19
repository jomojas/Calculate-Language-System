package SyntaxAnalysis.SyntaxNode;

public class NumberNode extends SyntaxNode{
	private double value;
	
	public NumberNode(double value) {
	    this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	@Override
	public String toString(String indent) {
	    // If value is an integer, display it without a decimal point
	    if (value == (int) value) {
	        return indent + "NumberNode: " + (int) value + "\n"; // Casting to int for integer output
	    }
	    return indent + "NumberNode: " + value + "\n";
	}
}
