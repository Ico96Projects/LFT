/*Esercizio 1.9: StringheCommenti*/
/* Progettare e implementare un DFA definito sull'alfabeto {/,*,a} che riconosca il linguaggio dei commenti 
 * scritti stile C con standard c89.
 * Per le regole di implementazione andare a vedere le slides.
*/


public class Es_1_9 {


	public static boolean scan(String s) {
		int state = 0;
		int i = 0;

		while(state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch(state) {
				case 0:
					if(ch == '/')
						state = 1;
					else if(ch == 'a' || ch == '*') //simboli dell'alfabeto ma uso illecito --> stato pozzo
						state = -1;
					else { //se inserisco un simbolo estraneo all'alfabeto stampa messaggio errore ed esce
						System.err.println("Character not in the alphabet: " + ch );
                        return false;
					}				
					break;

				case 1:
					if(ch == '*')
						state = 2;
					else if(ch == 'a' || ch == '/') //simboli dell'alfabeto ma uso illecito --> stato pozzo
						state = -1;
					else { //se inserisco un simbolo estraneo all'alfabeto stampa messaggio errore ed esce
						System.err.println("character not in the alphabet: " + ch );
                        return false;
					}					
					break;

				case 2:
					if(ch == '*')
						state = 3;
					else if(ch == 'a' || ch == '/') //simboli dell'alfabeto, corpo del commento
						state = 2;
					else { //se inserisco un simbolo estraneo all'alfabeto stampa messaggio errore ed esce
						System.err.println("character not in the alphabet: " + ch );
                        return false;
					}					
					break;

				case 3:
					if(ch == '*')
						state = 3;
					else if(ch == 'a') //simboli dell'alfabeto, corpo del commento, torno al 2
						state = 2;
					else if(ch == '/')
						state = 4;
					else { //se inserisco un simbolo estraneo all'alfabeto stampa messaggio errore ed esce
						System.err.println("character not in the alphabet: " + ch );
                        return false;
					}					
					break;

				case 4:
					if(ch == 'a' || ch == '*' || ch == '/') //simboli leciti ma uso errato --> stato pozzo
						state = -1;
					else { //se inserisco un simbolo estraneo all'alfabeto stampa messaggio errore ed esce
						System.err.println("Character not in the alphabet: " + ch );
                        return false;
					}				
					break;
			}
		}

		return state == 4;
	}


/*Nel main sottostante ho scritto direttamente ed esplicitamente (una buona parte di) tutti i possibili sceniari che si 
 *possono riscontrare nella fase di riconoscimento dei commenti. Ho deciso di evitare di passare la stringa da linea di
 *comando poichè il simbolo '/' non viene riconosciuto come tale, probabilemnte segue un'interpretazione a parte (simile
 *a come viene interpretato lo spazio) generando errore di simbolo non appartenente all'alfabeto.
 *Con questa "modifica" ora l'automa si comporta correttamente.
*/

	public static void main(String[] args) {
		System.out.println("PRINTING ALL THE STRINGS EXPECTED TO BE OK: \n");

		System.out.println(scan("/****/") ? "OK : ' /****/ ' " : "NOPE : ' /****/ ' ");
		System.out.println(scan("/*a*a*/") ? "OK : ' /*a*a*/ ' " : "NOPE : ' /*a*a*/ ' ");
		System.out.println(scan("/*a/**/") ? "OK : ' /*a/**/ ' " : "NOPE : ' /*a/**/ ' ");
		System.out.println(scan("/**a///a/a**/") ? "OK : ' /**a///a/a**/ ' " : "NOPE : ' /**a///a/a**/ ' ");
		System.out.println(scan("/**/") ? "OK : ' /**/ ' " : "NOPE : ' /**/ ' ");
		System.out.println(scan("/*/*/") ? "OK : ' /*/*/ ' " : "NOPE : ' /*/*/ ' ");


		System.out.println("\n\nPRINTING ALL THE STRINGS EXPECTED TO BE NOPE: \n");

		System.out.println(scan("/*/") ? "OK : ' /*/ ' " : "NOPE : ' /*/ ' "); 
		System.out.println(scan("/**/***/") ? "OK : ' /**/***/ ' " : "NOPE : ' /**/***/ ' ");


		System.out.println("\n\nPRINTING ALL THE STRINGS EXPECTED TO BE 'CHARACTER NOT IN ALPHABET': \n");
		
		System.out.println(scan("/*federico*/") ? "OK : ' /*federico*/ ' " : "NOPE : ' /*federico*/ ' "); 
		System.out.println(scan("/*___*/") ? "OK : ' /*___*/ ' " : "NOPE : ' /*___*/ ' ");
		System.out.println(scan("/*++*/") ? "OK : ' /*++*/ ' " : "NOPE : ' /*++*/ ' ");

	}
}