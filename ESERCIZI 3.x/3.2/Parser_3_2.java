import java.io.*;

public class Parser_3_2 {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser_3_2(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
		throw new Error("near line " + lex.line + ": " + s + " look: " + look.tag);
    }

    void match(int t) {
		if (look.tag == t) {
	    	if (look.tag != Tag.EOF) 
	    		move();
		}
		else error("syntax error match");
    }

    public void prog() { // <prog> -> <statlist> EOF
    	if(look.tag == Tag.ASSIGN || look.tag == Tag.PRINT || look.tag == Tag.READ || look.tag == Tag.WHILE || look.tag == Tag.IF || look.tag == '{'){
			statlist();
			match(Tag.EOF);
		}
		else{
			error("error in prog");
		}
    }

    private void statlist() { // <statlist> -> <stat> <statlistp> 
    	if(look.tag == Tag.ASSIGN || look.tag == Tag.PRINT || look.tag == Tag.READ || look.tag == Tag.WHILE || look.tag == Tag.IF || look.tag == '{'){
    		stat();
    		statlistp();
    	}
    	else{
    		error("error in statlist");
    	}
    }

    private void statlistp() { // <statlistp> -> ; <stat> <statlistp> | ε
		switch (look.tag) {
			case ';':
				match(';');
				stat();
				statlistp();
				break;
			case '}':
				break;
			case Tag.EOF:
				break;
			default:
				error("error in statlistp");
		}
    }

    private void stat() { /* <stat> -> ASSIGN <expr> TO <idlist> | PRINT(<exprlist>) | READ(<idlist>) | WHILE(<bexpr>) <stat> |
    								 | IF(<bexpr>) <stat> END | IF(<bexpr>) <stat> ELSE <stat> END | {<statlist>}   */
		switch (look.tag) {
			case Tag.ASSIGN:
				match(Tag.ASSIGN);
				expr();
				match(Tag.TO);
				idlist();
				break;
			case Tag.PRINT:
				match(Tag.PRINT);
				match('(');
				exprlist();
				match(')');
				break;
			case Tag.READ:
				match(Tag.READ);
				match('(');
				idlist();
				match(')');
				break;
			case Tag.WHILE:
				match(Tag.WHILE);
				match('(');
				bexpr();
				match(')');
				stat();
				break;
			case Tag.IF:
				match(Tag.IF);
				match('(');
				bexpr();
				match(')');
				stat();
				if(look.tag == Tag.ELSE) {
					match(Tag.ELSE);
					stat();
				}
				match(Tag.END);
				break;
			case '{':
				match('{');
				statlist();
				match('}');
				break;
			default:
				error("error in stat");
		}
    }

    private void idlist() { //<idlist> -> ID <idlistp>
        if(look.tag == Tag.ID){
    		match(Tag.ID);
    		idlistp();
    	}
    	else{
    		error("error in idlist");
    	}
    }

    private void idlistp() { //<idlistp> -> , ID <idlistp> | ε 
        if(look.tag == ','){
        	match(',');
    		match(Tag.ID);
    		idlistp();
    	}
    	else if(look.tag == Tag.EOF || look.tag == ')' || look.tag == '}' || look.tag == ';' || look.tag == Tag.ELSE || look.tag == Tag.END) {
    	}
    	else{
    		error("error in idlistp");
    	}
    }

    private void bexpr() { //<bexpr> -> RELOP <expr> <expr> 
        if(look.tag == Tag.RELOP){
        	match(Tag.RELOP);
    		expr();
    		expr();
    	}
    	else{
    		error("error in bexpr");
    	}
    }

    private void expr() { // <expr> -> +(<exprlist>) | - <expr> <expr> | *(<exprlist>) | / <expr> <expr> | NUM | ID
		switch (look.tag) {
			case '+':
				match('+');
				match('(');
				exprlist();
				match(')');
				break;
			case '-':
				match('-');
				expr();
				expr();
				break;
			case '*':
				match('*');
				match('(');
				exprlist();
				match(')');
				break;
			case '/':
				match('/');
				expr();
				expr();
				break;
			case Tag.NUM:
				match(Tag.NUM);
				break;
			case Tag.ID:
				match(Tag.ID);
				break;
			default:
				error("error in expr");
		}
    }

    private void exprlist() { //<exprlist> -> <expr> <exprlistp> 
        if(look.tag == '+' || look.tag == '-' || look.tag == '*' || look.tag == '/' || look.tag == Tag.NUM || look.tag == Tag.ID){
    		expr();
    		exprlistp();
    	}
    	else{
    		error("error in exprlist");
    	}
    }

    private void exprlistp() { //<exprlistp> -> , <expr> <exprlistp> | ε 
        if(look.tag == ','){
        	match(',');
    		expr();
    		exprlistp();
    	}
    	else if(look.tag == ')'){
    	}
    	else{
    		error("error in exprlistp");
    	}
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./Test.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser_3_2 parser = new Parser_3_2(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}