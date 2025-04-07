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
		this.value = null;
	}
	
	public Token(TokenType type, int row, int col,String value) {
		this(type, row, col);
		this.value = value;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public String getValue() {
		return value;
	}
}
