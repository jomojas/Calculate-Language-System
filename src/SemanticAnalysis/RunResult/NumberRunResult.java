package SemanticAnalysis.RunResult;

import MyException.MyException;

public class NumberRunResult extends RunResult {
	public int intV;
	public double floatV;
	public boolean isInt;
	
	public NumberRunResult(int intValue, double floatValue, boolean isInteger) {
		this.isInt = isInteger;
		this.floatV = isInteger ? 0.0 : floatValue;
		this.intV = isInteger ? intValue : 0;
	}
	
	@Override
	public String getResultType() {
		return "NumberRunResult";
	}
	
	public NumberRunResult setNegative() {
		return this.isInt ? new NumberRunResult(-intV, 0.0, true) : new NumberRunResult(0, -floatV, false);
	}
	
	public CompareResult compare(RunResult other) throws MyException {
		if(other instanceof NumberRunResult numRes) {
			double thisValue = this.getFloatValue();
			double otherValue = numRes.getFloatValue();
			
			if (Math.abs(thisValue - otherValue) < 1e-10) {
	            return CompareResult.EQUAL;
	        }
			
			return thisValue < otherValue ? CompareResult.SMALLER : CompareResult.GREATER;
		}
		throw new MyException("Unsupported Type For Number Compare: " + other.getResultType());
	}
	
	/*
	 * int + int -> int
	 * int + float -> float
	 * float + float -> float
	 */
	public RunResult add(RunResult other) throws MyException {
		if(other instanceof NumberRunResult numRes) {
			if(this.isInt && numRes.isInt) {
				long result = (long)intV + (long)numRes.intV;
				if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
					return new NumberRunResult(0, (double)result, false);
				}
				return new NumberRunResult((int)result, 0.0, true);
			}
			return new NumberRunResult(0, this.getFloatValue() + numRes.getFloatValue(), false);
		} else if(other instanceof StringRunResult strRes) {
			return new StringRunResult(this.toString() + strRes.value);
		}
		throw new MyException("Unsupported Type For Number Addition: " + other.getResultType());
	}
	
	/*
	 * int - int -> int
	 * int - float -> float
	 * float - int -> float
	 * float - float -> float
	 */
	public NumberRunResult sub(RunResult other) throws MyException {
		if(other instanceof NumberRunResult numRes) {
			if(this.isInt && numRes.isInt) {
				long result = (long)intV - (long)numRes.intV;
				if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
					return new NumberRunResult(0, (double)result, false);
				}
				return new NumberRunResult((int)result, 0.0, true);
			}
			return new NumberRunResult(0, this.getFloatValue() - numRes.getFloatValue(), false);
		}
		throw new MyException("Unsupported Type For Number Subtraction: " + other.getResultType());
	}
	
	/*
	 * int * int -> int
	 * int * float -> float
	 * float * float -> float 
	 */
	public NumberRunResult mul(RunResult other) throws MyException {
		if(other instanceof NumberRunResult numRes) {
			if(this.isInt && numRes.isInt) {
				long result = (long)intV * (long)numRes.intV;
				if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
					return new NumberRunResult(0, (double)result, false);
				}
				return new NumberRunResult((int)result, 0.0, true);
			}
			return new NumberRunResult(0, this.getFloatValue() * numRes.getFloatValue(), false);
		}
		throw new MyException("Unsupported Type For Number Multiplication: " + other.getResultType());
	}
	
	/*
	 * int / int -> int (only if there is no remainder) / float
	 * int / float -> float
	 * float / int -> float
	 * float / float -> float
	 */
	public NumberRunResult div(RunResult other) throws MyException {
		if(other instanceof NumberRunResult numRes) {
			if(numRes.getFloatValue() == 0.0) {
				throw new MyException("Division By Zero");
			}
			// No remainder
			if(this.isInt && numRes.isInt && this.intV % numRes.intV == 0) {
				return new NumberRunResult(this.intV / numRes.intV, 0, true);
			}
			return new NumberRunResult(0, this.getFloatValue() / numRes.getFloatValue(), false);
		}
		throw new MyException("Unsupported Type For Number Division: " + other.getResultType());
	}
	
	/*
	 * The sign of result follows dividend -17 % 5 == -2
	 * int % int -> int
	 * int % float -> float
	 * float % int -> float
	 * float % float -> float
	 */
	public NumberRunResult mod(RunResult other) throws MyException {
		if(other instanceof NumberRunResult numRes) {
			if(numRes.getFloatValue() == 0.0) {
				throw new MyException("Modulo By Zero");
			}
			
			if(this.isInt && numRes.isInt) {
				return new NumberRunResult(this.intV % numRes.intV, 0.0, true);
			}
			return new NumberRunResult(0, this.getFloatValue() % numRes.getFloatValue(), false);
		}
		throw new MyException("Unsupported Type For Number Modulo: " + other.getResultType());
	}
	
	/*
	 * Only when both this and other are integer, the result be integer
	 */
	public NumberRunResult exp(RunResult other) throws MyException {
		if(other instanceof NumberRunResult numRes) {
			double result = Math.pow(this.getFloatValue(), numRes.getFloatValue());
			if (this.isInt && numRes.isInt && 
			        result == (int)result && 
			        result <= Integer.MAX_VALUE) {
			        return new NumberRunResult((int)result, 0.0, true);
			    }
			return new NumberRunResult(0, result, false);
		}
		throw new MyException("Unsupported Type For Number Exponent: " + other.getResultType());
	}
	
	public NumberRunResult sin() {
		double result = Math.sin(Math.toRadians(this.getFloatValue()));
        
        if (this.isInt && result == (int)result) {
            return new NumberRunResult((int)result, 0.0, true);
        }
        return new NumberRunResult(0, result, false);
	}
	
	public NumberRunResult cos() {
        double result = Math.cos(Math.toRadians(this.getFloatValue()));
        if (this.isInt && result == (int)result) {
            return new NumberRunResult((int)result, 0.0, true);
        }
        return new NumberRunResult(0, result, false);
    }
    
    public NumberRunResult tan() {
        double result = Math.tan(Math.toRadians(this.getFloatValue()));
        if (this.isInt && result == (int)result) {
            return new NumberRunResult((int)result, 0.0, true);
        }
        return new NumberRunResult(0, result, false);
    }
	
	public double getFloatValue() {
		return isInt ? intV : floatV;
	}
	
	public String toString() {
		if(isInt) {
			return Integer.toString(intV);
		} else {
			return Double.toString(floatV);
		}
	}
}
