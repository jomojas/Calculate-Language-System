package SemanticAnalysis;

import java.util.*;

import javax.swing.JTextArea;

import MyException.MyException;
import SemanticAnalysis.Exception.*;
import SemanticAnalysis.RunResult.*;
import SyntaxAnalysis.SyntaxNode.*;
import SemanticAnalysis.VariableType;

public class Interpret {
//	private Map<String, VariableType> varTypeContainer = new HashMap<>();
//	private Map<String, RunResult> varContainer = new HashMap<>();
	private Stack<Map<String, VariableType>> varTypeStack = new Stack<>();
	private Stack<Map<String, RunResult>> varStack = new Stack<>();
	private JTextArea output;
	
	public Interpret(JTextArea outputArea) {
//		varTypeContainer.clear();
//		varContainer.clear();
		this.output = outputArea;
	}
	
	public void enterScope() {
		varTypeStack.push(new HashMap<>());
		varStack.push(new HashMap<>());
	}
	
	public void exitScope() {
		varTypeStack.pop();
		varStack.pop();
	}
	
	public void executeProgram(ProgramNode program) throws MyException {
		varTypeStack.clear();
		varStack.clear();
		enterScope();
		processExprListNode(program.exprList);
	}
	
	public RunResult processExprListNode(ExprListNode exprList) throws MyException {
		RunResult lastResult = new VoidRunResult();
		for(SyntaxNode node : exprList.getExprList()) {
			lastResult = executeNode(node);
		}
		return lastResult;
	}
	
	public RunResult executeNode(SyntaxNode node) throws MyException {
		if(node instanceof AssignNode asNode) {
			return processAssignNode(asNode);
		} else if(node instanceof DeclareNode deNode) {
			return processDeclareNode(deNode);
		} else if(node instanceof PrintNode prNode) {
			return processPrintNode(prNode);
		} else if(node instanceof SinNode siNode) {
			return processSinNode(siNode);
		} else if(node instanceof CosNode coNode) {
			return processCosNode(coNode);
		} else if(node instanceof TanNode taNode) {
			return processTanNode(taNode);
		} else if(node instanceof ExprListNode exNode) {
			return processExprListNode(exNode);
		} else if(node instanceof IfNode ifNode) {
			return processIfNode(ifNode);
		} else if(node instanceof WhileNode whNode) {
			return processWhileNode(whNode);
		} else if(node instanceof BreakNode) {
			throw new BreakException(node);
		} else if(node instanceof ContinueNode) {
			throw new ContinueException(node);
		} else {
			throw new MyException("Unknown Node Type");
		}
	}
	
	public RunResult processAssignNode(AssignNode node) throws MyException {
		if(node.isDeclaration) {
			RunResult expression = processArithOrStringOrBoolExpr(node.expression);
			varStack.peek().put(node.variableName, expression);
		} else {
			Map<String, RunResult> targetScope = findVariableScope(node.variableName);
			if(targetScope == null) {
				throw new MyException("Undefined variable: " + node.variableName);
			}
			RunResult expression = processArithOrStringOrBoolExpr(node.expression);
			targetScope.put(node.variableName, expression);
		}
		return new VoidRunResult();
	}
	
