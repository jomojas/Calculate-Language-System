package SyntaxAnalysis.SyntaxNode;

public class NumberNode extends SyntaxNode{
	private double Fvalue;
	private int Ivalue;
	private boolean isInt;
	
	public NumberNode(double value) {
	    this.Fvalue = value;
	    this.isInt = false;
	}
	
	public NumberNode(int value) {
		this.Ivalue = value;
		this.isInt = true;
	}
	
	public double getFloatValue() {
		return Fvalue;
	}
	
	public int getIntValue() {
		return Ivalue;
	}
	
	public boolean isInt() {
		return isInt;
	}
	
	@Override
	public String toString(String indent) {
	    if(isInt) {
	    	return indent + "NumberNode: " + Ivalue + "\n"; 
	    } else {
	    	return indent + "NumberNode: " + Fvalue + "\n";
	    }
	}
}
