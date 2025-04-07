package LexicalAnalysis;

import java.util.ArrayList;
import MyException.MyException;

public class Tokenizer {
	private String codeString;
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private ArrayList<String> lines = new ArrayList<String>();
	public int pos = 0;
	public int col = 0;
	
	public Tokenizer(String code) throws MyException {
		this.codeString = code;
		getLines();
		getTokens();
	}
	
	public ArrayList<Token> handOnTokens() {
		return tokens;
	}
	
	// Divide code to separate lines
	public void getLines() {
		boolean insideString = false;
		StringBuffer line  = new StringBuffer();
		for (int i = 0; i < codeString.length(); i++) {
			// Set insideString to true when enter a string
			if (codeString.charAt(i) == '"' && codeString.charAt(i - 1) != '\\') {
				insideString = !insideString; // Flip insideString
			}
			// Meet '\n' outside string, add current line to ArrayList<String>
			if (insideString == false && codeString.charAt(i) == '\n') {
				lines.add(line.toString());
				line.setLength(0);
				continue;
			}
			line.append(codeString.charAt(i));
			// If current index is the last character of codeString, add the last line to lines
			if (i == codeString.length() - 1) {
				lines.add(line.toString());
				line.setLength(0);
				break;
			}
		}
	}
	
	// Divide lines to separate tokens
	public void getTokens() throws MyException {
		int rowIndex = 0;
		for (String lineString : lines) {
			rowIndex++;
			while (pos < lineString.length()) {
	            char current = lineString.charAt(pos);

	            // Skip whitespace
	            if (Character.isWhitespace(current)) {
	            	// Tab \t
	            	if (current == '\t') {
	            		pos++;
	            		col += 4;
	            	}
	            	// Single space
	            	if (current == ' ') {
	            		pos++;
	            		col++;
	            	}
	                continue;
	            }

	            // Handle numbers (integers and floats)
	            if (Character.isDigit(current)) {
	                tokens.add(scanNumber(rowIndex, lineString));
	                continue;
	            }

	            // Handle identifiers (variables, keywords)
	            if (Character.isLetter(current)) {
	                tokens.add(scanIdentifier(rowIndex, lineString));
	                continue;
	            }

	            // Handle multi-character operators like >=, <=, ==, !=
	            if (current == '>' || current == '<' || current == '=' || current == '!') {
	                tokens.add(scanComparison(rowIndex, lineString));
	                continue;
	            }

	            // Handle string literals
	            if (current == '"') {
	                tokens.add(scanString(rowIndex, lineString));
	                continue;
	            }

	            // Handle DIV and comment('/')
	            if (current == '/') {
	            	scanSlash(rowIndex, lineString);
	            	continue;
	            }
	            
	            // Handle single-character tokens
	            switch (current) {
	                case '+': tokens.add(new Token(TokenType.PLUS, rowIndex, col + 1)); break;
	                case '-': tokens.add(new Token(TokenType.MINUS, rowIndex, col + 1)); break;
	                case '*': tokens.add(new Token(TokenType.MUL, rowIndex, col + 1)); break;
//	                case '/': tokens.add(new Token(TokenType.DIV, "/")); break;
	                case '^': tokens.add(new Token(TokenType.EXP, rowIndex, col + 1)); break;
	                case '&': tokens.add(new Token(TokenType.AND, rowIndex, col + 1)); break;
	                case '|': tokens.add(new Token(TokenType.OR, rowIndex, col + 1)); break;
	                case '!': tokens.add(new Token(TokenType.NOT, rowIndex, col + 1)); break;
	                case '(': tokens.add(new Token(TokenType.LPAREN, rowIndex, col + 1)); break;
	                case ')': tokens.add(new Token(TokenType.RPAREN, rowIndex, col + 1)); break;
	                case '{': tokens.add(new Token(TokenType.LCB, rowIndex, col + 1)); break;
	                case '}': tokens.add(new Token(TokenType.RCB, rowIndex, col + 1)); break;
//	                case '=': tokens.add(new Token(TokenType.ASSIGN, "=")); break;
//	                case '#': tokens.add(new Token(TokenType.DF, "#")); break;
	                default:
	                    throw new RuntimeException("Unexpected character: " + current);
	            }
	            pos++; // Move to next character
	            col++;
			}
			pos = 0;
			col = 0;
		}
	}
	
	private Token scanNumber(int rowIndex, String currentLine) throws MyException {
		StringBuilder number = new StringBuilder();
	    boolean isFloat = false;
	    boolean hasDigit = false;

	    while (pos < currentLine.length() && (Character.isDigit(currentLine.charAt(pos)) || currentLine.charAt(pos) == '.')) {
	        char currentChar = currentLine.charAt(pos);

	        if (currentChar == '.') {
	            if (isFloat) {
	                throw new MyException("Row:" + rowIndex + " Col:" + (col + 1) + "	Invalid number format: multiple decimal points in '" + number + "'");
	            }
	            isFloat = true;
	        } else {
	            hasDigit = true;  // At least one digit must be present
	        }

	        number.append(currentChar);
	        pos++;
	        col++;
	    }

	    // If the number ends with a '.', append '0' (convert "12." -> "12.0")
	    if (number.charAt(number.length() - 1) == '.') {
	        number.append('0');
	    }

	    // If no digit was found (e.g., just a dot), it's invalid
	    if (!hasDigit) {
	        throw new MyException("Row:" + rowIndex + "	Col:" + (col + 1) + "	Invalid number format: missing digits in '" + number + "'");
	    }

	    // Determine the token type
	    TokenType type = isFloat ? TokenType.FLOAT : TokenType.INT;

	    return new Token(type, rowIndex, col + 1, number.toString());
	}
	
