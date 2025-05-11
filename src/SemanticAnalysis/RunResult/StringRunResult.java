package SemanticAnalysis.RunResult;

import MyException.MyException;

public class StringRunResult extends RunResult {
	public String value;
	
	public StringRunResult(String value) {
		this.value = value;
	}
	
	public StringRunResult add(RunResult other) throws MyException {
		if(other instanceof VoidRunResult) {
			throw new MyException("Unsupported Type For String Addition: " + other.getResultType());
		}
		return new StringRunResult(value + other.toString());
	}
	
	public StringRunResult mul(RunResult other) throws MyException {
		if(other instanceof NumberRunResult numRes) {
			int count = numRes.isInt ? numRes.intV : (int)numRes.floatV;
			if(count < 0) {
				throw new MyException("Cannot Multiply String By Negative Number: " + count);
			}
			return new StringRunResult(value.repeat(count));
		}
		throw new MyException("Unsupported Type For String Multiplication: " + other.getResultType());		
	}
	
	@Override
	public String getResultType() {
		return "StringRunResult";
	}
	
	public String toString() {
		return value;
	}
}
