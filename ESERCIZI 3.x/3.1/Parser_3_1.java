import java.io.*;

public class Parser_3_1 {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser_3_1(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
		throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
		if (look.tag == t) {
	    	if (look.tag != Tag.EOF) move();
		} else error("syntax error");
    }

    public void start() {
    	if(look.tag == '(' || look.tag == Tag.NUM){
			expr();
			match(Tag.EOF);
		}
		else{
			error("error in start");
		}
    }

    private void expr() {
    	if(look.tag == '(' || look.tag == Tag.NUM){
    		term();
    		exprp();
    	}
    	else{
    		error("error in expr");
    	}
    }

    private void exprp() {
		switch (look.tag) {
			case '+':
				match('+');
				term();
				exprp();
				break;
			case '-':
				match('-');
				term();
				exprp();
				break;
			case ')':
				break;
			case Tag.EOF:
				break;
			default:
				error("error in exprp");
		}
    }

    private void term() {
        if(look.tag == '(' || look.tag == Tag.NUM){
    		fact();
    		termp();
    	}
    	else{
    		error("error in term");
    	}
    }

    private void termp() {
        switch (look.tag) {
			case '*':
				match('*');
				fact();
				termp();
				break;
			case '/':
				match('/');
				fact();
				termp();
				break;
			case '+':
				break;
			case '-':
				break;
			case ')':
				break;
			case Tag.EOF:
				break;
			default:
				error("error in termp");
		}
    }

    private void fact() {
        switch (look.tag) {
			case '(':
				match('(');
				expr();
				match(')');
				break;
			case Tag.NUM:
				match(Tag.NUM);
				break;
			default:
				error("error in fact");
		}
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./Test.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser_3_1 parser = new Parser_3_1(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}