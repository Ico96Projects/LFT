import java.io.*; 

public class Valutatore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(Lexer l, BufferedReader br) { 
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

    public void start() { //<start> -> <expr> EOF {print(expr.val)}
	   int expr_val;
       if(look.tag == '(' || look.tag == Tag.NUM){
            expr_val = expr();
            match(Tag.EOF);
            System.out.println(expr_val);
        }
        else
            error("error in start");
    }

    private int expr() { //<expr> -> <term> {exprp.i=term.val} <exprp> {expr.val=exprp.val}
	   int term_val, exprp_val = 0;
       if(look.tag == '(' || look.tag == Tag.NUM){
            term_val = term();
            exprp_val = exprp(term_val);
        }
        else
            error("error in expr");
        return exprp_val;
    }

    private int exprp(int exprp_i) {
        int term_val, exprp_val = 0;
        switch (look.tag) {
            case '+': //<exprp> -> + <term> {exprp1.i=exprp.i+term.val} <exprp1> {exprp.val=exprp1.val}
                match('+');
                term_val = term();
                exprp_val = exprp(exprp_i + term_val);
                break;
            case '-': //<exprp> -> - <term> {exprp1.i=exprp.i-term.val} <exprp1> {exprp.val=exprp1.val}
                match('-');
                term_val = term();
                exprp_val = exprp(exprp_i - term_val);
                break;
            case ')': //<exprp> -> ε {exprp.val=exprp.i}
                exprp_val = exprp_i;
                break;
            case Tag.EOF: //<exprp> -> ε {exprp.val=exprp.i}
                exprp_val = exprp_i;
                break;
            default:
                error("error in exprp");
        }
        return exprp_val;
    }

    private int term() { //<term> -> <fact> {termp.i=fact.val} <termp> {term.val=termp.val}
        int fact_val, termp_val = 0;
        if(look.tag == '(' || look.tag == Tag.NUM){
            fact_val = fact();
            termp_val = termp(fact_val);
        }
        else
            error("error in term");
        return termp_val;
    }
    
    private int termp(int termp_i) {
        int fact_val, termp_val = 0;
        switch (look.tag) {
            case '*': //<termp> -> * <fact> {termp1.i=termp.i*fact.val} <termp1> {termp.val=termp1.val}
                match('*');
                fact_val = fact();
                termp_val = termp(termp_i * fact_val);
                break;
            case '/': //<termp> -> / <fact> {termp1.i=termp.i/fact.val} <termp1> {termp.val=termp1.val}
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i / fact_val);
                break;
            case '+': //<termp> -> ε {termp.val=termp.i}
                termp_val = termp_i;
                break;
            case '-': //<termp> -> ε {termp.val=termp.i}
                termp_val = termp_i;
                break;
            case ')': //<termp> -> ε {termp.val=termp.i}
                termp_val = termp_i;
                break;
            case Tag.EOF: //<termp> -> ε {termp.val=termp.i}
                termp_val = termp_i;
                break;
            default:
                error("error in termp");
        }
        return termp_val;
    }
    
    private int fact() {
        int fact_val = 0;
        switch (look.tag) {
            case '(': //<fact> -> (<expr>) {fact.val=expr.val}
                match('(');
                fact_val = expr();
                match(')');
                break;
            case Tag.NUM: //<fact> -> NUM {fact.val=NUM.value}
                fact_val = ((NumberTok)look).lexeme;/*Leggere numero che arriva da lexer*/
                match(Tag.NUM);
                break;
            default:
                error("error in fact");
        }
        return fact_val;
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./Test.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}