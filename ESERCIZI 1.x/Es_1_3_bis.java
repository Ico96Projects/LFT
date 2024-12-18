/*Esercizio 1.3 bis:TurniLab*/
/* Riconoscere linguaggio di stringhe che contengono un numero di matricola seguito subito da un cognome
 * la combinazione di matricola e cognome deve corrispondere a turno 1 o turno 4.
 * Matricola pari && iniziale cognome compresa tra L e Z
 * Matricola dispari && iniziale cognome compresa tra A e K
 * Numero matricola ALMENO una cifra (non bumero massimo prestabilito)
 * Cognome composto da ALMENO una lettera 
*/


public class Es_1_3_bis {
	

	public static boolean scan (String s) {
		int state = 0;
		int i = 0;

		while(state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch (state) {
				case 0:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 1;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 2;
					else
						state = -1;
					break;

				case 1:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 1;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 2;

					if(ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = -1;
					else if(ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = 3;
					break;

				case 2:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 1;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 2;

					if(ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = 3;
					else if(ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = -1;
					break;

				case 3:
					if(ch >= '0' && ch <= '9')
						state = -1;
					else if(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z')
						state = 3;
					break;

			}
		}
		return state == 3;
	}

	public static void main(String[] args) {
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}