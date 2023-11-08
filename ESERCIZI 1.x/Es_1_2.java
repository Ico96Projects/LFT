/*Esercizio 1.2: Identificatori*/
/*Riconoscere identificatori: sequenze non vuote di lettere, numeri e simbolo di "underscore".
 *Non deve iniziare con un numero e non può essere formato dal solo simbolo di "underscore".
 *Fare prima DFA, poi tradurre in java
*/

public class Es_1_2 {

	public static boolean scan (String s) {
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


	public static void main(String[] args) {
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}