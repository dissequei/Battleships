package ship;

import exceptions.ParametrException;
import exceptions.DevException;
import tools.Position;

/**
 * Klasa reprezentujaca statek na planszy.
 * 
 * @author VGT
 * @version 1.0
 */
public class Ship
	{
	/**
	 * Referencja do obiektu planszy, na ktorej bedzie umieszczony statek.
	 */
	private Board oPlansza;
	/**
	 * Rozmiar statku podany w ilosci zajmowanych przez niego pol na planszy.
	 */
	private int iRozmiar;
	/**
	 * Tablica zawierajaca wspolrzedne poszczegolnych pol statku.
	 */
	private Position[] aWspolrzedne;
	/**
	 * Tablica przechowujaca informacje o tym, czy poszczegolne pola statku zostaly trafione.
	 */
	private boolean[] aTrafienia;
	/**
	 * Ilosc trafionych pol statku.
	 */
	private int iIloscTrafien;
	/**
	 * Konstruktor domyslny - tworzy nowy statek o podanym rozmiarze.
	 * 
	 * @param iRozmiar Ilosc pol, ktore statek zajmuje na planszy.
	 * @param oPlansza Referencja do obiektu planszy, na ktorej ma byc umieszczony statek.
	 */
	public Ship(int iRozmiar, Board oPlansza)
		{
		this.oPlansza = oPlansza;
		this.iRozmiar = iRozmiar;
		aWspolrzedne = new Position[ iRozmiar ];
		//wypelnienie tablicy wspolrzednych wartosciami domyslnymi (-1, -1)
		for (int i = 0; i < iRozmiar; ++i)
			{
			aWspolrzedne[i] = new Position(2);
			aWspolrzedne[i].setX(-1);
			aWspolrzedne[i].setY(-1);
			}
		aTrafienia = new boolean[ iRozmiar ];
		for (int i = 0; i < iRozmiar; ++i)
			aTrafienia[i] = false;
		iIloscTrafien = 0;
		}
	/**
	 * Wyswietlenie informacji o statku na standardowym wyjsciu.
	 */
	@Override public String toString()
		{
		String sReturn = "Statek\n";
		sReturn+= "Rozmiar: " + iRozmiar + "\n";
		sReturn+= "Pozycja:";
		for(Position oObj: aWspolrzedne)
			sReturn+= " " + oObj;
		sReturn+= "\nTrafienia: [";
		for(boolean bTrafienie: aTrafienia)
			sReturn+= " " + bTrafienie;
		sReturn+= " ]";
		return sReturn;
		}
	/**
	 * Zwraca obiekt zawierajacy wspolrzedne pola o podanym numerze.
	 * 
	 * @param iNumerPola Numer pola ktorego wspolrzedne maja byc zwrocone (liczone od 1).
	 * @return Wspolrzedne pola o podanym numerze.
	 * @throws ParametrException Wyrzuca wyjatek, jesli numer pola jest mniejszy od 1, lub wiekszy od rozmiaru statku. 
	 */
	public Position getPole(int iNumerPola) throws ParametrException
		{
		if (iNumerPola > iRozmiar || iNumerPola <= 0)
			throw new ParametrException("iNumerPola = " + iNumerPola);
		return aWspolrzedne[ iNumerPola - 1 ];
		}
	/**
	 * Jesli na podanych wspolrzednych znajduje sie pole statku, metoda zwraca jego numer,
	 * w przeciwnym wypadku zwraca 0.
	 * 
	 * @param iX Wspolrzedna X na planszy.
	 * @param iY Wspolrzedna Y outna planszy.
	 * @return Numer pola statku (liczone od 1).
	 * @throws ParametrException Wyrzuca wyjatek, jesli podane wspolrzedne znajduje sie poza zakresem planszy.
	 */
	public int getNumerPola(int iX, int iY) throws ParametrException
		{
		if (iX + 1 > oPlansza.getSzerokosc() || iX < 0)
			throw new ParametrException("iX = " + iX);
		if (iY + 1 > oPlansza.getWysokosc() || iY < 0)
			throw new ParametrException("iY = " + iY);
		for (int i = 0; i < iRozmiar; ++i)
			if (aWspolrzedne[i].getX() == iX && aWspolrzedne[i].getY() == iY)
				return (i + 1);
		return 0;
		}
	/**
	 * Metoda zwraca rozmiar statku.
	 * 
	 * @return rozmiar statku.
	 */
	public int getRozmiar()
		{
		return iRozmiar;
		}
	/**
	 * Metoda zwraca ilosc trafionych pol statku.
	 * 
	 * @return Ilosc trafionych pol.
	 */
	public int getIloscTrafien()
		{
		return iIloscTrafien;
		}
	/**
	 * Metoda zwraca informacje, czy statek nie otrzymal zadnych trafien.
	 * 
	 * @return Zwraca TRUE, jesli zadne z pol statku nie zostalo trafione, lub FALSE,
	 * jesli jest conajmniej jedno trafienie.
	 */
	public boolean getNietkniety()
		{
		if (iIloscTrafien == 0)
			return true;
		else
			return false;
		}
	/**
	 * Metoda zwraca informacje, czy statek zostal chociaz raz trafiony.
	 * 
	 * @return Zwraca TRUE, jesli statek ma conajmniej jedno trafione pole, lub FALSE,
	 * jesli wszystkie pola sa nietkniete.
	 */
	public boolean getTrafiony()
		{
		if (iIloscTrafien > 0)
			return true;
		else
			return false;
		}
	/**
	 * Metoda zwraca informacje, czy statek zostal zatopiony, czyli wszystkie jego pola zostaly trafione.
	 * 
	 * @return Zwraca TRUE, jesli wszystkie pola statku zostaly trafione, lub FALSE,
	 * jesli choc jedno pole pozostaje nietrafione.
	 */
	public boolean getZatopiony()
		{
		if (iIloscTrafien > 0 && iIloscTrafien == iRozmiar)
			return true;
		else
			return false;
		}
	/**
	 * Metoda zwraca referencja obiektu planszy, na ktorej sie znajduje.
	 * 
	 * @return Plansza, na ktorej umieszczony jest statek.
	 */
	public Board getPlansza()
		{
		return oPlansza;
		}
	/**
	 * Pozwala ustawic wspolrzedne pola o podanym numerze.<br />
	 * 
	 * Metoda dba takze o to, aby na planszy odpowiednio oznaczyc zajmowane przez statek miejsca.
	 * 
	 * @param iNumerPola Numer pola statku (liczone od 1).
	 * @param iX Wspolrzedna X na planszy (liczone od 0).
	 * @param iY Wspolrzedna Y na planszy (liczone od 0).
	 * @throws ParametrException Wyrzuca wyjatek, jesli numer pola jest poza zakresem pol statku,
	 * gdy wspolrzedne znajduja sie poza zakresem planszy, lub gdy na danym polu planszy nie mozna umiescic statku
	 */
	public void setPole(int iNumerPola, int iX, int iY) throws ParametrException
		{
		if (iNumerPola > iRozmiar)
			throw new ParametrException("iNumerPola = " + iNumerPola);
		if (iX + 1 > oPlansza.getSzerokosc() || iX < -1)
			throw new ParametrException("iX = " + iX);
		if (iY + 1 > oPlansza.getWysokosc() || iY < -1)
			throw new ParametrException("iY = " + iY);
		if (iX >= 0 && iY >= 0 && oPlansza.getPole(iX, iY) != FieldTypeBoard.PLANSZA_POLE_PUSTE)
			throw new ParametrException("iX, iY - pole niepuste");
		if (aWspolrzedne[ iNumerPola - 1].getX() == -1 && aWspolrzedne[ iNumerPola - 1 ].getY() == -1)
			{
			//pierwsze ustawienie wspolrzednych
			if (iX >= 0 && iY >= 0)
				{
				aWspolrzedne[ iNumerPola - 1 ].setX(iX);
				aWspolrzedne[ iNumerPola - 1 ].setY(iY);
				oPlansza.setPole(iX, iY, FieldTypeBoard.PLANSZA_STATEK);
				}
			}
		else
			{
			//pole juz ma ustawione wspolrzedne
			//zerowanie wspolrzednych
			oPlansza.setPole(aWspolrzedne[ iNumerPola - 1 ].getX(), aWspolrzedne[ iNumerPola - 1 ].getY(), FieldTypeBoard.PLANSZA_POLE_PUSTE);
			aWspolrzedne[ iNumerPola - 1 ].setX(-1);
			aWspolrzedne[ iNumerPola - 1 ].setY(-1);
			//ponowne wywolanie metody
			setPole(iNumerPola, iX, iY);
			}
		}
	/**
	 * Metoda ustawia wspolrzedne wszystkich pol statku na poczatkowe wartosci domyslne (-1, -1).
	 */
	public void resetujPola()
		{
		try
			{
			for (int i = 1; i <= iRozmiar; ++i)
				setPole(i, -1, -1);
			}
		catch (ParametrException e)
			{
			throw new DevException(e);
			}
		}
	/**
	 * Metoda sprawdza czy strzal na podane wspolrzedne jest celny.<br />
	 * 
	 * Jesli tak, oznacza pole statku, jako trafione i zwraca TRUE,
	 * w przeciwnym wypadku zwraca FALSE.<br />
	 * 
	 * Metoda dba takze o prawidlowe oznaczenie ostrzeliwanych pol na planszy.<br />
	 * 
	 * @param iX Wspolrzedna X strzalu.
	 * @param iY Wspolrzedna Y strzalu.
	 * @return Zwraca TRUE w przypadku trafienia, lub FALSE, jesli strzal byl niecelny.
	 * @throws ParametrException Wyrzuca wyjatek, jesli podane wspolrzedne znajduja sie poza zakresem planszy.
	 */
	public boolean strzal(int iX, int iY) throws ParametrException
		{
		if (iX < 0 || iX >= oPlansza.getSzerokosc())
			throw new ParametrException("iX = " + iX);
		if (iY < 0 || iY >= oPlansza.getWysokosc())
			throw new ParametrException("iY = " + iY);
		try
			{
			for (int i = 0; i < iRozmiar; ++i)
				if (aWspolrzedne[i].getX() == iX && aWspolrzedne[i].getY() == iY && aTrafienia[i] == false)
					{
					//nastapilo trafienie
					aTrafienia[i] = true;
					++iIloscTrafien;
					oPlansza.setPole(iX, iY, FieldTypeBoard.PLANSZA_STRZAL_CELNY);
					if (getZatopiony() == true)
						{
						//oznaczenie pol sasiadujacych ze statkiem jako niedostepne
						for (int j = 1; j <= iRozmiar; ++j)
							{
							Position oPole = getPole(j);
							for (int k = -1; k <= 1; ++k)
								for (int l = -1; l <= 1; ++l)
									{
									Position oSasiedniePole = new Position(2);
									oSasiedniePole.setX(oPole.getX() + k);
									oSasiedniePole.setY(oPole.getY() + l);
									if (oSasiedniePole.getX() < 0 || oSasiedniePole.getX() >= oPlansza.getSzerokosc()
										|| oSasiedniePole.getY() < 0 || oSasiedniePole.getY() >= oPlansza.getWysokosc()
										)
										continue;
									if (oPlansza.getPole(oSasiedniePole.getX(), oSasiedniePole.getY()) == FieldTypeBoard.PLANSZA_POLE_PUSTE)
										oPlansza.setPole(oSasiedniePole.getX(), oSasiedniePole.getY(), FieldTypeBoard.PLANSZA_POLE_NIEDOSTEPNE);
									}
							}
						}
					return true;
					}
			oPlansza.setPole(iX, iY, FieldTypeBoard.PLANSZA_STRZAL_NIECELNY);
			return false;
			}
		catch (ParametrException e)
			{
			throw new DevException(e);
			}
		}
	/**
	 * metoda zwraca informacje, czy statek jest zatopiony
	 * 
	 * @deprecated zastapiana przez metode {@link #getZatopiony()}
	 * @return zwraca TRUE, jesli statek jest zatopiony, lub FALSE w przeciwnym wypadku
	 */
	public boolean czyZatopiony()
		{
		if (iRozmiar > iIloscTrafien)
			return false;
		else
			return true;
		}
	}
