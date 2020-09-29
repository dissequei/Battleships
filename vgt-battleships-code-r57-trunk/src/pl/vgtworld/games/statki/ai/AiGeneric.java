package pl.vgtworld.games.statki.ai;

import java.util.ArrayList;
import java.util.Random;
import pl.vgtworld.exceptions.ParametrException;
import pl.vgtworld.exceptions.ProgramistaException;
import pl.vgtworld.games.statki.Plansza;
import pl.vgtworld.games.statki.PlanszaTypPola;
import pl.vgtworld.games.statki.StatekIterator;
import pl.vgtworld.tools.Pozycja;

/**
 * Klasa abstrakcyjna zawierajaca zestaw metod uzytecznych do budowania klas sztucznej inteligencji.<br />
 *
 * <p>
 * aktualizacje:<br />
 * 1.1<br />
 * - dodanie metody metody {@link #setProsteLinie(boolean)}<br />
 * </p>
 * 
 * @author VGT
 * @version 1.1
 */
public abstract class AiGeneric
	{
	/**
	 * Kontener statkow nalezacych do gracza sterowanego przez komputer
	 * (uzyteczne w bardziej rozbudowanych wersjach AI do ustalania, jak daleko od potencjalnej przegranej jest komputer).
	 */
	protected StatekIterator oStatki;
	/**
	 * Przechowuje wspolrzedne ostatniego celnego strzalu.<br />
	 * 
	 * Wartosc tego pola nalezy uzupelniac we wszystkich metodach oddajacych strzal na plansze przeciwnika.
	 */
	protected Pozycja oOstatnieTrafienie;
	/**
	 * Kontener wykorzystywany do przechowywania wspolrzednych dla udanych trafien w poprzednich rundach.
	 * 
	 * Na jego podstawie mozliwe jest szukanie kolejnych pol trafionego statku w celu jego dalszego ostrzalu.
	 */
	protected ArrayList<Pozycja> oUzyteczneTrafienia;
	/**
	 * Wlasciwosc okresla, czy statki na planszy moga byc tylko pionowymi/poziomymi liniami (TRUE),
	 * czy tez moga miec dowolne ksztalty (FALSE, domyslnie).
	 */
	protected boolean bProsteLinie;
	/**
	 * Generator liczb losowych.
	 */
	protected Random oRand;
	/**
	 * Konstruktor.
	 * 
	 * @param oStatki Kontener statkow nalezacych do gracza sterowanego przez dany obiekt Ai.
	 */
	public AiGeneric(StatekIterator oStatki)
		{
		this.oStatki = oStatki;
		bProsteLinie = false;
		oRand = new Random();
		oOstatnieTrafienie = new Pozycja(2);
		oOstatnieTrafienie.setX(-1);
		oOstatnieTrafienie.setY(-1);
		oUzyteczneTrafienia = new ArrayList<Pozycja>();
		}
	/**
	 * Metoda pozwala ustawic wlasciwosc okreslajaca dozwolny ksztalt statkow.
	 * 
	 * @param bWartosc Okresla, czy statki moga byc tylko pionowymi/poziomymi liniami.
	 * @since 1.1
	 */
	public void setProsteLinie(boolean bWartosc)
		{
		bProsteLinie = bWartosc;
		}
	/**
	 * Najprostrza mozliwa implementacja wyboru pola do ostrzelania. Metoda wyszukuje wszystkie pola, na ktore mozna oddac strzal
	 * i losowo wybiera jedno z nich.<br />
	 * 
	 * Informacje na temat wspolrzednych oddanego strzalu sa przekazywane do metody strzal() kontenera
	 * statkow przekazanego w parametrze i tam jest zrealizowana pelna obsluga strzalu.
	 * 
	 * @param oStatkiPrzeciwnika Kontener statkow przeciwnika, ktory ma byc ostrzelany.
	 * @return Zwraca TRUE w przypadku trafienia ktoregos ze statkow, lub FALSE, jesli strzal byl niecelny.
	 */
	protected boolean strzalLosowy(StatekIterator oStatkiPrzeciwnika)
		{
		try
			{
			Pozycja oWylosowanePole = losujPole(oStatkiPrzeciwnika.getPlansza());
			boolean bTrafienie = oStatkiPrzeciwnika.strzal(oWylosowanePole.getX(), oWylosowanePole.getY());
			if (bTrafienie == true)
				{
				//zapisanie celnego strzalu w tablicy trafien
				Pozycja oTrafienie = new Pozycja(2);
				oTrafienie.setX(oWylosowanePole.getX());
				oTrafienie.setY(oWylosowanePole.getY());
				oUzyteczneTrafienia.add(oTrafienie);
				}
			return bTrafienie;
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Metoda wybiera losowo jedno z zapisanych wczesniejszych trafien i sprawdza, czy mozna ostrzelac ktores z sasiadujacych pol.<br />
	 * 
	 * Jesli tak, wybiera jedno z pol do ostrzelania. Jesli nie, usuwa pole z listy, wybiera losowo kolejne zapisane trafienie
	 * i powtarza proces. Jesli wyczerpane zostana zapisane trafienia, wywolywana jest metoda strzalLosowy()
	 * 
	 * @param oStatkiPrzeciwnika Kontener statkow przeciwnika.
	 * @return Zwraca TRUE, w przypadku trafienia statku, lub FALSE, jesli strzal byl niecelny.
	 */
	protected boolean strzalSasiadujacy(StatekIterator oStatkiPrzeciwnika)
		{
		//przygotowanie kontenera przechowujacego do 4 sasiednich pol, ktore nadaja sie do kolejnego strzalu
		ArrayList<Pozycja> oSasiedniePola = new ArrayList<Pozycja>(4);
		//petla wyszukujaca we wczesniejszych trafieniach pola do oddania kolejnego strzalu
		while (oUzyteczneTrafienia.size() > 0)
			{
			//wylosowanie pola do przetestowania
			int iLosowanePole = oRand.nextInt(oUzyteczneTrafienia.size());
			Pozycja oWybranePole = oUzyteczneTrafienia.get(iLosowanePole);
			
			try
				{
				//wczytanie wspolrzednych 4 sasiadow i sprawdzenie, czy sa to pola puste, lub zawierajace statek
				for (int i = -1; i <= 1; ++i)
					for (int j = -1; j <= 1; ++j)
						if (
							oWybranePole.getX() + i >= 0 && oWybranePole.getX() + i < oStatkiPrzeciwnika.getPlansza().getSzerokosc()
							&& oWybranePole.getY() + j >= 0 && oWybranePole.getY() + j < oStatkiPrzeciwnika.getPlansza().getWysokosc()
							&& (i + j == -1 || i + j == 1)
							)
							{
							if (oStatkiPrzeciwnika.getPlansza().getPole(oWybranePole.getX() + i, oWybranePole.getY() + j) == PlanszaTypPola.PLANSZA_POLE_PUSTE
								|| oStatkiPrzeciwnika.getPlansza().getPole(oWybranePole.getX() + i, oWybranePole.getY() + j) == PlanszaTypPola.PLANSZA_STATEK
								)
								{
								Pozycja oPrawidlowe = new Pozycja(2);
								oPrawidlowe.setX(oWybranePole.getX() + i);
								oPrawidlowe.setY(oWybranePole.getY() + j);
								oSasiedniePola.add(oPrawidlowe);
								}
							}
				
				if (bProsteLinie == true)
					{
					boolean bPionowy = false;
					boolean bPoziomy = false;
					for (int i = -1; i <= 1; ++i)
						for (int j = -1; j <= 1; ++j)
							if (
								oWybranePole.getX() + i >= 0 && oWybranePole.getX() + i < oStatkiPrzeciwnika.getPlansza().getSzerokosc()
								&& oWybranePole.getY() + j >= 0 && oWybranePole.getY() + j < oStatkiPrzeciwnika.getPlansza().getWysokosc()
								&& (i + j == -1 || i + j == 1)
								)
								{
								if (oStatkiPrzeciwnika.getPlansza().getPole(oWybranePole.getX() + i, oWybranePole.getY() + j) == PlanszaTypPola.PLANSZA_STRZAL_CELNY)
									{
									if (i == 0)
										bPionowy = true;
									if (j == 0)
										bPoziomy = true;
									}
								}
					if (bPionowy == true && bPoziomy == true)
						throw new ProgramistaException();
					if (bPionowy == true)
						{
						for (int i = oSasiedniePola.size() - 1; i >= 0; --i)
							if (oSasiedniePola.get(i).getX() != oWybranePole.getX())
								oSasiedniePola.remove(i);
						}
					if (bPoziomy == true)
						{
						for (int i = oSasiedniePola.size() - 1; i >= 0; --i)
							if (oSasiedniePola.get(i).getY() != oWybranePole.getY())
								oSasiedniePola.remove(i);
						}
					}
				
				if (oSasiedniePola.size() > 0)
					{
					//sa pola prawidlowe do oddania kolejnego strzalu
					int iWylosowanySasiad = oRand.nextInt(oSasiedniePola.size());
					//oddanie strzalu na wspolrzedne weybranego pola
					boolean bStrzal;
					bStrzal = oStatkiPrzeciwnika.strzal(oSasiedniePola.get(iWylosowanySasiad).getX(), oSasiedniePola.get(iWylosowanySasiad).getY());
					if (bStrzal == true)
						{
						//zapisanie celnego strzalu w tablicy trafien
						Pozycja oTrafienie = new Pozycja(2);
						oTrafienie.setX( oSasiedniePola.get(iWylosowanySasiad).getX() );
						oTrafienie.setY( oSasiedniePola.get(iWylosowanySasiad).getY() );
						oUzyteczneTrafienia.add(oTrafienie);
						}
					return bStrzal;
					}
				else
					{
					//brak prawidlowych pol. usuniecie trafienia z listy i przejscie do kolejnej iteracji petli wyszukujacej
					oUzyteczneTrafienia.remove(iLosowanePole);
					}
				}
			catch (ParametrException e)
				{
				throw new ProgramistaException(e);
				}
			
			}
		return strzalLosowy(oStatkiPrzeciwnika);
		}
	/**
	 * Metoda wyszukuje losowo na planszy pole do oddania strzalu, jednak jesli wylosowane pole nie zawiera statku,
	 * nastepuje ponowne losowanie w celu znalezienia lepszego pola do strzalu. Dozwolona ilosc powtorzen okresla
	 * drugi parametr.<br />
	 * 
	 * Jesli w ktorejkolwiek iteracji nastapi wylosowanie pola zawierajacego statek, strzal uznaje sie za trafiony
	 * i nie sa wykonywane kolejne iteracje petli.<br />
	 * 
	 * Jesli w ostatniej iteracji takze zostanie wylosowane pole puste,
	 * wspolrzedne tego pola zostaje uznane za wykonany strzal i jest on niecelny.
	 * 
	 * @param oStatkiPrzeciwnika Kontener statkow przeciwnika.
	 * @param iIloscPowtorzen Dozwolona ilosc powtorzen losowania pola do ostrzalu.
	 * @return Zwraca TRUE, w przypadku trafienia statku, lub FALSE, jesli strzal byl niecelny.
	 */
	protected boolean strzalWielokrotny(StatekIterator oStatkiPrzeciwnika, int iIloscPowtorzen)
		{
		try
			{
			Pozycja oWylosowanePole = null;
			Plansza oPlansza = oStatkiPrzeciwnika.getPlansza();
			for (int i = 1; i <= iIloscPowtorzen; ++i)
				{
				oWylosowanePole = losujPole(oPlansza);
				if (oPlansza.getPole(oWylosowanePole.getX(), oWylosowanePole.getY()) == PlanszaTypPola.PLANSZA_STATEK || i == iIloscPowtorzen)
					{
					boolean bStrzal;
					bStrzal = oStatkiPrzeciwnika.strzal(oWylosowanePole.getX(), oWylosowanePole.getY());
					if (bStrzal == true)
						{
						//zapisanie celnego strzalu w tablicy trafien
						Pozycja oTrafienie = new Pozycja(2);
						oTrafienie.setX( oWylosowanePole.getX() );
						oTrafienie.setY( oWylosowanePole.getY() );
						oUzyteczneTrafienia.add(oTrafienie);
						}
					return bStrzal;
					}
				}
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		//petla musi zwrocic strzal. skoro doszlo tutaj - wywal wyjatek
		throw new ProgramistaException();
		}
	/**
	 * Metoda wybiera losowe pole dostepne do ostrzelania na planszy przeciwnika i zwraca obiekt typu Pozycja zawierajacy te wspolrzedne.
	 * 
	 * @param oPlanszaPrzeciwnika Plansza przeciwnika, na ktora ma byc oddany strzal.
	 * @return Wspolrzedne wylosowanego pola do ostrzelania.
	 */
	private Pozycja losujPole(Plansza oPlanszaPrzeciwnika)
		{
		try
			{
			Pozycja oWylosowanePole = new Pozycja(2);
			int iWylosowanePole = oRand.nextInt( oPlanszaPrzeciwnika.getIloscNieznanych() ) + 1;
			//obliczenie x i y dla wylosowanego pola
			int iX = 0;
			int iY = 0;
			while (iWylosowanePole > 0)
				{
				if (oPlanszaPrzeciwnika.getPole(iX, iY) == PlanszaTypPola.PLANSZA_POLE_PUSTE || oPlanszaPrzeciwnika.getPole(iX, iY) == PlanszaTypPola.PLANSZA_STATEK)
					--iWylosowanePole;
				if (iWylosowanePole > 0)
					{
					++iX;
					if (iX == oPlanszaPrzeciwnika.getSzerokosc())
						{
						++iY;
						iX = 0;
						}
					}
				}
			oWylosowanePole.setX(iX);
			oWylosowanePole.setY(iY);
			return oWylosowanePole;
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	}
