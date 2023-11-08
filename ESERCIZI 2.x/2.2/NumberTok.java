public class NumberTok extends Token {
	// ... completare ...
	public int lexeme;
    public NumberTok(int tag, int n) { super(tag); lexeme=n; }
    public String toString() { return "<" + tag + ", " + lexeme + ">"; }
}
