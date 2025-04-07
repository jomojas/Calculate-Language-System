package LexicalAnalysis;

public enum TokenType {
	INT, 
	FLOAT, 
	BOOL,
	STRING,
	PLUS, 
	MINUS, 
	MUL, 
	DIV,
	EXP,
	AND,
	OR,
	NOT,
	LPAREN,	// (
	RPAREN,	// )
	LCB,	// {
	RCB,	// }
	COMP,	// <, >, <=, >=, ==, !=
	ASSIGN,	// =
	IF,
	ELSE,
	WHILE,
	BREAK,
	CONTINUE,
	RETURN,
	PRINT,
	READNUM,
	COS,
	SIN,
	TAN,
	ID,
	KW,	// int, float, bool, string
}
