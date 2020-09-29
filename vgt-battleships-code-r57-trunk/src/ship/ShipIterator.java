package ship;

import exceptions.ParametrException;
import exceptions.DevException;
import tools.Position;

/**
 * Klasa kontener przechowujaca statki gracza.<br />
 * 
 * <p>
 * aktualizacje:<br />
 * 1.2<br />
 * - dodanie parametru bProsteLinie do metody {@link #weryfikujRozmieszczenie(boolean)}.<br />
 * </p>
 * 
 * @author VGT
 * @version 1.2
 */
public class ShipIterator
	{
	/**
	 * Referencja do obiektu planszy, na ktorej umieszczone sa przechowywane statki.
	 */
	private Board oPlansza;
	/**
	 * Tablica przechowujaca statki.
	 */
	private Ship[] aStatki;
	/**
	 * Ilosc statkow przechowywana aktualnie w obiekcie.
	 */
	private int iIloscStatkow;
	/**
	 * Obiekt przechowujacy wspolrzedne ostatniego obslugiwanego strzalu.
	 */
	private Position oOstatniStrzal;
	/**
	 * Konstruktor domyslny.
	 * 
	 * @param oPlansza Referencja do obiektu planszy, na ktorej umieszczone zostana statki.
	 */
	public ShipIterator(Board oPlansza)
		{
		this.oPlansza = oPlansza;
		aStatki = new Ship[0];
		iIloscStatkow = 0;
		oOstatniStrzal = new Position(2);
		oOstatniStrzal.setX(-1);
		oOstatniStrzal.setY(-1);
		}
	/**
	 * Wyswietlenie listy statkow przechowywanych przez obiekt na standardowym wyjsciu.
	 */
	@Override public String toString()
		{
		String sReturn = "StatekIterator\n";
		sReturn+= "Ilosc statkow: " + iIloscStatkow + "\n";
		sReturn+= "=================\n";
		for (int i = 0; i < iIloscStatkow; ++i)
			sReturn+= aStatki[i] + "\n";
		return sReturn;
		}
	/**
	 * Metoda zwraca referencje do obiektu statku o podanym numerze.
	 * 
	 * @param iNumer Numer statku, ktory ma byc zwrocony (liczone od 1).
	 * @return Zwraca referencje do statku o podanym numerze.
	 * @throws ParametrException Wyrzuca wyjatek, jesli numer statku jest mniejszy, lub rowny 0,
	 * lub wiekszy od ilosci statkow przechowywanych w obiekcie.
	 */
	public Ship getStatek(int iNumer) throws ParametrException
		{
		if (iNumer > iIloscStatkow || iNumer <= 0)
			throw new ParametrException("iNumer = " + iNumer);
		return aStatki[iNumer - 1];
		}
	/**
	 * Zwraca wspolrzedne na ktorych jest umieszczone pole o numerze przekazanym w drugim parametrze
	 * nalezace do statku o numerze przekazanym w pierwszym parametrze.
	 * 
	 * @param iNrStatku Numer statku, ktorego pole ma byc zwrocone (liczone od 1).
	 * @param iNrPola Numer pola, ktore ma byc zwrocone (liczone od 1).
	 * @return Zwraca obiekt zawierajacy wspolrzedne pobieranego pola.
	 * @throws ParametrException Wyrzuca wyjatek, jesli numer statku, lub nr pola sa poza dostepnym zakresem.
	 */
	public Position getPole(int iNrStatku, int iNrPola) throws ParametrException
		{
		if (iNrStatku > iIloscStatkow || iNrStatku <= 0)
			throw new ParametrException("iNrStatku = " + iNrStatku);
		return aStatki[ iNrStatku - 1 ].getPole(iNrPola);
		}
	/**
	 * Zwraca referencje do obiektu planszy, na ktorej sa umieszczone statki przechowywane przez obiekt.
	 * 
	 * @return Referencja do planszy.
	 */
	public Board getPlansza()
		{
		return oPlansza;
		}
	/**
	 * Zwraca wspolrzedne ostatniego strzalu.
	 * 
	 * @return Wspolrzedne ostatniego strzalu.
	 */
	public Position getOstatniStrzal()
		{
		return oOstatniStrzal;
		}
	/**
	 * Zwraca ilosc statkow przechowywanych aktualnie w obiekcie
	 * (nie ma znaczenia, czy statki zostaly umieszczone na planszy).
	 * 
	 * @return Ilosc statkow.
	 */
	public int getIloscStatkow()
		{
		return iIloscStatkow;
		}
	/**
	 * Zwraca ilosc statkow przechowywanych aktualnie w obiekcie o rozmiarze podanym w parametrze
	 * (nie ma znaczenia, czy statki zostaly umieszczone na planszy).
	 * 
	 * @param iRozmiar Rozmiar statkow, ktore maja byc policzone.
	 * @return Ilosc statkow o podanym rozmiarze.
	 */
	public int getIloscStatkow(int iRozmiar)
		{
		int iIlosc = 0;
		for (int i = 0; i < iIloscStatkow; ++i)
			if (aStatki[i].getRozmiar() == iRozmiar)
				++iIlosc;
		return iIlosc;
		}
	/**
	 * Metoda zwraca ilosc trafionych, ale nie zatopionych statkow.
	 * 
	 * @return Ilosc trafionych, ale nie zatopionych statkow.
	 */
	public int getIloscTrafionychStatkow()
		{
		int iTrafioneNiezatopione = 0;
		for (Ship oStatek: aStatki)
			{
			if (oStatek.getTrafiony() == true && oStatek.getZatopiony() == false)
				++iTrafioneNiezatopione;
			}
		return iTrafioneNiezatopione;
		}
	/**
	 * Metoda zwraca ilosc zatopionych statkow.
	 * 
	 * @return Ilosc zatopionych statkow.
	 */
	public int getIloscZatopionychStatkow()
		{
		int iZatopione = 0;
		for (Ship oStatek: aStatki)
			{
			if (oStatek.getZatopiony() == true)
				++iZatopione;
			}
		return iZatopione;
		}
	/**
	 * Zwraca informacje o ilosci statkow, ktore jeszcze nie zostaly trafione.
	 * 
	 * @return Ilosc nietrafionych statkow.
	 * @since 1.1 
	 */
	public int getIloscNieuszkodzonychStatkow()
		{
		return getIloscStatkow() - getIloscTrafionychStatkow() - getIloscZatopionychStatkow();
		}
	/**
	 * Zwraca rozmiar, jaki ma najwiekszy statek aktualnie przechowywany w kontenerze.
	 */
	public int getMaxRozmiarStatku()
		{
		int iMax = 0;
		for (int i = 0; i < iIloscStatkow; ++i)
			if (aStatki[i].getRozmiar() > iMax)
				iMax = aStatki[i].getRozmiar();
		return iMax;
		}
	/**
	 * Metoda oblicza laczna ilosc pol zajmowanych na planszy przez poszczegolne statki.<br />
	 * 
	 * Nie ma znaczenia, czy pola zostaly umieszczone na planszy - metoda oblicza wymagana a nie rzeczywista ilosc pol na planszy
	 * zajmowana przez statki.
	 * 
	 * @return Laczny rozmiar wszystkich statkow.
	 */
	public int getLacznyRozmiarStatkow()
		{
		try
			{
			int iRozmiar = 0;
			for (int i = 1; i <= getIloscStatkow(); ++i)
				iRozmiar+= getStatek(i).getRozmiar();
			return iRozmiar;
			}
		catch (ParametrException e)
			{
			throw new DevException(e);
			}
		}
	/**
	 * Metoda oblicza laczna ilosc trafionych pol wszystkich statkow przechowywanych przez kontener.
	 * 
	 * @return Laczna ilosc trafionych pol we wszystkich statkach.
	 */
	public int getLacznaIloscTrafien()
		{
		int iTrafienia = 0;
		try
			{
			for (int i = 1; i <= iIloscStatkow; ++i)
				iTrafienia+= getStatek(i).getIloscTrafien();
			}
		catch (ParametrException e)
			{
			throw new DevException(e);
			}
		return iTrafienia;
		}
	/**
	 * Metoda pozwala ustawic wspolrzedne na planszy dla wskazanego pola statku.
	 * 
	 * @param iNrStatku Numer statku, dla ktorego sa ustawiane wspolrzedne pola (liczone od 1).
	 * @param iNrPola Numer pola danego statku, dla ktorego sa ustawiane wspolrzedne (liczone od 1).
	 * @param iX Wspolrzedna X pozycji na planszy, na ktora ma byc ustawione pole (liczone od 0).
	 * @param iY Wspolrzedna Y pozycji na planszy, na ktora ma byc ustawione pole (liczone od 0).
	 * @throws ParametrException Wyrzuca wyjatek w przypadku przekroczenia zakresu numeracji statkow, pol danego statku, lub podania
	 * wspolrzednych poza zakresem planszy.
	 */
	public void setPole(int iNrStatku, int iNrPola, int iX, int iY) throws ParametrException
		{
		if (iNrStatku > iIloscStatkow || iNrStatku <= 0)
			throw new ParametrException("iNrStatku = " + iNrStatku);
		if (iX >= oPlansza.getSzerokosc() || iX < -1)
			throw new ParametrException("iX = " + iX);
		if (iY >= oPlansza.getWysokosc() || iY < -1)
			throw new ParametrException("iY = " + iY);
		aStatki[ iNrStatku - 1 ].setPole(iNrPola, iX, iY);
		}
	/**
	 * Metoda ustawia wszystkie pola dla wszystkich statkow na pozycje startowa (-1, -1).
	 */
	public void resetujPola()
		{
		for (int i = 0; i < iIloscStatkow; ++i)
			aStatki[i].resetujPola();
		}
	/**
	 * Glowna metoda przekazujaca informacje o strzale do wszystkich obiektow ktore tego wymagaja.<br />
	 * 
	 * Informacja o strzale jest przekazywana kolejno do wszystkich statkow znajdujacych sie w kontenerze, dopoki ktorys
	 * nie przekaze informacji o udanym trafieniu. Obiekty statkow zajmuja sie obsluga tej informacji na swoje potrzeby,
	 * a takze dokonuja odpowiednich oznaczen na planszy.
	 * 
	 * @param iX Wspolrzedna X pola na planszy, na ktore jest oddany strzal.
	 * @param iY Wspolrzedna Y pola na planszy, na ktore jest oddany strzal.
	 * @return Zwraca TRUE, jesli ktorys statek zostal trafiony, lub FALSE, jesli strzal byl niecelny.
	 * @throws ParametrException Wyrzuca wyjatek, jesli podane wspolrzedne znajduja sie poza wymiarami planszy.
	 */
	public boolean strzal(int iX, int iY) throws ParametrException
		{
		if (iX >= oPlansza.getSzerokosc() || iX < 0)
			throw new ParametrException("Ix = " + iX);
		if (iY >= oPlansza.getWysokosc() || iY < 0)
			throw new ParametrException("iY = " + iY);
		if (oPlansza.getPole(iX, iY) != FieldTypeBoard.PLANSZA_POLE_PUSTE && oPlansza.getPole(iX, iY) != FieldTypeBoard.PLANSZA_STATEK)
			throw new DevException();
		oOstatniStrzal.setX(iX);
		oOstatniStrzal.setY(iY);
		for (int i = 0; i < iIloscStatkow; ++i)
			if (aStatki[i].strzal(iX, iY) == true)
				return true;
		return false;
		}
	/**
	 * Dodaje do kontenera statek o podanej wielkosci.<br />
	 * 
	 * Po utworzeniu statku wszystkie jego pola sa ustawione na domyslna pozycje (-1, -1).
	 * 
	 * @param iRozmiar Rozmiar tworzonego statku.
	 */
	public void dodajStatek(int iRozmiar)
		{
		//utworzenie nowej tablicy statkow i przepisanie referek dotychczasowych do nowej tablicy
		Ship[] aNoweStatki = new Ship[ iIloscStatkow + 1 ];
		for (int i = 0; i < iIloscStatkow; ++i)
			aNoweStatki[i] = aStatki[i];
		//utworzenie nowego statku
		Ship oObj = new Ship(iRozmiar, oPlansza);
		aNoweStatki[iIloscStatkow] = oObj;
		++iIloscStatkow;
		aStatki = null;
		aStatki = aNoweStatki;
		}
	/**
	 * Usuwa statek o podanym numerze.
	 * 
	 * @param iNumer Numer statku do usuniecia (liczone od 1).
	 * @throws ParametrException Wyrzuca wyjatek, jesli numer statku jest poza zakresem dostepnych statkow.
	 */
	public void usunStatek(int iNumer) throws ParametrException
		{
		if (iNumer > iIloscStatkow || iNumer <= 0)
			throw new ParametrException("iNumer = " + iNumer);
		//utworzenie nowej tablicy statkow i przepisanie referek z pominieciem usuwanego
		Ship[] aNoweStatki = new Ship[ iIloscStatkow - 1 ];
		int iLocalIndex = 0;
		for (int i = 0; i < iIloscStatkow; ++i)
			{
			if (i + 1 == iNumer)
				aStatki[i] = null;
			else
				aNoweStatki[iLocalIndex++] = aStatki[i];
			}
		--iIloscStatkow;
		aStatki = null;
		aStatki = aNoweStatki;
		}
	/**
	 * Metoda sprawdza, czy wszystkie statki zostaly umieszczone na planszy i czy ich rozmieszczenie jest zgodne z zasadami gry.<br />
	 * 
	 * Sprawdzane sa kolejno: czy wszystkie pola znajduja sie na planszy, czy pola sa polaczone w jeden element,
	 * oraz czy zadne z pol nie styka sie bokiem lub naroznikiem z innym statkiem.
	 *
	 * aktualizacje:<br />
	 * 
	 * 1.2 - dodanie pierwszego parametru
	 * 
	 * @param bProsteLinie Okresla, czy statki moga byc tylko pionowymi/poziomymi liniami.
	 * @return Zwraca TRUE w przypadku prawidlowego rozmieszczenia statkow, lub FALSE, jesli zostal znaleziony blad.
	 */
	public boolean weryfikujRozmieszczenie(boolean bProsteLinie)
		{
		try
			{
			//petla wykonywana dla kazdego kolejnego statku
			for (int iNrStatku = 1; iNrStatku <= iIloscStatkow; ++iNrStatku)
				{
				Ship oStatek = getStatek(iNrStatku);
				ShipVerification oWeryfikator = new ShipVerification();
				oWeryfikator.importujStatek(oStatek);
				//sprawdzenie, czy wszystkie pola znajduja sie na planszy
				if (oWeryfikator.polaNaPlanszy() == false)
					return false;
				//sprawdzenie, czy wszystkie pola tworza jednolita strukture (stykaja sie ze soba)
				if (oWeryfikator.polaPolaczone(bProsteLinie) == false)
					return false;
				//sprawdzenie, czy statek nie styka sie bokiem, lub naroznikiem z innym statkiem
				if (oWeryfikator.brakSasiadow() == false)
					return false;
				}
			}
		catch (ParametrException e)
			{
			throw new DevException(e);
			}
		return true;
		}
	}
