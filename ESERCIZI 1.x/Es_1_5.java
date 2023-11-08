/*Esercizio 1.5: TurniLabInverso*/
/*Come esercizio 1.3 sul numero di matricola e cognome per turni T2 e T3 di laboratorio ma "invertito" nel senso che 
 *prima viene letto il cognome e poi il numero di matricola. Le regole e limitazioni rimangono invariate dal 1.3
*/

/*prendere base da 1.3 e modificare*/

public class Es_1_5 {


	public static boolean scan (String s) {
		int state = 0;
		int i = 0;

		while(state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch (state) {
				case 0:
					if(ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = 1;
					else if(ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = 2;
					else
						state = -1;
					break;

				case 1:
					if(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z')
						state = 1;
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 5;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 3;
					break;

				case 2:
					if(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z')
						state = 2;
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 4;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 6;
					break;

				case 3:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 5;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 3;
					else
						state = -1;
					break;

				case 4:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 4;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 6;
					else
						state = -1;
					break;

				case 5:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 5;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 3;
					else
						state = -1;
					break;

				case 6:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 4;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 6;
					else
						state = -1;
					break;
			}
		}
		return ((state == 5) || (state == 6));
	}

	public static void main(String[] args) {
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}