	public RunResult processDeclareNode(DeclareNode node) throws MyException {
		for(SyntaxNode singleNode : node.children) {
			if(singleNode instanceof VariableNode snNode) {
				if(varTypeStack.peek().containsKey(snNode.variableName)) {
					throw new MyException("Duplicate declaration: " + snNode.variableName);
				}
				switch (node.getType()) {
				case "int":
					varTypeStack.peek().put(snNode.variableName, VariableType.INT);
					varStack.peek().put(snNode.variableName, null);
					break;
				case "float":
					varTypeStack.peek().put(snNode.variableName, VariableType.FLOAT);
					varStack.peek().put(snNode.variableName, null);
					break;
				case "string":
					varTypeStack.peek().put(snNode.variableName, VariableType.STRING);
					varStack.peek().put(snNode.variableName, null);
					break;
				case "bool":
					varTypeStack.peek().put(snNode.variableName, VariableType.BOOL);
					varStack.peek().put(snNode.variableName, null);
					break;
				}
			} else if(singleNode instanceof AssignNode asNode) {
				if(varTypeStack.peek().containsKey(asNode.variableName)) {
					throw new MyException("Duplicate declaration: " + asNode.variableName);
				}
				switch (node.getType()) {
				case "int":
					varTypeStack.peek().put(asNode.variableName, VariableType.INT);
					processAssignNode(asNode);
					break;
				case "float":
					varTypeStack.peek().put(asNode.variableName, VariableType.FLOAT);
					processAssignNode(asNode);
					break;
				case "string":
					varTypeStack.peek().put(asNode.variableName, VariableType.STRING);
					processAssignNode(asNode);
					break;
				case "bool":
					varTypeStack.peek().put(asNode.variableName, VariableType.BOOL);
					processAssignNode(asNode);
					break;
				}
			}
		}
		return new VoidRunResult();
	}
	
	public RunResult processPrintNode(PrintNode node) throws MyException {
		RunResult expression = new VoidRunResult();
		expression = processArithOrStringOrBoolExpr(node.expression);
		output.append(expression.toString());
		return expression;
	}
	
	public RunResult processSinNode(SinNode node) throws MyException {
		RunResult expression = processArithOrStringOrBoolExpr(node.expression);
		if(expression instanceof NumberRunResult numRes) {			
			return numRes.sin();
		}
		throw new MyException("Unsupported Type For SIN Calculation: " + expression.getResultType());
	}
	
	public RunResult processCosNode(CosNode node) throws MyException {
		RunResult expression = processArithOrStringOrBoolExpr(node.expression);
		if(expression instanceof NumberRunResult numRes) {			
			return numRes.cos();
		}
		throw new MyException("Unsupported Type For COS Calculation: " + expression.getResultType());
	}
	
	public RunResult processTanNode(TanNode node) throws MyException {
		RunResult expression = processArithOrStringOrBoolExpr(node.expression);
		if(expression instanceof NumberRunResult numRes) {			
			return numRes.tan();
		}
		throw new MyException("Unsupported Type For TAN Calculation: " + expression.getResultType());
	}
	
	public RunResult processIfNode(IfNode node) throws MyException {
		enterScope();
		RunResult condition = processArithOrStringOrBoolExpr(node.condition);
		if(!(condition instanceof BoolRunResult)) {			
			throw new MyException("Unsupported Type For IF Condition: " + condition.getResultType());
		}
		BoolRunResult boolRes = (BoolRunResult)condition;
		// IF Block
		if(boolRes.value) {
			processExprListNode(node.thenBlock);
		} else { // Then Block
			processExprListNode(node.elseBlock);
		}
		exitScope();
		return new VoidRunResult();
	}
	
	public RunResult processWhileNode(WhileNode node) throws MyException {
		enterScope();
		while(true) {
			try {
				RunResult condition = processArithOrStringOrBoolExpr(node.condition);
				if(!(condition instanceof BoolRunResult)) {			
					throw new MyException("Unsupported Type For WHILE Condition: " + condition.getResultType());
				}
				BoolRunResult boolRes = (BoolRunResult)condition;
				if(boolRes.value) {
					processExprListNode(node.body);
				} else {
					break;
				}
			} catch (BreakException e) {
				break;
			} catch (ContinueException e) {
				continue;
			}
		}
		
		exitScope();
		return new VoidRunResult();
	}
	
