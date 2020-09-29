package ship;

import java.util.ArrayList;
import java.util.Random;
import exceptions.ParametrException;
import exceptions.DevException;
import tools.Position;

/**
 * Klasa zajmuje sie losowym rozmieszczeniem statkow na planszy.<br />
 *
 * <p>
 * aktualizacje:<br />
 * 1.1<br />
 * - dodanie parametru bProsteLinie do metody {@link #rozmiescStatki(StatekIterator, boolean)}.<br />
 * </p>
 * 
 * @author VGT
 * @version 1.1
 */
public class ShipPositioner
	{
	/**
	 * Kontener, ktorego statki maja byc rozmieszczone na planszy.
	 */
	private ShipIterator oStatki;
	/**
	 * Plansza nalezaca do danego kontenera statkow.
	 */
	private Board oPlansza;
	/**
	 * Generator liczb losowych.
	 */
	private Random oRand;
	/**
	 * Konstruktor domyslny.
	 */
	public ShipPositioner()
		{
		oStatki = null;
		oPlansza = null;
		oRand = new Random();
		}
	/**
	 * @deprecated
	 */
	public boolean rozmiescStatki(ShipIterator oStatki) throws ParametrException
		{
		return rozmiescStatki(oStatki, false);
		}
	/**
	 * Glowna metoda rozpoczynajaca procedure rozmieszczania statkow na planszy.<br />
	 * 
	 * Udane rozmieszczenie statkow nie jest gwarantowane. Im mniej miejsca na planszy i/lub wiecej statkow, tym wieksze szanse
	 * na blad podczas rozmieszczenie statkow. Jesli rozmieszczenie statkow jest niemozliwe do zrealizowania, metoda zwraca wartosc FALSE
	 * i wszelkie czesciowe rozmieszczenie statkow jest zerowane.<br />
	 * 
	 * Dla standardowej gry (plansza 10x10 i 10 statkow o rozmiarach od 1 do 4)
	 * aktualna skutecznosc rozmieszczenia statkow jest na poziomie 99,93%.<br />
	 * 
	 * aktualizacje:<br />
	 * 
	 * 1.1 - dodanie drugiego parametru.
	 * 
	 * @param oStatki Kontener z utworzonymi statkami, ktore maja byc rozmieszczone na planszy.
	 * @param bProsteLinie Okresla, czy statki maja byc tylko pionowymi / poziomymi liniami.
	 * @return Zwraca TRUE, jesli statki zostaly prawidlowo rozmieszczone, lub FALSE jesli wystapil blad.
	 * @throws ParametrException Wyrzuca wyjatek, jesli kontener nie zawiera zadnych statkow.
	 */
	public boolean rozmiescStatki(ShipIterator oStatki, boolean bProsteLinie) throws ParametrException
		{
		if (oStatki.getIloscStatkow() == 0)
			throw new ParametrException("oStatki.iIloscStatkow = 0");
		try
			{
			importujStatki(oStatki);
			this.oStatki.resetujPola();
			//ustalenie rozmiaru najwieszego statku
			int iMaxRozmiar = 0;
			for (int i = 1; i <= this.oStatki.getIloscStatkow(); ++i)
				if (oStatki.getStatek(i).getRozmiar() > iMaxRozmiar)
					iMaxRozmiar = this.oStatki.getStatek(i).getRozmiar();
			//rozmieszczenie statkow rozpoczynajac od najwiekszych
			while (iMaxRozmiar > 0)
				{
				for (int i = 1; i <= this.oStatki.getIloscStatkow(); ++i)
					if (oStatki.getStatek(i).getRozmiar() == iMaxRozmiar)
						{
						int iPodejscie = 1; // ktora proba umieszczenia statku na planszy
						boolean bUmieszczony = false;
						while (bUmieszczony == false)
							{
							try
								{
								umiescStatekNaPlanszy(oStatki.getStatek(i), bProsteLinie);
								bUmieszczony = true;
								}
							catch (StatkiPozycjonerException e)
								{
								if (iPodejscie >= 3)
									throw new StatkiPozycjonerException();
								++iPodejscie;
								oStatki.getStatek(i).resetujPola();
								}
							}
						}
				--iMaxRozmiar;
				}
			}
		catch (StatkiPozycjonerException e)
			{
			oStatki.resetujPola();
			return false;
			}
		return true;
		}
	/**
	 * Zaladowanie obiektow statkow i planszy do lokalnych wlasciwosci obiektu.
	 * 
	 * @param oStatki Kontener statkow wymagajacy rozmieszczenia statkow na planszy.
	 */
	private void importujStatki(ShipIterator oStatki)
		{
		this.oStatki = oStatki;
		this.oPlansza = oStatki.getPlansza();
		}
	/**
	 * Losowo umieszcza pola przekazanego w obiekcie statku na planszy.
	 * 
	 * @param oStatek Statek, ktorego pola nalezy umiescic na planszy.
	 * @throws StatkiPozycjonerException Wyrzuca wyjatek, jesli umieszczenie statku na planszy zakonczylo sie niepowodzeniem.
	 */
	private void umiescStatekNaPlanszy(Ship oStatek, boolean bProsteLinie) throws StatkiPozycjonerException
		{
		try
			{
			for (int i = 1; i <= oStatek.getRozmiar(); ++i)
				{
				if (i == 1)
					{
					//pierwsze pole
					Position oPustePole = wylosujPustePole();
					oStatek.setPole(i, oPustePole.getX(), oPustePole.getY());
					}
				else
					{
					//kolejne pola
					//tworzenie listy kandydatow na kolejne pola
					int iIloscKandydatow = 0;
					ArrayList<Position> oKandydaci = new ArrayList<Position>();
					for (int j = 1; j < i; ++j)
						{
						Position oPole = oStatek.getPole(j);
						for (int k = -1; k <= 1; ++k)
							for (int l = -1; l <= 1; ++l)
								if (k + l == -1 || k + l == 1)
									if (oPole.getX() + k >= 0 && oPole.getX() + k < oPlansza.getSzerokosc()
										&& oPole.getY() + l >= 0 && oPole.getY() + l < oPlansza.getWysokosc()
										&& oPlansza.getPole(oPole.getX() + k, oPole.getY() + l) == FieldTypeBoard.PLANSZA_POLE_PUSTE
										)
										{
										Position oKandydat = new Position(2);
										oKandydat.setX(oPole.getX() + k);
										oKandydat.setY(oPole.getY() + l);
										if (weryfikujKandydata(oKandydat, oStatek))
											{
											++iIloscKandydatow;
											oKandydaci.add(oKandydat);
											}
										}
										
						}
					//jesli statki maja byc liniami, odrzucenie pol niepasujacych
					if (bProsteLinie == true && i > 2)
						{
						//sprawdzenie, czy statek jest pionowy, czy poziomy i odrzucenie pol niepasujacych
						if (oStatek.getPole(1).getX() == oStatek.getPole(2).getX())
							{
							//wspolrzedne X musza byc takie same
							for (int j = oKandydaci.size() - 1; j >= 0; --j)
								if (oStatek.getPole(1).getX() != oKandydaci.get(j).getX())
									{
									--iIloscKandydatow;
									oKandydaci.remove(j);
									}
							}
						else if (oStatek.getPole(1).getY() == oStatek.getPole(2).getY())
							{
							//wspolrzedne Y musza byc takie same
							for (int j = oKandydaci.size() - 1; j >= 0; --j)
								if (oStatek.getPole(1).getY() != oKandydaci.get(j).getY())
									{
									--iIloscKandydatow;
									oKandydaci.remove(j);
									}
							}
						else
							throw new DevException("nie mozna ustalic kierunku statku");
						}
					if (iIloscKandydatow == 0)
						throw new StatkiPozycjonerException();
					//wylosowanie kandydata
					int iLos = oRand.nextInt(iIloscKandydatow);
					//ustawienie pola statku na wylosowanym kandydacie
					oStatek.setPole(i, oKandydaci.get(iLos).getX(), oKandydaci.get(iLos).getY());
					}
				}
			}
		catch (ParametrException e)
			{
			throw new DevException(e);
			}
		}
	/**
	 * Metoda zwraca wspolrzedne losowo wybranego pustego pola na planszy,
	 * ktore nadaje sie do rozpoczecia nowego statku (nie posiada zadnych sasiadow).
	 * 
	 * @return Zwraca losowe puste pole z planszy.
	 */
	private Position wylosujPustePole() throws StatkiPozycjonerException
		{
		try
			{
			Position oWspolrzedne = new Position(2);
			//policzenie pustych pol na planszy
			int iIloscPustych = 0;
			for (int i = 0; i < oPlansza.getSzerokosc(); ++i)
				for (int j = 0; j < oPlansza.getWysokosc(); ++j)
					if (oPlansza.getPole(i, j) == FieldTypeBoard.PLANSZA_POLE_PUSTE)
						{
						Position oKandydat = new Position(2);
						oKandydat.setX(i);
						oKandydat.setY(j);
						if (weryfikujKandydata(oKandydat, null))
							++iIloscPustych;
						}
			//blad jesli pustych pol nie ma
			if (iIloscPustych == 0)
				throw new StatkiPozycjonerException();
			int iWylosowanePole = oRand.nextInt(iIloscPustych) + 1;
			//ponowne przejscie po planszy i zwrocenie pustego pola o wylosowanym numerze
			for (int i = 0; i < oPlansza.getSzerokosc(); ++i)
				for (int j = 0; j < oPlansza.getWysokosc(); ++j)
					if (oPlansza.getPole(i, j) == FieldTypeBoard.PLANSZA_POLE_PUSTE)
						{
						Position oKandydat = new Position();
						oKandydat.setX(i);
						oKandydat.setY(j);
						if (weryfikujKandydata(oKandydat, null))
							--iWylosowanePole;
						if (iWylosowanePole == 0)
							{
							oWspolrzedne.setX(i);
							oWspolrzedne.setY(j);
							return oWspolrzedne;
							}
						}
			throw new DevException("koniec planszy");
			}
		catch (ParametrException e)
			{
			throw new DevException(e);
			}
		}
	/**
	 * Metoda sprawdza, czy pole o podanych wspolrzednych nie posiada w sasiedztwie zadnych statkow.
	 * 
	 * @param oPole Wspolrzedne pola do sprawdzenia.
	 * @param oStatek Jesli jest porzekazany obiekt statku, jest to informacja,
	 * ze moga wystepowac pola tego statku i kandydat jest nadal prawidlowy.
	 * @return Zwraca TRUE, jesli pole nie ma niechcianych sasiadow, lub false w przeciwnym wypadku.
	 */
	private boolean weryfikujKandydata(Position oPole, Ship oStatek)
		{
		try
			{
			for (int i = -1; i <= 1; ++i)
				for (int j = -1; j <= 1; ++j)
					if (oPole.getX() + i >= 0 && oPole.getX() + i < oPlansza.getSzerokosc()
						&& oPole.getY() + j >= 0 && oPole.getY() + j < oPlansza.getWysokosc()
						&& oPlansza.getPole(oPole.getX() + i, oPole.getY() + j) == FieldTypeBoard.PLANSZA_STATEK
						)
						{
						if (oStatek == null)
							return false;
						else if(oStatek.getNumerPola(oPole.getX() + i, oPole.getY() + j) == 0)
							return false;
						}
			return true;
			}
		catch (ParametrException e)
			{
			throw new DevException(e);
			}
		}
	}

/**
 * Wyjatek wyrzucany w przypadku wystapienia bledow podczas umieszczania statku na planszy.<br />
 * 
 * Jego wystapienie informuje glowna metode pozycjonujaca o tym, ze rozmieszczenie statkow zakonczylo sie
 * bledem, ktorego nie mozna rozwiazac.
 * 
 * @author VGT
 * @version 1.0
 */
class StatkiPozycjonerException extends Exception {}
