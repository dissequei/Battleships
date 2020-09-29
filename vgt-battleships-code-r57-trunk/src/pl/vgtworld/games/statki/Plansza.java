package pl.vgtworld.games.statki;

import pl.vgtworld.exceptions.ParametrException;

/**
 * Klasa reprezentujaca plansze gry.
 * 
 * @author VGT
 * @version 1.0
 */
public class Plansza
	{
	/**
	 * Szerokosc planszy.
	 */
	private int iSzerokosc;
	/**
	 * Wysokosc planszy.
	 */
	private int iWysokosc;
	/**
	 * Ilosc pol na planszy nieznanych dla przeciwnika, czyli dostepnych do ostrzelania.<br />
	 * 
	 * Sa to pola puste, oraz pola zawierajace statek.
	 */
	private int iNieznanePola;
	/**
	 * Tablica przechowujaca stan poszczegolnych pol planszy.
	 */
	PlanszaTypPola[][] aPlansza;
	/**
	 * Konstruktor domyslny - tworzy plansze o domyslnym rozmiarze 10x10 pol.
	 */
	public Plansza()
		{
		this(10, 10);
		}
	/**
	 * Konstruktor przeciazony - pozwala utworzyc plansze w ksztalcie kwadratu o podanej dlugosci boku.
	 * 
	 * @param iRozmiar Wartosc okreslajaca szerokosc i wysokosc tworzonej planszy.
	 */
	public Plansza(int iRozmiar)
		{
		this(iRozmiar, iRozmiar);
		}
	/**
	 * Konstruktor przeciazony - pozwala utworzyc plansze o dowolnej szerokosci i wysokosci.
	 * 
	 * @param iSzerokosc Wartosc okreslajaca szerokosc tworzonej planszy.
	 * @param iWysokosc Wartosc okreslajaca wysokosc tworzonej planszy.
	 */
	public Plansza(int iSzerokosc, int iWysokosc)
		{
		this.iSzerokosc = iSzerokosc;
		this.iWysokosc = iWysokosc;
		iNieznanePola = iSzerokosc * iWysokosc;
		aPlansza = new PlanszaTypPola[ iSzerokosc ][ iWysokosc ];
		wyczysc();
		}
	/**
	 * Wyswietlenie planszy na standardowym wyjsciu.
	 */
	@Override public String toString()
		{
		String sReturn = "Plansza\n";
		sReturn+= "Rozmiar: [ " + iSzerokosc + ", " + iWysokosc + " ]\n";
		for (int j = 0; j < iWysokosc; ++j)
			{
			sReturn+= "|";
			for (int i = 0; i < iSzerokosc; ++i)
				{
				PlanszaTypPola ePole = aPlansza[i][j];
				switch (ePole)
					{
					case PLANSZA_POLE_PUSTE:
						sReturn+= "_";
						break;
					case PLANSZA_STATEK:
						sReturn+= "S";
						break;
					case PLANSZA_STRZAL_CELNY:
						sReturn+= "X";
						break;
					case PLANSZA_STRZAL_NIECELNY:
						sReturn+= "-";
						break;
					case PLANSZA_POLE_NIEDOSTEPNE:
						sReturn+= "!";
						break;
					default:
						sReturn+= "?";
						break;
					}
				sReturn+= "|";
				}
			if (j + 1 < iWysokosc)
				sReturn+="\n";
			}
		return sReturn;
		}
	/**
	 * Zwraca szerokosc planszy.
	 * 
	 * @return Rozmiar planszy w poziomie.
	 */
	public int getSzerokosc()
		{
		return iSzerokosc;
		}
	/**
	 * Zwraca wysokosc planszy.
	 * 
	 * @return Rozmiar planszy w pionie.
	 */
	public int getWysokosc()
		{
		return iWysokosc;
		}
	/**
	 * Zwraca ilosc nieznanych pol na planszy.
	 * 
	 * @return Ilosc nieznanych pol.
	 */
	public int getIloscNieznanych()
		{
		return iNieznanePola;
		}
	/**
	 * Metoda zwraca typ pola na podanych wspolrzednych.
	 * 
	 * @param iX Wspolrzedna X na planszy (liczone od 0).
	 * @param iY Wspolrzedna Y na planszy (liczone od 0).
	 * @return Zwraca typ pola na podanych wspolrzednych.
	 * @throws ParametrException Wyrzuca wyjatek, gdy wspolrzedne znajduja sie poza zakresem planszy.
	 */
	public PlanszaTypPola getPole(int iX, int iY) throws ParametrException
		{
		if (iX >= iSzerokosc || iX < 0)
			throw new ParametrException("iX = " + iX);
		if (iY >= iWysokosc || iY < 0)
			throw new ParametrException("iY = " + iY);
		return aPlansza[iX][iY];
		}
	/**
	 * Pozwala ustawic typ pola na podanych wspolrzednych.<br />
	 * 
	 * Metoda dba takze o to, aby wartosc zmiennej iNieznanePola byla aktualizowana w przypadku zmiany tej wartosci.
	 * 
	 * @param iX Wspolrzedna X na planszy (liczone od 0).
	 * @param iY Wspolrzedna Y na planszy (liczone od 0).
	 * @param eTyp Typ pola, na ktore ma byc ustawione pole o podanych wspolrzednych.
	 * @throws ParametrException Wyrzuca wyjatek, gdy wspolrzedne znajduja sie poza zakresem planszy.
	 */
	public void setPole(int iX, int iY, PlanszaTypPola eTyp) throws ParametrException
		{
		if (iX + 1 > iSzerokosc)
			throw new ParametrException("iX = " + iX);
		if (iY + 1 > iWysokosc)
			throw new ParametrException("iY = " + iY);
		PlanszaTypPola eStaryTyp = aPlansza[iX][iY];
		aPlansza[iX][iY] = eTyp;
		//ustalenie czy zmienila sie ilosc pol nieznanych i ewentualna korekta tej wartosci
		if (
			(eStaryTyp == PlanszaTypPola.PLANSZA_POLE_PUSTE || eStaryTyp == PlanszaTypPola.PLANSZA_STATEK)
			&& (eTyp == PlanszaTypPola.PLANSZA_STRZAL_NIECELNY || eTyp == PlanszaTypPola.PLANSZA_STRZAL_CELNY || eTyp == PlanszaTypPola.PLANSZA_POLE_NIEDOSTEPNE)
			)
			{
			--iNieznanePola;
			}
		else if (
			(eStaryTyp == PlanszaTypPola.PLANSZA_STRZAL_NIECELNY || eStaryTyp == PlanszaTypPola.PLANSZA_STRZAL_CELNY || eStaryTyp == PlanszaTypPola.PLANSZA_POLE_NIEDOSTEPNE)
			&& (eTyp == PlanszaTypPola.PLANSZA_POLE_PUSTE || eTyp == PlanszaTypPola.PLANSZA_STATEK)
			)
			{
			++iNieznanePola;
			}
		}
	/**
	 * Metoda pozwala na zmiane rozmiaru juz utworzonej planszy.<br />
	 * 
	 * W celu unikniecia potencjalnej utraty informacji nie nalezy stosowac na wypelnionej statkami planszy.
	 * Z tego wzgledu dodatkowo po zmianie rozmiaru jest wywolywana metoda {@link #wyczysc()}.
	 * 
	 * @param iSzerokosc Nowa szerokosc planszy.
	 * @param iWysokosc Nowa wysokosc planszy.
	 * @throws ParametrException Wyrzuca wyjatek, jesli podana szerokosc i/lub wysokosc jest mniejsza od 1.
	 */
	public void zmienRozmiar(int iSzerokosc, int iWysokosc) throws ParametrException
		{
		if (iSzerokosc < 1)
			throw new ParametrException("iSzerokosc = " + iSzerokosc);
		if (iWysokosc < 1)
			throw new ParametrException("iWysokosc = " + iWysokosc);
		this.iSzerokosc = iSzerokosc;
		this.iWysokosc = iWysokosc;
		iNieznanePola = iSzerokosc * iWysokosc;
		aPlansza = new PlanszaTypPola[ iSzerokosc ][ iWysokosc ];
		wyczysc();
		}
	/**
	 * Metoda czysci cala plansze ustawiajac typ wszystkich pol na "puste".
	 */
	public void wyczysc()
		{
		for (int i = 0; i < iSzerokosc; ++i)
			for (int j = 0; j < iWysokosc; ++j)
				aPlansza[i][j] = PlanszaTypPola.PLANSZA_POLE_PUSTE;
		iNieznanePola = iSzerokosc * iWysokosc;
		}
	}
