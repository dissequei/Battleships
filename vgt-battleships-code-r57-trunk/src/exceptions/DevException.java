package exceptions;

/**
 * Wyjatek typu RuntimeException wykorzystywany glownie w przypadku obejmowania instukcjami try-catch wnetrza metod.
 * Wystapienie tego bledu zazwyczaj jest niezalezne od uzytkownika klasy i bedzie wynikiem bledu
 * autora klasy w tworzeniu danego bloku kodu.<br />
 * 
 * <p>
 * aktualizacje:<br />
 * 1.1.1<br />
 * - poprawki w nazewnictwie zmiennych<br />
 * - poprawki w dokumentacji<br />
 * 1.1<br />
 * - dodanie dwoch dodatkowych konstruktorow pozwalajacych przekazac wyjatek-przyczyne wyrzucenia wyjatku.<br />
 * </p>
 * 
 * @author VGT
 * @version 1.1.1
 */
public class DevException
	extends RuntimeException
	{
	/**
	 * Konstruktor domyslny.
	 */
	public DevException() {super();}
	/**
	 * Konstruktor przeciazony pozwalajacy zdefiniowac tresc bledu.
	 * 
	 * @param sMessage Komunikat na temat bledu, ktory spowodowal wyrzucenie wyjatku.
	 */
	public DevException(String sMessage) {super(sMessage);}
	/**
	 * Konstruktor przeciazony pozwalajacy zdefiniowac tresc bledu i wyjatek-przyczyne.
	 * 
	 * @since 1.1
	 * @param sMessage Komunikat na temat bledu, ktory spowodowal wyrzucenie wyjatku.
	 * @param oCause Wyjatek bedacy przyczyna bledu.
	 */
	public DevException(String sMessage, Throwable oCause) {super(sMessage, oCause);}
	/**
	 * Konstruktor przeciazony pozwalajacy zdefiniowac wyjatek-przyczyne.
	 * 
	 * @since 1.1
	 * @param oCause Wyjatek bedacy przyczyna bledu.
	 */
	public DevException(Throwable oCause) {super(oCause);}
	}