	public RunResult processArithOrStringOrBoolExpr(SyntaxNode node) throws MyException {
		if(node instanceof VariableNode vNode) {
			Map<String, RunResult> targetScope = findVariableScope(vNode.variableName);
			if(targetScope == null) {
				throw new MyException("Undefined Variable: " + vNode.variableName);
			}
			RunResult value = targetScope.get(vNode.variableName);
			if(value == null) {
				throw new MyException("Uninitialized variable: " + vNode.variableName);
			}
			return value;
		} else if(node instanceof BinaryOpNode bNode) {
			RunResult left = processArithOrStringOrBoolExpr(bNode.left);
			RunResult right = processArithOrStringOrBoolExpr(bNode.right);
			return processBinaryOpNode(bNode.operator, left, right);
		} else if(node instanceof UnaryOpNode uNode) {
			RunResult operand = processArithOrStringOrBoolExpr(uNode.operand);
			switch (uNode.operator) {
			case "+":
				if(!(operand instanceof NumberRunResult)) {					
					throw new MyException("Unsupported Type For Unary Operator +:" + operand.getResultType());
				}
				if(operand instanceof NumberRunResult numRes) {
					if(numRes.isInt) {
						return new NumberRunResult(numRes.intV, 0.0, true);
					} else {
						return new NumberRunResult(0, numRes.floatV, false);
					}
				}
				break;
			case "-":
				if(!(operand instanceof NumberRunResult)) {					
					throw new MyException("Unsupported Type For Unary Operator -:" + operand.getResultType());
				}
				if(operand instanceof NumberRunResult numRes) {
					if(numRes.isInt) {
						return new NumberRunResult(-numRes.intV, 0.0, true);
					} else {
						return new NumberRunResult(0, -numRes.floatV, false);
					}
				}
				break;
			case "!":
				if(!(operand instanceof BoolRunResult)) {					
					throw new MyException("Unsupported Type For Unary Operator !:" + operand.getResultType());
				}
				if(operand instanceof BoolRunResult boolRes) {
					if(boolRes.value) {
						return new BoolRunResult(!boolRes.value);
					} else {
						return new BoolRunResult(!boolRes.value);
					}
				}
				break;
			default:
				throw new MyException("Unknown Unary Operator: " + uNode.operator);
			}
		} else if(node instanceof NumberNode nNode) {
			if(nNode.isInt()) {
				return new NumberRunResult(nNode.getIntValue(), 0.0, true);
			} else {
				return new NumberRunResult(0, nNode.getFloatValue(), false);
			}
		} else if(node instanceof StringNode sNode) {
			return new StringRunResult(sNode.getValue());
		} else if(node instanceof BoolNode bNode) {
			return new BoolRunResult(bNode.getValue());
		}
		return null;
	}
	
