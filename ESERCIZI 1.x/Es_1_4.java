/*Esercizio 1.4: TurniLabModificato*/
/*Modificare automa dell'esercizio precedente in modo che sia in grado di riconoscere le combinazioni di matricola e studente
 *del turno T2 o T3 dove numero di matricola e cognome possono essere separati da una sequenza di spazi e possono essere
 *precedute e/o seguite da una sequenza eventualmente vuota di spazi.
 *Progettare poi una variante dell'automa che riconosca i cognomi composti da più parti.
*/

/*---------------NON FUNZIONA: NON RIESCE A LEGGERE GLI SPAZI (RISOLTO)---------------
 * Non riesce a vedere gli spazi, sia alla fine che all'inizio che tra numero e parola.
 * Di conseguenza automa non lo vede e si ferma agli stati 1 o 2 (dopo aver letto i numeri correttamente) ma non prosegue.
 * Dovrebbe leggere carattere di spazio (' ') e passare agli stati 3 o 4 e da lì di conseguenza ciclare sugli stessi stati 3 o 4 
 * fintanto che legge spazi e poi passare allo stato 5 (uno dei due finali) se legge rispettivamente A-K o L-Z.
 * Peccato che non legga proprio gli spazi. E' come se la stringa finisse dopo l'inserimento dei numeri.
 * 
 *---------------SOLUZIONE:-------------------------------------------------
 * Quando esegui programma e passi la stringa da riconoiscere ricordati che stai utilizzando args, quindi interpreta lo spazio 
 * come carattere di terminazione dell'argomento. Per ovviare a questo problema serve scrivere la tua stringa (contenenente spazi) 
 * racchiusa tra virgolette, in questo modo: "......"
*/

public class Es_1_4 {
	

	public static boolean scan(String s) {
		int state = 0;
		int i = 0;

		while(state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			System.out.println("state :" + state);

			switch(state) {
				case 0:
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 1;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 2;
					if(ch == ' ')
						state = 0;
					else if(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z')
						state = -1;
					break;

				case 1:
					if(ch == ' ')
						state = 3;
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 1;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 2;
					if(ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = -1;
					else if (ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = 5;
					break;

				case 2:
					if(ch == ' ')
						state = 4;
					if(ch == '0' || ch == '2' || ch == '4' || ch == '6' || ch == '8')
						state = 1;
					else if(ch == '1' || ch == '3' || ch == '5' || ch == '7' || ch == '9')
						state = 2;
					if(ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = 5;
					else if (ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = -1;
					break;

				case 3:
					if(ch == ' ')
						state = 3;
					else if(ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = 5;
					else //quando ho pari, dispari e lettere dalla L alla Z
						state = -1;
					break;

				case 4:
					if(ch == ' ')
						state = 4;
					else if(ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = 5;
					else //quando ho pari, dispari e lettere dalla A alla K
						state = -1;
					break;

				case 5:
					if(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z')
						state = 5;
					else if(ch == ' ')
						state = 6;
					else //quando ho pari e dispari
						state = -1;
					break;

				case 6:
					if(ch == ' ')
						state = 6;
					else //quando ho pari, dispari e lettere dalla A alla Z (cioè tutto tranne spazio)
						state = -1;
					break;
			}
		}

		System.out.println("state :" + state);

		return ((state == 5) || (state == 6));
	}


	public static void main(String[] args) {
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}