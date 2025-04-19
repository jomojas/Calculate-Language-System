package SyntaxAnalysis;

import java.util.*;

import SyntaxAnalysis.SyntaxNode.*;
import MyException.*;
import LexicalAnalysis.*;

public class Parser {
	private ArrayList<Token> tokens;
	private int currentTokenIndex = 0;
	
	public Parser(String codeString) throws MyException {
		SeparateToken se = new SeparateToken(codeString);
		this.tokens = se.getTokens();
//		SyntaxNode rootNode = parseProgram();
//		rootNode.toString("");
	}
	
	public String returnSyntaxTree() throws MyException {
		SyntaxNode rootNode = parseProgram();
		return rootNode.toString("");
	}
	
	public Token currentToken() {
		return tokens.get(currentTokenIndex);
	}
	
	public void advanceToken() {
		currentTokenIndex++;
	}
	
	public Token peekNextToken() {
		return tokens.get(currentTokenIndex + 1);
	}
	
	public Token peekLastToken() {
		return tokens.get(currentTokenIndex - 1);
	}
	
	private void expect(TokenType expectedType) throws MyException {
	    if (currentToken().getType() != expectedType) {
	        throw new MyException("Unexpected token: " + currentToken().getType() +
	            "row:" + currentToken().getRow() + "col:" + currentToken().getCol());
	    }
	}

	
	// Return a programNode which is also the root of syntax tree
	public SyntaxNode parseProgram() throws MyException {
		ProgramNode pn = new ProgramNode();
		
		while (currentToken().getType() != TokenType.EOF) {
	        SyntaxNode node = parseExpr();
	        pn.addExpr(node);
	    }
		
		return pn;
	}
	
	// Parse multiple expressions
	public SyntaxNode parseExpr() throws MyException {
		Token token = currentToken();
		
		switch (token.getType()) {
	        case WHILE:
	            return parseWhile();
	        case IF:
	            return parseIf();
	        case PRINT:
	            return parsePrint();
	        case SIN:
	            return parseSin();
	        case COS:
	            return parseCos();
	        case TAN:
	            return parseTan();
	        case KW:
	            return parseKW();
	        case BREAK:
	            advanceToken();
	            return new BreakNode();
	        case CONTINUE:
	            advanceToken();
	            return new ContinueNode();
	        case BACK:
	            advanceToken();
	            return new BackNode();
	        case RETURN:
	            return parseReturn();
	        case ID:
	            return parseAssign();  // New method: can be assign or expression
	        default:
	            throw new MyException("Unexpected token: " + token + "\trow:" + token.getRow() + 
	            		"\tcol:" + token.getCol());
		}
	}
	
	public SyntaxNode parseWhile() throws MyException {
		advanceToken();				// Move past WHILE
		
		expect(TokenType.LPAREN);	// Expect '(' after WHILE
		advanceToken();				// Move past '('
		
		SyntaxNode condition = parseArithOrStringOrBoolExpr();
		
		expect(TokenType.RPAREN); 	// Expect ')' after condition
		advanceToken();				// Move past ')'
		
		SyntaxNode body = parseBlock();	
		
		return new WhileNode(condition, body);
	}
	
	public SyntaxNode parseIf() throws MyException {
		advanceToken();				// Move past IF
		
		expect(TokenType.LPAREN);	// Expect '(' after IF
		advanceToken();				// Move past '('
		
		SyntaxNode condition = parseArithOrStringOrBoolExpr();
		
		expect(TokenType.RPAREN);	// Expect ')' after condition
		advanceToken();				// Move past ')'
		
		SyntaxNode thenBlock = parseBlock();
		
		// has else sentence
		if (currentToken().getType() == TokenType.ELSE) {
			advanceToken();		// Move past ELSE
			SyntaxNode elseBlock = parseBlock();
			return new IfNode(condition, thenBlock, elseBlock);
		}
		
		return new IfNode(condition, thenBlock);
	}
	
	public SyntaxNode parsePrint() throws MyException {
		advanceToken(); 			// Move past PRINT
		
		expect(TokenType.LPAREN);	// Expect '(' after PRINT
		advanceToken();				// Move past '('
		
		SyntaxNode expression = parseArithOrStringOrBoolExpr();
		
		expect(TokenType.RPAREN);	// Expect ')' after expression 
		advanceToken();
		
		return new PrintNode(expression);
	}
	
	public SyntaxNode parseSin() throws MyException {
		advanceToken();				// Move past SIN
		
		expect(TokenType.LPAREN); 	// Expect '(' after SIN
		advanceToken();
		
		SyntaxNode expression = parseArithOrStringOrBoolExpr();
		
		expect(TokenType.RPAREN);	// Expect ')' after expression
		advanceToken();
		
		return new SinNode(expression);
	}
	
