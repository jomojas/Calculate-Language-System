package SemanticAnalysis.RunResult;

import MyException.MyException;

public class BoolRunResult extends RunResult {
	public boolean value;
	
	public BoolRunResult(boolean value) {
		this.value = value;
	}
	
	public BoolRunResult not() {
		return new BoolRunResult(!value);
	}
	
	public String toString() {
		return Boolean.toString(value);
	}
	
	public RunResult add(RunResult other) throws MyException {
		if(other instanceof BoolRunResult boolRes) {
			return new BoolRunResult(this.value || boolRes.value);
		} else if(other instanceof StringRunResult stringRes) {
			return new StringRunResult(this.toString() + stringRes.value);
		} else {
			throw new MyException("Unsupported Type For Boolean Addition: " + other.getResultType());
		}
	}
	
	public RunResult and(RunResult other) throws MyException {
		if(other instanceof BoolRunResult boolRes) {
			return new BoolRunResult(this.value && boolRes.value);
		} else {
			throw new MyException("Unsupported Type For Logical AND: " + other.getResultType());
		}
	}
	
	public RunResult or(RunResult other) throws MyException {
		if(other instanceof BoolRunResult boolRes) {
			return new BoolRunResult(this.value || boolRes.value);
		} else {
			throw new MyException("Unsupported Type For Logical OR: " + other.getResultType());
		}
	}
	
	public String getResultType() {
		return "BoolRunResult";
	}
}
