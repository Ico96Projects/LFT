/*Esercizio 1.6: TurniLabPenultimaCifra*/
/*Costruire DFA che riconosca stringhe di numeri di matricola e cognome degli studenti appartenenti ai turni T2 o T3 di laboratorio
 *La differenziazione in turni in base al numero di matricola non segue più la parità dell'ultima cifra ma 
 *come quest'anno bisogna considerare la penultima cifra.
 *Per il resto rimane simile al 1.3
*/


public class Es_1_6 {


	public static boolean scan(String s) {
		int state = 0;
		int i = 0;

		while(state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch(state) {
				case 0:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 1;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 2;
					else //se ho lettere (dalla A alla Z) e qualunque cosa che non sia numero
						state = -1;					
					break;

				case 1:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 3;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 4;
					else //se ho lettere (dalla A alla Z) e qualunque cosa che non sia numero
						state = -1;					
					break;

				case 2:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 5;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 6;
					else //se ho lettere (dalla A alla Z) e qualunque cosa che non sia numero
						state = -1;					
					break;

				case 3:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 3;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 4;
					if(ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = 7;
					else if(ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = -1;
					break;

				case 4:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 5;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 6;
					if(ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = 7;
					else if(ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = -1;
					break;

				case 5:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 3;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 4;
					if(ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = -1;
					else if(ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = 7;
					break;

				case 6:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 5;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 6;
					if(ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = -1;
					else if(ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = 7;
					break;

				case 7:
					if(ch >= '0' && ch <= '9')
						state = -1;
					else if(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z')
						state = 7;
					break;
			}
		}

		return state == 7;
	}


	public static void main(String[] args) {
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}