import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count = 0;

    public Translator(Lexer l, BufferedReader br) {
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
            int lnext_prog = code.newLabel();
            statlist(lnext_prog);
            code.emitLabel(lnext_prog);
            match(Tag.EOF);
            try {
                code.toJasmin();
            }
            catch(java.io.IOException e) {
                System.out.println("IO error\n");
            }
        }
        else{
            error("error in prog");
        }
    }

    private void statlist(int lnext) { // <statlist> -> <stat> <statlistp> 
        if(look.tag == Tag.ASSIGN || look.tag == Tag.PRINT || look.tag == Tag.READ || look.tag == Tag.WHILE || look.tag == Tag.IF || look.tag == '{'){
            int lnext_statlist = code.newLabel();
            stat(lnext_statlist);
            code.emitLabel(lnext_statlist);
            statlistp(lnext);
        }
        else{
            error("error in statlist");
        }
    }

    private void statlistp(int lnext) { // <statlistp> -> ; <stat> <statlistp> | ε
        switch (look.tag) {
            case ';':
                match(';');
                int lnext_statlistp = code.newLabel();
                stat(lnext_statlistp);
                code.emitLabel(lnext_statlistp);
                statlistp(lnext);
                break;
            case '}':
                break;
            case Tag.EOF:
                break;
            default:
                error("error in statlistp");
        }
    }

    private void stat(int lnext) { /* <stat> -> ASSIGN <expr> TO <idlist> | PRINT(<exprlist>) | READ(<idlist>) | WHILE(<bexpr>) <stat> |
                                     | IF(<bexpr>) <stat> END | IF(<bexpr>) <stat> ELSE <stat> END | {<statlist>}   */
        int id_addr;
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
                OpCode oc = OpCode.invokestatic;
                exprlist_1(new Instruction(OpCode.invokestatic,1), oc);
                match(')');
                break;
            case Tag.READ:
                match(Tag.READ);
                match('(');
                code.emit(OpCode.invokestatic,0);
                idlist();
                match(')');
                break;
            case Tag.WHILE:
                match(Tag.WHILE);
                int wh_cond = code.newLabel();
                int wh_body = code.newLabel();
                int wh_jump = code.newLabel();
                code.emitLabel(wh_cond);
                match('(');
                bexpr(wh_body, wh_jump);
                match(')');
                code.emitLabel(wh_body);
                stat(wh_body);
                code.emit(OpCode.GOto, wh_cond);
                code.emitLabel(wh_jump);
                break;
            case Tag.IF:
                match(Tag.IF);
                int true_cond = code.newLabel();
                int false_cond = code.newLabel();
                int jump = code.newLabel();
                match('(');
                bexpr(true_cond, false_cond);
                match(')');
                code.emitLabel(true_cond);
                stat(true_cond);
                code.emit(OpCode.GOto, jump);
                code.emitLabel(false_cond);
                if(look.tag == Tag.ELSE) {
                    match(Tag.ELSE);
                    stat(false_cond);
                    match(Tag.END);
                    code.emitLabel(jump);
                    break;
                }
                match(Tag.END);
                code.emitLabel(jump);
                break;
            case '{':
                match('{');
                statlist(lnext);
                match('}');
                break;
            default:
                error("error in stat");
        }
    }

    private void idlist() { //<idlist> -> ID <idlistp>
        if(look.tag == Tag.ID){
            int id_addr = st.lookupAddress(((Word)look).lexeme);
            if(id_addr == -1) {
                id_addr = count;
                st.insert(((Word)look).lexeme, count++);
            }
            match(Tag.ID);
            code.emit(OpCode.istore, id_addr);
            idlistp(st);
        }
        else{
            error("error in idlist");
        }
    }

    private void idlistp(SymbolTable st) { //<idlistp> -> , ID <idlistp> | ε 
        if(look.tag == ','){
            match(',');
            code.emit(OpCode.invokestatic,0);
            int id_addr = st.lookupAddress(((Word)look).lexeme);
            if(id_addr == -1) {
                id_addr = count;
                st.insert(((Word)look).lexeme, count++);
            }
            match(Tag.ID);
            code.emit(OpCode.istore, id_addr);
            idlistp(st);
        }
        else if(look.tag == Tag.EOF || look.tag == ')' || look.tag == '}' || look.tag == ';' || look.tag == Tag.ELSE || look.tag == Tag.END) {
        }
        else{
            error("error in idlistp");
        }
    }

    private void bexpr(int true_label, int false_label) { //<bexpr> -> RELOP <expr> <expr> 
        if(look.tag == Tag.RELOP){
            String str = ((Word)look).lexeme;
            match(Tag.RELOP);
            expr();
            expr();
            switch(str) {
                case "==":
                    code.emit(OpCode.if_icmpeq, true_label);
                    break;
                case ">":
                    code.emit(OpCode.if_icmpgt, true_label);
                    break;
                case "<":
                    code.emit(OpCode.if_icmplt, true_label);
                    break;
                case ">=":
                    code.emit(OpCode.if_icmpge, true_label);
                    break;
                case "<=":
                    code.emit(OpCode.if_icmple, true_label);
                    break;
                default:
                    code.emit(OpCode.if_icmpne, true_label);
                    break;
            }
            code.emit(OpCode.GOto, false_label);
        }
        else{
            error("error in bexpr");
        }
    }

    private void expr() { // <expr> -> +(<exprlist>) | - <expr> <expr> | *(<exprlist>) | / <expr> <expr> | NUM | ID
        OpCode oc_2;
        switch (look.tag) {
            case Tag.NUM:
                int val = ((NumberTok)look).lexeme;
                code.emit(OpCode.ldc, val);
                match(Tag.NUM);
                break;
            case Tag.ID:
                code.emit(OpCode.iload, st.lookupAddress(((Word)look).lexeme));
                match(Tag.ID);
                break;
            case '+':
                match('+');
                match('(');
                oc_2 = OpCode.iadd;
                exprlist_2(new Instruction(OpCode.iadd), oc_2);
                match(')');
                break;
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;
            case '*':
                match('*');
                match('(');
                oc_2 = OpCode.imul;
                exprlist_2(new Instruction(OpCode.imul), oc_2);
                match(')');
                break;
            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;
            default:
                error("error in expr");
        }
    }

    private void exprlist_1(Instruction instruction, OpCode oc) { //<exprlist> -> <expr> <exprlistp> 
        if(look.tag == '+' || look.tag == '-' || look.tag == '*' || look.tag == '/' || look.tag == Tag.NUM || look.tag == Tag.ID){
            expr();
            code.emit(oc,1);
            exprlistp_1(instruction, oc);
        }
        else{
            error("error in exprlist_1");
        }
    }

    private void exprlistp_1(Instruction instruction, OpCode oc) { //<exprlistp> -> , <expr> <exprlistp> | ε 
        if(look.tag == ','){
            match(',');
            expr();
            code.emit(oc,1);
            exprlistp_1(instruction, oc);
        }
        else if(look.tag == ')'){
        }
        else{
            error("error in exprlistp_1");
        }
    }

    private void exprlist_2(Instruction instruction, OpCode oc_2) { //<exprlist> -> <expr> <exprlistp> 
        if(look.tag == '+' || look.tag == '-' || look.tag == '*' || look.tag == '/' || look.tag == Tag.NUM || look.tag == Tag.ID){
            expr();
            exprlistp_2(instruction, oc_2);
        }
        else{
            error("error in exprlist_2");
        }
    }

    private void exprlistp_2(Instruction instruction, OpCode oc) { //<exprlistp> -> , <expr> <exprlistp> | ε 
        if(look.tag == ','){
            match(',');
            expr();
            code.emit(oc);
            exprlistp_2(instruction, oc);
        }
        else if(look.tag == ')'){
        }
        else{
            error("error in exprlistp_2");
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./esempio_semplice.lft";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}


