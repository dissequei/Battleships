package ship;

import exceptions.DevException;

/**
 * Klasa przechowujaca informacje na temat aktualnego statusu rozgrywki.<br />
 *
 * <p>
 * aktualizacje:<br />
 * 1.1<br />
 * - dodanie metod {@link #getPunktyGracz()} i {@link #getPunktyKomputer()}.<br />
 * </p>
 * 
 * @author VGT
 * @version 1.1
 */
public class GameStatus
	{
	/**
	 * Okresla, czy gra zostala rozpoczeta.
	 */
	private boolean bGraRozpoczeta;
	/**
	 * Okresla, czy gra zostala zakonczona.
	 */
	private boolean bGraZakonczona;
	/**
	 * Okresla, czy rozmieszczenie statkow przez gracza zostalo ukonczone i zatwierdzone.
	 */
	private boolean bStatkiRozmieszczone;
	/**
	 * Okresla, czy wygral gracz.
	 */
	private boolean bWygranaGracz;
	/**
	 * Okresla, czy wygral komputer.
	 */
	private boolean bWygranaKomputer;
	/**
	 * Laczna ilosc zwyciestw gracza od momentu uruchomienia programu.
	 */
	private int iPunktyGracz;
	/**
	 * Laczna ilosc zwyciestw komputera od momentu uruchomienia programu.
	 */
	private int iPunktyKomputer;
	/**
	 * Konstruktor domyslny.
	 */
	public GameStatus()
		{
		iPunktyGracz = 0;
		iPunktyKomputer = 0;
		resetujUstawienia();
		}
	/**
	 * Zwraca informacje, czy gra zostala rozpoczeta.
	 * 
	 * @return Zwraca TRUE, jesli trwa rozgrywka, lub FALSE w przeciwnym wypadku.
	 */
	public boolean getGraRozpoczeta()
		{
		return bGraRozpoczeta;
		}
	/**
	 * Zwraca informacje, czy gra zostala zakonczona.
	 * 
	 * @return Zwraca TRUE, jesli rozgrywka zakonczyla sie (trafiono wszystkie statki gracza lub komputera),
	 * lub FALSE w przeciwnym wypadku.
	 */
	public boolean getGraZakonczona()
		{
		return bGraZakonczona;
		}
	/**
	 * Zwraca informacje, czy statki gracza zostaly prawidlowo rozmieszczone na planszy.
	 * 
	 * @return Zwraca TRUE, jesli gracz rozmiescil statki i zostaly one zweryfikowane, lub FALSE w przeciwnym wypadku.
	 */
	public boolean getStatkiRozmieszczone()
		{
		return bStatkiRozmieszczone;
		}
	/**
	 * Zwraca informacje, czy gracz wygral aktualna rozgrywke.
	 * 
	 * @return Zwraca TRUE, jesli gracz zatopil wszystkie statki komputera, lub FALSE w przeciwnym wypadku.
	 */
	public boolean getWygranaGracz()
		{
		return bWygranaGracz;
		}
	/**
	 * Zwraca informacje, czy komputer wygral aktualna rozgrywke.
	 * 
	 * @return Zwraca TRUE, jesli komputer zatopil wszystkie statki gracza, lub FALSE w przeciwnym wypadku.
	 */
	public boolean getWygranaKomputer()
		{
		return bWygranaKomputer;
		}
	/**
	 * Zwraca ilosc punktow gracza.
	 * 
	 * @return Ilosc punktow zdobytych przez gracza.
	 * @since 1.1
	 */
	public int getPunktyGracz()
		{
		return iPunktyGracz;
		}
	/**
	 * Zwraca ilosc punktow komputera.
	 * 
	 * @return Ilosc punktow zdobytych przez komputer.
	 * @since 1.1
	 */
	public int getPunktyKomputer()
		{
		return iPunktyKomputer;
		}
	/**
	 * Metoda wywolywana przy rozpoczeciu nowej rozgrywki.
	 * Ustawia wlasciwosci obiektu na wymagane wartosci.
	 */
	public void rozpocznijNowaGre()
		{
		resetujUstawienia();
		bGraRozpoczeta = true;
		}
	/**
	 * Metoda wywolywana przy wygranej gracza.
	 * Ustawia wlasciwosci obiektu na wymagane wartosci.
	 */
	public void zwyciestwoGracza()
		{
		++iPunktyGracz;
		bGraRozpoczeta = false;
		bGraZakonczona = true;
		bWygranaGracz = true;
		}
	/**
	 * Metoda wywolywana przy wygranej komputera.
	 * Ustawia wlasciwosci obiektu na wymagane wartosci.
	 */
	public void zwyciestwoKomputera()
		{
		++iPunktyKomputer;
		bGraRozpoczeta = false;
		bGraZakonczona = true;
		bWygranaKomputer = true;
		}
	/**
	 * Metoda wywolywana w momencie zatwierdzenia rozmieszczenia statkow gracza.
	 * Ustawia wlasciwosci obiektu na wymagane wartosci.
	 */
	public void zatwierdzRozmieszczenieStatkow()
		{
		if (bGraRozpoczeta != true || bStatkiRozmieszczone != false || bGraZakonczona != false)
			throw new DevException();
		bStatkiRozmieszczone = true;
		}
	/**
	 * Metoda resetujaca ustawienia obiektu na wartosci poczatkowe.
	 */
	private void resetujUstawienia()
		{
		bGraRozpoczeta = false;
		bGraZakonczona = false;
		bStatkiRozmieszczone = false;
		bWygranaGracz = false;
		bWygranaKomputer = false;
		}
	}
