package pl.vgtworld.games.statki;

import pl.vgtworld.exceptions.ParametrException;
import pl.vgtworld.exceptions.ProgramistaException;
import pl.vgtworld.tools.Pozycja;

/**
 * Klasa sprawdzajaca, czy statek spelnia rozne warunki
 * odnosnie jego struktury, czy rozmieszczenia na planszy.<br />
 * 
 * <p>
 * aktualizacje:<br />
 * 1.1<br />
 * - dodanie parametru bProsteLinie do metody {@link #polaPolaczone(boolean)}.<br />
 * </p>
 * 
 * @author VGT
 * @version 1.1
 */
public class StatekWeryfikacja
	{
	/**
	 * Obiekt statku, ktory bedzie weryfikowany.
	 */
	private Statek oStatek;
	/**
	 * Plansza, na ktorej znajduje sie weryfikowany statek.
	 */
	private Plansza oPlansza;
	/**
	 * Konstruktor domyslny.
	 */
	public StatekWeryfikacja()
		{
		oStatek = null;
		oPlansza = null;
		}
	/**
	 * Import statku, dla ktorego maja byc wykonywane testy.
	 * 
	 * @param oStatek Obiekt statku do testow.
	 */
	public void importujStatek(Statek oStatek)
		{
		this.oStatek = oStatek;
		oPlansza = oStatek.getPlansza();
		}
	/**
	 * Metoda sprawdza, czy wszystkie pola statku znajduja sie na planszy.
	 * 
	 * @return Zwraca TRUE jesli statek w calosci jest na planszy, lub FALSE w przeciwnym wypadku.
	 */
	public boolean polaNaPlanszy()
		{
		if (oStatek == null)
			throw new ProgramistaException("obiekt statku niezaimportowany");
		try
			{
			for (int i = 1; i <= oStatek.getRozmiar(); ++i)
				{
				Pozycja oPole = oStatek.getPole(i);
				if (oPole.getX() == -1 || oPole.getX() >= oPlansza.getSzerokosc()
					|| oPole.getY() == -1 || oPole.getY() >= oPlansza.getWysokosc()
					)
					return false;
				}
			return true;
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * @deprecated
	 */
	public boolean polaPolaczone()
		{
		return polaPolaczone(false);
		}
	/**
	 * Metoda sprawdza, czy wszystkie pola danego statku tworza jednolita strukture
	 * (stykaja sie krawedziami i nie tworza dwoch lub wiecej niepolaczonych obszarow na planszy).<br />
	 * 
	 * aktualizacje:<br />
	 * 
	 * 1.1 - dodanie pierwszego parametru
	 * 
	 * @param bProsteLinie Okresla, czy pola musza byc w jednej linii pionowej lub poziomej.
	 * @return Zwraca TRUE, jesli statek jest prawidlowo zbudowany, lub FALSE w przeciwnym wypadku.
	 */
	public boolean polaPolaczone(boolean bProsteLinie)
		{
		if (oStatek == null)
			throw new ProgramistaException("obiekt statku niezaimportowany");
		try
			{
			int iIloscPrawidlowych = 0;
			boolean[] aPrawidlowe = new boolean[ oStatek.getRozmiar() ];
			for (int i = 0; i < oStatek.getRozmiar(); ++i)
				aPrawidlowe[i] = false;
			//pierwsze pole statku prawidlowe z automatu
			++iIloscPrawidlowych;
			aPrawidlowe[0] = true;
			boolean bZmiany = true;
			//petla wykonujaca sie dopoki nastepuja jakies zmiany w ilosci prawidlowych pol
			while (bZmiany == true)
				{
				bZmiany = false;
				for (int i = 0; i < oStatek.getRozmiar(); ++i)
					if (aPrawidlowe[i] == true)
						{
						Pozycja oPolePrawidlowe = oStatek.getPole(i+1);
						for (int j = -1; j <= 1; ++j)
							for (int k = -1; k <= 1; ++k)
								{
								if (oPolePrawidlowe.getX() + j < 0 || oPolePrawidlowe.getX() + j >= oPlansza.getSzerokosc()
									|| oPolePrawidlowe.getY() + k < 0 || oPolePrawidlowe.getY() + k >= oPlansza.getWysokosc()
									)
									continue;
								int iNumerPola = oStatek.getNumerPola(oPolePrawidlowe.getX() + j, oPolePrawidlowe.getY() + k);
								if (iNumerPola > 0 && aPrawidlowe[iNumerPola - 1] == false)
									{
									bZmiany = true;
									++iIloscPrawidlowych;
									aPrawidlowe[iNumerPola - 1] = true;
									}
								}
						}
				}
			
			if (iIloscPrawidlowych == oStatek.getRozmiar())
				{
				//dodatkowe sprawdzenie, czy pola tworza linie, jesli wymagane
				if (bProsteLinie == true)
					{
					int iX = -1;
					int iY = -1;
					boolean bPoziom = true;
					boolean bPion = true;
					for (int i = 1; i <= iIloscPrawidlowych; ++i)
						{
						if (iX == -1)
							iX = oStatek.getPole(i).getX();
						else if (iX != oStatek.getPole(i).getX())
							bPoziom = false;
						if (iY == -1)
							iY = oStatek.getPole(i).getY();
						else if (iY != oStatek.getPole(i).getY())
							bPion = false;
						}
					if (bPoziom == true || bPion == true)
						return true;
					else
						return false;
					}
				else
					return true;
				}
			else
				return false;
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Metoda sprawdza, czy ktorekolwiek pole statku styka sie z innym statkiem.
	 * 
	 * @return Zwraca FALSE, jesli wystapilo zetkniecie z innym statkiem, lub TRUE w przeciwnym wypadku. 
	 */
	public boolean brakSasiadow()
		{
		if (oStatek == null)
			throw new ProgramistaException("obiekt statku niezaimportowany");
		try
			{
			for (int i = 1; i <= oStatek.getRozmiar(); ++i)
				{
				Pozycja oPole = oStatek.getPole(i);
				for (int j = -1; j <= 1; ++j)
					for (int k = -1; k <=1; ++k)
						{
						Pozycja oSasiedniePole = new Pozycja(2);
						oSasiedniePole.setX(oPole.getX() + j);
						oSasiedniePole.setY(oPole.getY() + k);
						//odrzucenie sprawdzania pol, ktore laduja poza zakresem planszy
						if (oSasiedniePole.getX() < 0 || oSasiedniePole.getX() >= oPlansza.getSzerokosc()
							|| oSasiedniePole.getY() < 0 || oSasiedniePole.getY() >= oPlansza.getWysokosc()
							)
							continue;
						if (oPlansza.getPole(oSasiedniePole.getX(), oSasiedniePole.getY()) == PlanszaTypPola.PLANSZA_STATEK
							&& oStatek.getNumerPola(oSasiedniePole.getX(), oSasiedniePole.getY()) == 0
							)
							return false;
						}
				}
			return true;
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	}
