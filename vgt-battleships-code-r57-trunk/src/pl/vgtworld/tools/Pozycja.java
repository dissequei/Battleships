package pl.vgtworld.tools;

import java.util.Arrays;
import pl.vgtworld.exceptions.ParametrException;
import pl.vgtworld.exceptions.ProgramistaException;

/**
 * Klasa sluzaca do obslugi pozycji w n-wymiarowym zakresie wspolrzednych.<br />
 * 
 * <p>
 * aktualizacje:<br />
 * 1.2.1<br />
 * - poprawki w dokumentacji<br />
 * 1.2<br />
 * - dodanie metod {@link #getZ()}, {@link #setZ(int)}, {@link #przesunZ(int)}, {@link #clone()}<br />
 * 1.1<br />
 * - dodanie obslugi wyjatkow w przypadku odwolywania sie do wymiarow poza zakresem<br />
 * </p>
 * 
 * @author VGT
 * @version 1.2.1
 */
public class Pozycja
	{
	/**
	 * Przechowuje informacje o ilosci wymiarow danej instancji obiektu.
	 */
	int iLiczbaWymiarow;
	/**
	 * Przechowuje wspolrzedne poszczegolnych wymiarow.
	 */
	int[] aWymiary;
	/**
	 * Konstruktor domyslny. Tworzy obiekt o dwoch wymiarach.
	 */
	public Pozycja()
		{
		this(2);
		}
	/**
	 * Konstruktor przeciazony pozwalajacy okreslic ilosc wymiarow obiektu.
	 * 
	 * @param iLiczbaWymiarow Ilosc wymiarow obiektu.
	 */
	public Pozycja(int iLiczbaWymiarow)
		{
		this.iLiczbaWymiarow = iLiczbaWymiarow;
		aWymiary = new int[ iLiczbaWymiarow ];
		for (int i = 0; i < iLiczbaWymiarow; ++i)
			aWymiary[ i ] = 0;
		}
	/**
	 * Przeslonieta wersja metody toString().
	 */
	@Override public String toString()
		{
		return Arrays.toString(aWymiary);
		}
	/**
	 * Metoda zwraca pozycje zapisana na podanym w parametrze wymiarze.
	 * 
	 * @param iNrWymiaru Numer wymiaru, dla ktorego ma byc zwrocona pozycja (liczone od 1).
	 * @return Zwraca pozycja obiektu na danym wymiarze.
	 * @throws ParametrException Wyrzuca wyjatek, jesli przekazany numer wymiaru jest poza zakresem.
	 */
	public int getWymiar(int iNrWymiaru) throws ParametrException
		{
		if (iNrWymiaru > iLiczbaWymiarow || iNrWymiaru <= 0)
			throw new ParametrException("iNrWymiaru = " + iNrWymiaru);
		return aWymiary[ iNrWymiaru - 1 ];
		}
	/**
	 * Uproszczona wersja metody {@link #getWymiar(int)} zwracajaca pozycje pierwszego wymiaru.
	 * 
	 * @return Zwraca pozycje pierwszego wymiaru.
	 */
	public int getX()
		{
		try
			{
			return getWymiar(1);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Uproszczona wersja metody {@link #getWymiar(int)} zwracajaca pozycje drugiego wymiaru.
	 * 
	 * @return Zwraca pozycje drugiego wymiaru.
	 */
	public int getY()
		{
		try
			{
			return getWymiar(2);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Uproszczona wersja metody {@link #getWymiar(int)} zwracajaca pozycje trzeciego wymiaru.
	 * 
	 * @since 1.2
	 * @return Zwraca pozycje trzeciego wymiaru.
	 */
	public int getZ()
		{
		try
			{
			return getWymiar(3);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Metoda zapisuje pozycje w podanym wymiarze.
	 * 
	 * @param iNrWymiaru Numer wymiaru, dla ktorego ma byc zapisana pozycja (liczone od 1).
	 * @param iPozycja Pozycja obiektu w danym wymiarze, na ktora ma byc ustawiony.
	 * @throws ParametrException Wyrzuca wyjatek, jesli przekazany numer wymiaru jest poza zakresem.
	 */
	public void setWymiar(int iNrWymiaru, int iPozycja) throws ParametrException
		{
		if (iNrWymiaru > iLiczbaWymiarow || iNrWymiaru <= 0)
			throw new ParametrException("iNrWymiaru = " + iNrWymiaru);
		aWymiary[ iNrWymiaru - 1 ] = iPozycja;
		}
	/**
	 * Uproszczona wersja metody {@link #setWymiar(int, int)} ustawiajaca pozycje dla pierwszego wymiaru.
	 * 
	 * @param iPozycja Pozycja obiektu w pierwszym wymiarze.
	 */
	public void setX(int iPozycja)
		{
		try
			{
			setWymiar(1, iPozycja);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Uproszczona wersja metody {@link #setWymiar(int, int)} ustawiajaca pozycje dla drugiego wymiaru.
	 * 
	 * @param iPozycja Pozycja obiektu w drugim wymiarze.
	 */
	public void setY(int iPozycja)
		{
		try
			{
			setWymiar(2, iPozycja);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Uproszczona wersja metody {@link #setWymiar(int, int)} ustawiajaca pozycje dla trzeciego wymiaru.
	 * 
	 * @since 1.2
	 * @param iPozycja Pozycja obiektu w trzecim wymiarze.
	 */
	public void setZ(int iPozycja)
		{
		try
			{
			setWymiar(3, iPozycja);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Metoda przesuwa pozycje na danym wymiarze o dana wartosc.
	 * 
	 * @param iNrWymiaru Numer wymiaru, ktorego pozycja ma byc przesunieta (liczone od 1).
	 * @param iWartoscPrzesuniecia Wartosc przesuniecia danego wymiaru.
	 * @throws ParametrException Wyrzuca wyjatek, jesli numer wymiaru jest poza zakresem.
	 */
	public void przesunWymiar(int iNrWymiaru, int iWartoscPrzesuniecia) throws ParametrException
		{
		if (iNrWymiaru > iLiczbaWymiarow || iNrWymiaru <= 0)
			throw new ParametrException("iNrWymiaru = " + iNrWymiaru);
		aWymiary[ iNrWymiaru - 1 ]+= iWartoscPrzesuniecia;
		}
	/**
	 * Uproszczona wersja metody {@link #przesunWymiar(int, int)} pozwalajaca przesunac pozycje obiektu na pierwszym wymiarze.
	 * 
	 * @param iWartoscPrzesuniecia Wartosc przesuniecia pozycji na pierwszym wymiarze.
	 */
	public void przesunX(int iWartoscPrzesuniecia)
		{
		try
			{
			przesunWymiar(1, iWartoscPrzesuniecia);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Uproszczona wersja metody {@link #przesunWymiar(int, int)} pozwalajaca przesunac pozycje obiektu na drugim wymiarze.
	 * 
	 * @param iWartoscPrzesuniecia Wartosc przesuniecia pozycji na drugim wymiarze.
	 */
	public void przesunY(int iWartoscPrzesuniecia)
		{
		try
			{
			przesunWymiar(2, iWartoscPrzesuniecia);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Uproszczona wersja metody {@link #przesunWymiar(int, int)} pozwalajaca przesunac pozycje obiektu na trzecim wymiarze.
	 * 
	 * @since 1.2
	 * @param iWartoscPrzesuniecia Wartosc przesuniecia pozycji na trzecim wymiarze.
	 */
	public void przesunZ(int iWartoscPrzesuniecia)
		{
		try
			{
			przesunWymiar(3, iWartoscPrzesuniecia);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Przeslonieta wersja metody klasy Object tworzaca kopie obiektu.
	 * 
	 * @since 1.2
	 * @return Zwraca referencje do utworzonej kopii obiektu.
	 */
	@Override public Object clone()
		{
		Pozycja oRef = new Pozycja(iLiczbaWymiarow);
		try
			{
			for (int i = 1; i <= iLiczbaWymiarow; ++i)
				oRef.setWymiar(i, getWymiar(i));
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		return oRef;
		}
	/**
	 * Przeslonieta wersja metody klasy Object porownujaca dwa obiekty Pozycja.
	 * 
	 * @param oObj Obiekt do porownania.
	 * @return Zwraca TRUE, jesli obydwa obiekty sa tego samego typu, maja taka sama ilosc wymiarow
	 * i takie same pozycje na wszystkich wymiarach, w przeciwnym wypadku zwraca FALSE. 
	 */
	@Override public boolean equals(Object oObj)
		{
		if (oObj == null)
			return false;
		
		if (getClass() != oObj.getClass())
			return false;
		
		Pozycja oPozycja = (Pozycja)oObj;
		
		if (oPozycja.iLiczbaWymiarow != iLiczbaWymiarow)
			return false;
		for (int i = 0; i < iLiczbaWymiarow; ++i)
			if (aWymiary[i] != oPozycja.aWymiary[i])
				return false;
		return true;
		}
	}