	public RunResult processBinaryOpNode(String operator, RunResult left, RunResult right) throws MyException {
		switch (operator) {
		// ARITH(+, -, *, /, %, ^)
		case "+":
			if(left instanceof NumberRunResult leftRes) {
				return leftRes.add(right);
			} else if(left instanceof StringRunResult leftRes) {
				return leftRes.add(right);
			} else if(left instanceof BoolRunResult leftRes) {
				return leftRes.add(right);
			} else {
				throw new MyException("Unsupported Type for Addition: " + left.getResultType());
			}
//			break;
		case "-":
			if(left instanceof NumberRunResult leftRes) {
				return leftRes.sub(right);
			} else {
				throw new MyException("Unsupported Type For Subtraction: " + left.getResultType());
			}
//			break;
		case "*":
			if(left instanceof NumberRunResult leftRes) {
				return leftRes.mul(right);
			} else if(left instanceof StringRunResult leftRes) {
				return leftRes.mul(right);
			} else {
				throw new MyException("Unsupported Type For Multiplication: " + left.getResultType());
			}
//			break;
		case "/":
			if(left instanceof NumberRunResult leftRes) {
				return leftRes.div(right);
			} else {
				throw new MyException("Unsupported Type For Division: " + left.getResultType());
			}
//			break;
		case "%":
			if(left instanceof NumberRunResult leftRes) {
				return leftRes.mod(right);
			} else {
				throw new MyException("Unsupported Type For Modulo: " + left.getResultType());
			}
//			break;
		case "^":
			if(left instanceof NumberRunResult leftRes) {
				return leftRes.exp(right);
			} else {
				throw new MyException("Unsupported Type For Exponent: " + left.getResultType());
			}
//			break;
			
		// COMP(>, >=, <, <=, ==, !=)
		case ">":
			if(left instanceof NumberRunResult leftRes) {
				CompareResult res = leftRes.compare(right);
				if(res == CompareResult.GREATER) {
					return new BoolRunResult(true);
				} else {
					return new BoolRunResult(false);
				}
			} else {
				throw new MyException("Unsupported Type For Comparison: " + left.getResultType());
			}
//			break;
		case ">=":
			if(left instanceof NumberRunResult leftRes) {
				CompareResult res = leftRes.compare(right);
				if(res == CompareResult.GREATER || res == CompareResult.EQUAL) {
					return new BoolRunResult(true);
				} else {
					return new BoolRunResult(false);
				}
			} else {
				throw new MyException("Unsupported Type For Comparison: " + left.getResultType());
			}
//			break;
		case "<":
			if(left instanceof NumberRunResult leftRes) {
				CompareResult res = leftRes.compare(right);
				if(res == CompareResult.SMALLER) {
					return new BoolRunResult(true);
				} else {
					return new BoolRunResult(false);
				}
			} else {
				throw new MyException("Unsupported Type For Comparison: " + left.getResultType());
			}
//			break;
		case "<=":
			if(left instanceof NumberRunResult leftRes) {
				CompareResult res = leftRes.compare(right);
				if(res == CompareResult.SMALLER || res == CompareResult.EQUAL) {
					return new BoolRunResult(true);
				} else {
					return new BoolRunResult(false);
				}
			} else {
				throw new MyException("Unsupported Type For Comparison: " + left.getResultType());
			}
//			break;
		case "==":
			if(left instanceof NumberRunResult leftRes) {
				CompareResult res = leftRes.compare(right);
				if(res == CompareResult.EQUAL) {
					return new BoolRunResult(true);
				} else {
					return new BoolRunResult(false);
				}
			} else if(left instanceof StringRunResult strRes1 && right instanceof StringRunResult strRes2) { 
				if(strRes1.value.equals(strRes2.value)) {
					return new BoolRunResult(true);
				} else {
					return new BoolRunResult(false);
				}
			} else if(left instanceof BoolRunResult boolRes1 && right instanceof BoolRunResult boolRes2) {
				if(boolRes1.value == boolRes2.value) {
					return new BoolRunResult(true);
				} else {
					return new BoolRunResult(false);
				}
			} else {
				throw new MyException("Unsupported Type For Comparison: " + left.getResultType());
			}
//			break;
		case "!=":
			if(left instanceof NumberRunResult leftRes) {
				CompareResult res = leftRes.compare(right);
				if(res == CompareResult.GREATER || res == CompareResult.SMALLER) {
					return new BoolRunResult(true);
				} else {
					return new BoolRunResult(false);
				}
			} else {
				throw new MyException("Unsupported Type For Comparison: " + left.getResultType());
			}
//			break;
			
		// LOGIC(&, |)
		case "&":
			if(left instanceof BoolRunResult leftRes) {
				return leftRes.and(right);
			} else {
				throw new MyException("Unsupported Type For Logical Operations: " + left.getResultType());
			}
//			break;
		case "|":
			if(left instanceof BoolRunResult leftRes) {
				return leftRes.or(right);
			} else {
				throw new MyException("Unsupported Type For Logical Operations: " + left.getResultType());
			}
//			break;
		}
		return null;
	}
	
	public Map<String, RunResult> findVariableScope(String varName) {
	    for (int i = varStack.size() - 1; i >= 0; i--) {
	        if (varStack.get(i).containsKey(varName)) {
	            return varStack.get(i);
	        }
	    }
	    return null;
	}
}