	public SyntaxNode parseCos() throws MyException {
		advanceToken(); 			// Move past COS
		
		expect(TokenType.LPAREN); 	// Expect '(' after COS
		advanceToken();
		
		SyntaxNode expression = parseArithOrStringOrBoolExpr();
		
		expect(TokenType.RPAREN);	// Expect ')' after expression
		advanceToken();
		
		return new CosNode(expression);
	}
	
	public SyntaxNode parseTan() throws MyException {
		advanceToken();				// Move past TAN
		
		expect(TokenType.LPAREN);	// Expect '(' after TAN
		advanceToken();
		
		SyntaxNode expression = parseArithOrStringOrBoolExpr();
		
		expect(TokenType.RPAREN); 	// Expect ')' after expression
		advanceToken();
		
		return new TanNode(expression);
	}
	
	public SyntaxNode parseKW() throws MyException {
		Token keyword = currentToken();
		advanceToken();	// Move past keyword
		
		DeclareNode dn = new DeclareNode(keyword.getValue());
		
		while (true) {
			Token token = currentToken();
			
			if (token.getType() == TokenType.ID) {
				Token nextToken = peekNextToken();	// Get the token after ID
				
				// Assign
				if (nextToken.getType() == TokenType.ASSIGN) {
					dn.add(parseAssign());	// handles Assign
				} else {
					dn.add(new VariableNode(token.getValue()));
					advanceToken();	// Move past ID
				}
			} else {	// The token after Keyword must be ID
				throw new MyException("Unexpected Token: " + token.getValue() + "row:" + token.getRow() + "col:" + token.getCol());
			}
			
			// Check if we've encountered the end of the declarations (e.g., no more ID)
	        Token nextToken = currentToken();
	        if (nextToken.getType() != TokenType.ID) {
	            break;  // Exit the loop when there are no more IDs to process
	        }
		}
		
		return dn;
	}
	
	public SyntaxNode parseReturn() throws MyException {
		advanceToken();		// Move past RETURN
		
		expect(TokenType.LPAREN);	// Expect '(' after RETURN
		advanceToken();				// Move past '('
		
		SyntaxNode expression = parseArithOrStringOrBoolExpr();
		
		expect(TokenType.RPAREN);	// Expect ')' after expression 
		advanceToken();
		
		return new ReturnNode(expression);
	}
	
	public SyntaxNode parseAssign() throws MyException {
		Token token = currentToken();	// token type is ID
		advanceToken();	// Move past ID to Assign
		
		// currentToken must Assign
		if (currentToken().getType() != TokenType.ASSIGN) {
			throw new MyException("Unexpected token: " + currentToken().getType() + "\trow:" + 
					currentToken().getRow() + "\tcol:" + currentToken().getCol());
		}
		advanceToken();	// Move past '='
		
		SyntaxNode expression = parseArithOrStringOrBoolExpr();
		
		return new AssignNode(token.getValue(), expression);
	}
	
	public SyntaxNode parseBlock() throws MyException {
		expect(TokenType.LCB);	// Expect '{' at the beginning of block
		advanceToken();
		
		ExprListNode blockList = new ExprListNode();
		
		while (currentToken().getType() != TokenType.RCB) {
	        SyntaxNode node = parseExpr();
	        blockList.addExpr(node);
	    }

		advanceToken();	// Move pass the end '}'
		
		return blockList;
	}
	
	// arithOrStringOrBoolExpr -> termA((OR)termA)*
	public SyntaxNode parseArithOrStringOrBoolExpr() throws MyException {
		SyntaxNode term = parseTermA();  // Start parsing termA
//		advanceToken(); // Move to the next token after parsing termA
		
	    // Now handle OR between terms (termA OR termA)
	    while (currentToken().getType() == TokenType.OR) {
	        Token operator = currentToken();  // Capture the OR token
	        advanceToken();  // Move past OR
	        SyntaxNode rightTerm = parseTermA();  // Parse the right-hand side
	        term = new BinaryOpNode(operator.getValue(), term, rightTerm);  // Combine terms
	    }

	    return term;
	}
	
	// termA -> termB((AND)termB)*
	public SyntaxNode parseTermA() throws MyException {
	    SyntaxNode term = parseTermB();  // Start parsing termB
//	    advanceToken(); // Move to the next token after parsing termB
	    
	    // Handle AND between terms (termB AND termB)
	    while (currentToken().getType() == TokenType.AND) {
	        Token operator = currentToken();  // Capture the AND token
	        advanceToken();  // Move past AND
	        SyntaxNode rightTerm = parseTermB();  // Parse the right-hand side
	        term = new BinaryOpNode(operator.getValue(), term, rightTerm);  // Combine terms
	    }
	
	    return term;
	}
	