	private Token scanIdentifier(int rowIndex, String currentLine) throws MyException {
		int start = pos;
		while (pos < currentLine.length()) {
	        char c = currentLine.charAt(pos);

	        // Valid identifier character
	        if (Character.isLetterOrDigit(c) || c == '_') {
	            pos++;
	            col++;
	        }
	        // If it's a character that can legally end an identifier (whitespace, operator, etc.), stop scanning
	        else if (Character.isWhitespace(c) || "(){}[],;:+-*/%&|=!<>".indexOf(c) != -1) {
	            break;
	        }
	        // If it's an invalid character in an identifier, throw an error
	        else {
	            throw new MyException("Row:" + rowIndex + " Col:" + (col + 1) + " — Unexpected character '" + c + "' in identifier");
	        }
	    }

        String value = currentLine.substring(start, pos);

        // Match keywords
        switch (value) {
            case "int": case "float": case "string": case "bool":
                return new Token(TokenType.KW, rowIndex, col, value);
            case "if": return new Token(TokenType.IF, rowIndex, col);
            case "else": return new Token(TokenType.ELSE, rowIndex, col);
            case "while": return new Token(TokenType.WHILE, rowIndex, col);
            case "break": return new Token(TokenType.BREAK, rowIndex, col);
            case "return": return new Token(TokenType.RETURN, rowIndex, col);
            case "back": return new Token(TokenType.BACK, rowIndex, col);
            case "continue": return new Token(TokenType.CONTINUE, rowIndex, col);
            case "print": return new Token(TokenType.PRINT, rowIndex, col);
            case "readNum": return new Token(TokenType.READNUM, rowIndex, col);
            case "sin": return new Token(TokenType.SIN, rowIndex, col);
            case "cos": return new Token(TokenType.COS, rowIndex, col);
            case "tan": return new Token(TokenType.TAN, rowIndex, col);
            case "true": case "false": return new Token(TokenType.BOOL, rowIndex, col, value);
            default:
                return new Token(TokenType.ID, rowIndex , col, value);
        }
	}
	
	private Token scanComparison(int rowIndex, String currentLine) {
		  	char firstChar = currentLine.charAt(pos);
		    pos++; // Move to the next character
		    col++;

		    // Check for two-character comparators (<=, >=, !=, ==)
		    if (pos < currentLine.length()) {
		        char nextChar = currentLine.charAt(pos);
		        if ((firstChar == '<' || firstChar == '>' || firstChar == '!' || firstChar == '=') && nextChar == '=') {
		            pos++; // Move past '='
		            return new Token(TokenType.COMP, rowIndex, col + 1, firstChar + "=");
		        }
		    }

		    // If it's a single '=', it must be an assignment token
		    if (firstChar == '=') {
		        return new Token(TokenType.ASSIGN, rowIndex, col + 1);
		    }

		    // Otherwise, return a single-character comparator (< or >)
		    return new Token(TokenType.COMP, rowIndex, col + 1, String.valueOf(firstChar));
	}
	
	private Token scanString(int rowIndex, String currentLine) throws MyException {
		StringBuilder sb = new StringBuilder();
	    pos++; // Skip the opening quote (we assume input[pos] == '"')
	    col++;

	    while (pos < currentLine.length()) {
	        char current = currentLine.charAt(pos);

	        // Handle escape sequences
	        if (current == '\\') {
	            pos++; // Move to escaped character
	            col++;
	            if (pos >= currentLine.length()) {
	                throw new MyException("Row:" + rowIndex + "	Col:" +	(col + 1) + "	Unexpected end of input after escape character");
	            }

	            char escapedChar = currentLine.charAt(pos);
	            switch (escapedChar) {
	                case 'n': sb.append('\n'); break;
	                case 't': sb.append('\t'); break;
	                case '"': sb.append('"'); break;
	                case '\\': sb.append('\\'); break;
	                default:
	                    throw new MyException("Row:" + rowIndex + "	Col:" +	(col + 1) + "	Invalid escape sequence: \\" + escapedChar);
	            }
	        } 
	        // Handle closing quote
	        else if (current == '"') {
	            pos++; // Move past closing quote
	            col++;
	            return new Token(TokenType.STRING, rowIndex, col + 1, sb.toString());
	        } 
	        // Append normal characters
	        else {
	            sb.append(current);
	        }
	        pos++; // Move to next character
	        col++;
	    }

	    // If we reach here, the string was not closed
	    throw new MyException("Row:" + rowIndex + "	Col:" +	(col + 1) + "	Unterminated string literal");
	}
	
	private void scanSlash(int rowIndex, String currentLine) {
		char secondChar = currentLine.charAt(pos + 1);
		if (secondChar != '/') {
			tokens.add(new Token(TokenType.DIV, rowIndex, col + 1));
		}
		// Handle comment
		else {
			while (pos < currentLine.length()) {
				pos++;
			}
		}
	}
}




