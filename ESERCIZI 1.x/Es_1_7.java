/*Esercizio 1.7: MioNomeConUnErrore*/
/* Costruire DFA che riconosca stringhe contenenti il mio nome e tutte le stringhe ottenute dopo la 
 * sostituzione di un carattere del nome con un altro carattere qualsiasi.
 * In sostanza deve accettare la stringa "Federico" (con prima lettera maiuscola) e tutte le 
 * stringhe ottenute da "Federico" a cui ho modificato un carattere (ho commesso un errore)
*/

public class Es_1_7 {

	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		//System.out.println("state: " + state);//PURAMENTE A SCOPO DI DEBUG (data la quantità di ctrl+c e ctrl+v probabili errori)

		while(state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch(state) {
				case 0:
					if(ch == 'f' || ch == 'F')
						state = 1;
					else if((ch >= 'a' && ch <= 'z' && ch != 'f') || (ch >= 'A' && ch <= 'Z' && ch != 'F'))
						state = 9;
					else { 
						System.err.println("Erroneous character: " + ch );
                        return false;
                    }
					break;

				case 1:
					if(ch == 'e' || ch == 'E')
						state = 2;
					else if((ch >= 'a' && ch <= 'z' && ch != 'e') || (ch >= 'A' && ch <= 'Z' && ch != 'E'))
						state = 10;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
                    }
                    break;

                case 2:
					if(ch == 'd' || ch == 'D')
						state = 3;
					else if((ch >= 'a' && ch <= 'z' && ch != 'd') || (ch >= 'A' && ch <= 'Z' && ch != 'D'))
						state = 11;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
                    }
                    break;

                case 3:
					if(ch == 'e' || ch == 'E')
						state = 4;
					else if((ch >= 'a' && ch <= 'z' && ch != 'e') || (ch >= 'A' && ch <= 'Z' && ch != 'E'))
						state = 12;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
                    }
                    break;

                case 4:
					if(ch == 'r' || ch == 'R')
						state = 5;
					else if((ch >= 'a' && ch <= 'z' && ch != 'r') || (ch >= 'A' && ch <= 'Z' && ch != 'R'))
						state = 13;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
                    }
                    break;

                case 5:
					if(ch == 'i' || ch == 'I')
						state = 6;
					else if((ch >= 'a' && ch <= 'z' && ch != 'i') || (ch >= 'A' && ch <= 'Z' && ch != 'I'))
						state = 14;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
                    }
                    break;

                case 6:
					if(ch == 'c' || ch == 'C')
						state = 7;
					else if((ch >= 'a' && ch <= 'z' && ch != 'c') || (ch >= 'A' && ch <= 'Z' && ch != 'C'))
						state = 15;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
                    }
                    break;

                case 7:
					if(ch == 'o' || ch == 'O')
						state = 8;
					else if((ch >= 'a' && ch <= 'z' && ch != 'o') || (ch >= 'A' && ch <= 'Z' && ch != 'O'))
						state = 16;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
                    }
                    break;

                case 8:
                	if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
                		state = -1;
                	else {
                		System.err.println("Erroneous character: " + ch );
                        return false;
                	}
                	break;

               	case 9:
               		if(ch == 'e' || ch == 'E')
               			state = 10;
               		else if((ch >= 'a' && ch <= 'z' && ch != 'e') || (ch >= 'A' && ch <= 'Z' && ch != 'E'))
               			state = -1;
               		else {
               			System.err.println("Erroneous character: " + ch );
                        return false;
               		}
               		break;

               	case 10:
               		if(ch == 'd' || ch == 'D')
               			state = 11;
               		else if((ch >= 'a' && ch <= 'z' && ch != 'd') || (ch >= 'A' && ch <= 'Z' && ch != 'D'))
               			state = -1;
               		else {
               			System.err.println("Erroneous character: " + ch );
                        return false;
               		}
               		break;

               	case 11:
               		if(ch == 'e' || ch == 'E')
               			state = 12;
               		else if((ch >= 'a' && ch <= 'z' && ch != 'e') || (ch >= 'A' && ch <= 'Z' && ch != 'E'))
               			state = -1;
               		else {
               			System.err.println("Erroneous character: " + ch );
                        return false;
               		}
               		break;

               	case 12:
               		if(ch == 'r' || ch == 'R')
               			state = 13;
               		else if((ch >= 'a' && ch <= 'z' && ch != 'r') || (ch >= 'A' && ch <= 'Z' && ch != 'R'))
               			state = -1;
               		else {
               			System.err.println("Erroneous character: " + ch );
                        return false;
               		}
               		break;

               	case 13:
               		if(ch == 'i' || ch == 'I')
               			state = 14;
               		else if((ch >= 'a' && ch <= 'z' && ch != 'i') || (ch >= 'A' && ch <= 'Z' && ch != 'I'))
               			state = -1;
               		else {
               			System.err.println("Erroneous character: " + ch );
                        return false;
               		}
               		break;

               	case 14:
               		if(ch == 'c' || ch == 'C')
               			state = 15;
               		else if((ch >= 'a' && ch <= 'z' && ch != 'c') || (ch >= 'A' && ch <= 'Z' && ch != 'C'))
               			state = -1;
               		else {
               			System.err.println("Erroneous character: " + ch );
                        return false;
               		}
               		break;

               	case 15:
               		if(ch == 'o' || ch == 'O')
               			state = 16;
               		else if((ch >= 'a' && ch <= 'z' && ch != 'o') || (ch >= 'A' && ch <= 'Z' && ch != 'O'))
               			state = -1;
               		else {
               			System.err.println("Erroneous character: " + ch );
                        return false;
               		}
               		break;

               	case 16:
                	if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
                		state = -1;
                	else {
                		System.err.println("Erroneous character: " + ch );
                        return false;
                	}
                	break;
			}
			//System.out.println("state: " + state);//PURAMENTE A SCOPO DI DEBUG (data la quantità di ctrl+c e ctrl+v probabili errori)
		}

		return (state == 8) || (state == 16);
	}

	public static void main(String[] args) {
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}