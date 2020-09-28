package exceptions;

/**
 * Wyjatek wyrzucany w przypadku, gdy parametr przekazany do metody ma nieprawidlowa wartosc.<br />
 * 
 * <p>
 * aktualizacje:<br />
 * 1.0.1<br />
 * - poprawki w nazewnictwie zmiennych<br />
 * - poprawki w dokumentacji.<br />
 * </p>
 * 
 * @author VGT
 * @version 1.0.1
 */
public class ParametrException
	extends Exception
	{
	/**
	 * Konstruktor domyslny.
	 */
	public ParametrException() {super();}
	/**
	 * Konstruktor przeciazony pozwalajacy zdefiniowac tresc bledu.
	 * 
	 * @param sMessage Komunikat na temat bledu, ktory spowodowal wyrzucenie wyjatku.
	 */
	public ParametrException(String sMessage) {super(sMessage);}
	}
