import java.io.*; 
import java.util.*;

public class Lexer_2_1 {

    public static int line = 1;
    private char peek = ' ';
    String wrd = "";
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;
            case '(':
                peek = ' ';
                return Token.lpt;
            case ')':
                peek = ' ';
                return Token.rpt;
            case '{':
                peek = ' ';
                return Token.lpg;
            case '}':
                peek = ' ';
                return Token.rpg;
            case '+':
                peek = ' ';
                return Token.plus;
            case '-':
                peek = ' ';
                return Token.minus;
            case '*':
                peek = ' ';
                return Token.mult;
            case '/':
                peek = ' ';
                return Token.div;
            case ';':
                peek = ' ';
                return Token.semicolon;
            case ',':
                peek = ' ';
                return Token.comma;
	
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } 
                else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }
            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                }
                else {
                    System.err.println("Erroneous character"
                            + " after | : "  + peek );
                    return null;
                }
            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                }
                else {
                    System.err.println("Erroneous character"
                            + " after = : "  + peek );
                    return null;
                }
            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                }
                else if (peek == '>') {
                	peek = ' ';
                	return Word.ne;
                }
                else if (peek != '=' || peek != '>') {
                	peek = ' ';
                	return Word.lt;
                }
                else {
                    System.err.println("Erroneous character"
                            + " after < : "  + peek );
                    return null;
                }
            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                }
                else if (peek != '=') {
                	peek = ' ';
                	return Word.gt;
                }
                else {
                    System.err.println("Erroneous character"
                            + " after < : "  + peek );
                    return null;
                }
          
            case (char)-1:
                return new Token(Tag.EOF);

            default:
                if (Character.isLetter(peek)) {
	// ... gestire il caso degli identificatori e delle parole chiave
                	wrd = wrd + peek;
                	readch(br);
                	while (peek != ' ' && peek != '\t' && peek != '\n'  && peek != '\r' && (Character.isLetter(peek) || Character.isDigit(peek))) {
                		wrd = wrd + peek;
                		peek = ' ';
                		readch(br);
                	}
                	if (Word.whiletok.lexeme.equals(wrd)) {
                		wrd = "";
                		return Word.whiletok;
                	} else if (Word.assign.lexeme.equals(wrd)) {
                		wrd = "";
                		return Word.assign;
                	} else if (Word.to.lexeme.equals(wrd)) {
                		wrd = "";
                		return Word.to;
                	} else if (Word.iftok.lexeme.equals(wrd)) {
                		wrd = "";
                		return Word.iftok;
                	} else if (Word.elsetok.lexeme.equals(wrd)) {
                		wrd = "";
                		return Word.elsetok;
                	} else if (Word.begin.lexeme.equals(wrd)) {
                		wrd = "";
                		return Word.begin;
                	} else if (Word.end.lexeme.equals(wrd)) {
                		wrd = "";
                		return Word.end;
                	} else if (Word.print.lexeme.equals(wrd)) {
                		wrd = "";
                		return Word.print;
                	} else if (Word.read.lexeme.equals(wrd)) {
                		wrd = "";
                		return Word.read;
                	}
                	else{
                		Token s = new Token(257);
                		Word c = new Word(s.tag, wrd);
                		wrd = "";
                		return c;
                	}

                } else if (Character.isDigit(peek)) {
	// ... gestire il caso dei numeri
                	wrd = wrd + peek;
                	readch(br);
                	while (Character.isDigit(peek)) {
                		wrd = wrd + peek;
                		peek = ' ';
                		readch(br);
                	}
                	int numero = Integer.parseInt(wrd); //cast da stringa a intero
                	Token s = new Token(256);
                	NumberTok c = new NumberTok(s.tag, numero);
                	wrd = "";
                	return c;
                } else {
                        System.err.println("Erroneous character: " 
                                + peek );
                        return null;
                }
        }
    }
		
    public static void main(String[] args) {
        Lexer_2_1 lex = new Lexer_2_1();
        String path = "./Test.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}
