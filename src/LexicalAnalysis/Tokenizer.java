package LexicalAnalysis;

import java.util.ArrayList;

public class Tokenizer {
	private String codeString;
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private ArrayList<String> lines = new ArrayList<String>();
	
	public Tokenizer(String code) {
		this.codeString = code;
		getLines();
		getTokens();
	}
	
	// Divide code to separate lines
	public void getLines() {
		
	}
	
	// Divide lines to separate tokens
	public void getTokens() {
		
	}
	
	public ArrayList<Token> handOnTokens() {
		return tokens;
	}
}
