package LexicalAnalysis;

import java.util.*;

import MyException.MyException;

public class SeparateToken {
	public ArrayList<Token> tokens = new ArrayList<Token>();
	
	public SeparateToken(String codeString) throws MyException {
		Tokenizer tokenizer = new Tokenizer(codeString);
		this.tokens = tokenizer.handOnTokens();
	}
	
	public ArrayList<Token> getTokens() {
		return tokens;
	}
}
