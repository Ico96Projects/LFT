import java.io.*; 
import java.util.*;

public class Lexer_2_3 {

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

    public static boolean scan (String s) { //Automa 1.2: Riconoscitore di identificatori
        int state = 0;
        int i = 0;

        while(state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {
                case 0:
                    if (Character.isLetter(ch))
                        state = 2;
                    else if (ch == '_')
                        state = 1;
                    else if (Character.isDigit(ch))
                        state = -1;
                    break;

                case 1:
                    if (ch == '_')
                        state = 1;
                    else if (Character.isLetter(ch) || Character.isDigit(ch))
                        state = 2;
                    break;

                case 2:
                    if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '_')
                        state = 2;
                    break;          
            }
        }
        return state == 2;
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }

    	//gestione simbolo "//" e "/**/" per il riconoscimento e ignoramento dei commenti:

        while(peek == '/') {
        	readch(br);
        	if(peek == '/') { //commento con doppia linea "//"
        		do {
        			readch(br);
        		} while(peek != '\n' && peek != Tag.EOF);
        		if(peek == '\n') { 
        			line++;
        			readch(br);
        		}
        	}
        	else if(peek == '*') { //commento con asterisco "/**/"
        		boolean end_star = false, end_comment = false;
        		do { //leggo corpo del commento, fino a che non leggo chiusura
        			readch(br);
        			if(peek == '*') 
        				end_star = true; //inizio chiusura del commento
        			else if(end_star && peek == '/') {
        				end_comment = true;
        				peek = ' ';
        			}
        			else { //corpo del commento
        				if(peek == '\n') 
        					line++;
        				end_star = false;
        			}
        		} while(!end_comment && peek != (char) -1);
        		if(end_comment) {
        			while(peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
        				if(peek == '\n')
        					line++;
        				readch(br);
        			}
        		}
        		else{
        			System.err.println("ERROR: commento non chiuso");
        			return null;
        		}
        	}
        	else
        		return Token.div; // simbolo '/' singolo, non accoppiato a '/' o '*'
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
                if (Character.isLetter(peek) || (peek == '_')) {
	// ... gestire il caso degli identificatori e delle parole chiave
                	wrd = wrd + peek;
                	readch(br);
                	while ((Character.isDigit(peek)) || peek == '_' || (Character.isLetter(peek))) {
                		wrd = wrd + peek;
                        peek = ' ';
                		readch(br);
                	}
                    boolean flag = scan(wrd);
                    if(Word.whiletok.lexeme.equals(wrd)) {
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
                	} else if (flag == true) { //se flag è true allora wrd è un identificatore
                        Token t = new Token(257);
                        Word w = new Word(t.tag, wrd);
                        wrd = "";
                        return w;
                	}
                	else{
                        System.err.println("Erroneous character: " 
                                + peek );
                		return null;
                	}

                } else if (Character.isDigit(peek)) {
	// ... gestire il caso dei numeri
                	wrd = wrd + peek;
                	readch(br);
                	while ((Character.isDigit(peek)) || (peek == '_') || (Character.isLetter(peek))) {
                		wrd = wrd + peek;
                		peek = ' ';
                		readch(br);
                	}

                    boolean flag = scan(wrd);
                    if(flag == true) {
                        Token t = new Token(257);
                        Word w = new Word(t.tag, wrd);
                        wrd = "";
                        return w;
                    }
                    else {
                        int numero = Integer.parseInt(wrd); //cast da stringa a intero
                        Token s = new Token(256);
                        NumberTok c = new NumberTok(s.tag, numero);
                        wrd = "";
                        return c;
                    }
                } else {
                        System.err.println("Erroneous character: " 
                                + peek );
                        return null;
                }
        }
    }
		
    public static void main(String[] args) {
        Lexer_2_3 lex = new Lexer_2_3();
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
