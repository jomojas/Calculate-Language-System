package LexicalAnalysis;

public enum TokenType {
	INT, // 12
	FLOAT, //12.3
	BOOL,	// true false
	STRING,	// "hello"
	PLUS, // +
	MINUS, // -
	MUL, // *
	DIV,	// /
	EXP,	// ^
	AND,	// &
	OR,	// |
	NOT,	// !
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
	BACK,
	CONTINUE,
	RETURN,
	PRINT,
	READNUM,
	COS,
	SIN,
	TAN,
	ID,
	KW,	// int, float, bool, string
	EOF, // The end of file
}