	// termB -> termC((COMP)termC)*
	public SyntaxNode parseTermB() throws MyException {
	    SyntaxNode term = parseTermC();  // Start parsing termC
//	    advanceToken();  // Move to the next token after parsing termC

	    // Handle comparisons (e.g., <, >, ==, !=)
	    while (currentToken().getType() == TokenType.COMP) {
	        Token operator = currentToken();  // Capture comparison operator
	        advanceToken();  // Move past comparison operator
	        SyntaxNode rightTerm = parseTermC();  // Parse the right-hand side
	        term = new BinaryOpNode(operator.getValue(), term, rightTerm);  // Combine terms
	    }

	    return term;
	}
	
	// termC -> termD((PLUS | MINUS)termD)*
	public SyntaxNode parseTermC() throws MyException {
	    SyntaxNode term = parseTermD();  // Start parsing termD
//	    advanceToken();  // Move to the next token after parsing termD

	    // Handle addition/subtraction
	    while (currentToken().getType() == TokenType.PLUS || currentToken().getType() == TokenType.MINUS) {
	        Token operator = currentToken();  // Capture the addition or subtraction operator
	        advanceToken();  // Move past the operator
	        SyntaxNode rightTerm = parseTermD();  // Parse the right-hand side
	        term = new BinaryOpNode(operator.getValue(), term, rightTerm);  // Combine terms
	    }

	    return term;
	}
	
	// termD -> termE((MUL | DIV | MODULAR)termE)*
	public SyntaxNode parseTermD() throws MyException {
	    SyntaxNode term = parseTermE();  // Start parsing termE
//	    advanceToken();  // Move to the next token after parsing termE

	    // Handle multiplication/division/modular
	    while (currentToken().getType() == TokenType.MUL || currentToken().getType() == TokenType.DIV || currentToken().getType() == TokenType.MODULAR) {
	        Token operator = currentToken();  // Capture the operator
	        advanceToken();  // Move past the operator
	        SyntaxNode rightTerm = parseTermE();  // Parse the right-hand side
	        term = new BinaryOpNode(operator.getValue(), term, rightTerm);  // Combine terms
	    }

	    return term;
	}
	
	// termE -> atom((EXP)atom)*
	public SyntaxNode parseTermE() throws MyException {
	    // Start by parsing the first atom
	    SyntaxNode term = parseAtom();

	    // Handle exponentiation (EXP) between terms (atom EXP atom)
	    while (currentToken().getType() == TokenType.EXP) {
	        Token operator = currentToken();  // Capture EXP operator
	        advanceToken();  // Move past EXP
	        SyntaxNode rightTerm = parseAtom();  // Parse the right-hand side atom
	        term = new BinaryOpNode(operator.getValue(), term, rightTerm);  // Combine terms with EXP operator
	    }

	    return term;
	}
	
	/* atom -> INT | FLOAT | STRING | BOOL
         	-> (PLUS | MINUS | NOT)atom
         	-> LPAREN arithOrStingOrBoolExpr RPAREN */
	public SyntaxNode parseAtom() throws MyException {
		Token token = currentToken();
		
		// atom -> INT | FLOAT | STRING | BOOL
		if (token.getType() == TokenType.INT) {
			return new NumberNode(Integer.parseInt(token.getValue()));
		} else if (token.getType() == TokenType.FLOAT) {
			return new NumberNode(Float.parseFloat(token.getValue()));
		} else if (token.getType() == TokenType.STRING) {
			return new StringNode(token.getValue());
		} else if (token.getType() == TokenType.BOOL) {
			return new BoolNode(Boolean.parseBoolean(token.getValue()));
		}
		
		// atom -> (PLUS | MINUS | NOT)atom
		else if (token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS || token.getType() == TokenType.NOT) {
	        Token operator = currentToken();  // Capture the operator
	        advanceToken();  // Move past the operator
	        SyntaxNode atom = parseAtom();  // Parse the atom (expression)
	        return new UnaryOpNode(operator.getValue(), atom);  // Create a UnaryOpNode
	    }
		
		// atom -> LPAREN arithOrStingOrBoolExpr RPAREN
		else if (token.getType() == TokenType.LPAREN) {
	        advanceToken();  // Move past '('
	        SyntaxNode expr = parseArithOrStringOrBoolExpr();  // Parse the expression inside parentheses
	        expect(TokenType.RPAREN);  // Ensure the closing ')'
	        advanceToken();  // Move past ')'
	        return expr;
	    }
		
		// If none of the above, throw an exception
	    throw new MyException("Invalid token: " + token.getType() + "\trow:" + token.getRow() + 
	    					"\tcol:" + token.getCol());
	}
}




