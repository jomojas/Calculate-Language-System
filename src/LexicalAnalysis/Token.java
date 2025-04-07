package LexicalAnalysis;

public class Token {
	private TokenType type;
	private int row;
	private int col;
	private String value;
	
	public Token(TokenType type, int row, int col) {
		this.type = type;
		this.row = row;
		this.col = col;
	}
	
	public Token(TokenType type, int row, int col,String value) {
		this(type, row, col);
		this.value = value;
	}
}
