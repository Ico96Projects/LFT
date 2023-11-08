/*Esercizio 1.8: CostantiNumeriche*/
/* Implementare un DFA che riconosca le stringhe del linguaggio delle costanti numeriche scritte in virgola mobile 
 * utilizzando la notazione scientifica in cui "e" indica fiunzione exp in base 10.
 * Per riferimento ai parametri di implementazione e limitazioni nel modo di riconoscimento delle stringhe (regole)
 * andare a vedere le slides.
*/


public class Es_1_8 {


	public static boolean scan(String s) {
		int state = 0;
		int i = 0;

		while(state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch(state) {
				case 0:
					if(ch == '+' || ch == '-')
						state = 1;
					else if(Character.isDigit(ch))
						state = 2;
					else if(ch == '.')
						state = 3;
					else if(ch == 'e')
						state = -1;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
					}				
					break;

				case 1:
					if(Character.isDigit(ch))
						state = 2;
					else if(ch == '.')
						state = 3;
					else if(ch == '+' || ch == '-' || ch == 'e')
						state = -1;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
					}					
					break;

				case 2:
					if(Character.isDigit(ch))
						state = 2;
					else if(ch == '.')
						state = 3;
					else if(ch == 'e')
						state = 5;
					else if(ch == '+' || ch == '-')
						state = -1;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
					}				
					break;

				case 3:
					if(Character.isDigit(ch))
						state = 4;
					else if(ch == 'e' || ch == '+' || ch == '-' || ch == '.')
						state = -1;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
					}				
					break;

				case 4:
					if(Character.isDigit(ch))
						state = 4;
					else if(ch == 'e')
						state = 5;
					else if(ch == '+' || ch == '-' || ch == '.')
						state = -1;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
					}				
					break;

				case 5:
					if(Character.isDigit(ch))
						state = 6;
					else if(ch == '+' || ch == '-')
						state = 7;
					else if(ch == '.')
						state = 8;
					else if(ch == 'e')
						state = -1;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
					}				
					break;

				case 6:
					if(Character.isDigit(ch))
						state = 6;
					else if(ch == '.')
						state = 8;
					else if(ch == '+' || ch == '-' || ch == 'e')
						state = -1;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
					}				
					break;

				case 7:
					if(Character.isDigit(ch))
						state = 6;
					else if(ch == '.')
						state = 8;
					else if(ch == '+' || ch == '-' || ch == 'e')
						state = -1;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
					}				
					break;

				case 8:
					if(Character.isDigit(ch))
						state = 9;
					else if(ch == '+' || ch == '-' || ch == 'e' || ch == '.')
						state = -1;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
					}				
					break;

				case 9:
					if(Character.isDigit(ch))
						state = 9;
					else if(ch == '+' || ch == '-' || ch == 'e' || ch == '.')
						state = -1;
					else {
						System.err.println("Erroneous character: " + ch );
                        return false;
					}				
					break;

			}
		}

		return (state == 2) || (state == 4) || (state == 6) || (state == 9);
	}


	public static void main(String[] args) {
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}