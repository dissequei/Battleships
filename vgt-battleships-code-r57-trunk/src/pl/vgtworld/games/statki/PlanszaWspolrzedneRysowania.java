package pl.vgtworld.games.statki;

import pl.vgtworld.tools.Pozycja;

/**
 * Klasa abstrakcyjna zawierajaca statyczne metody do konwersji pomiedzy wspolrzednymi na narysowanej planszy w pixelach,
 * a wspolrzednymi w polach.
 * 
 * @author VGT
 * @version 1.0
 */
public abstract class PlanszaWspolrzedneRysowania
	{
	/**
	 * Wartosc marginesu, o ktory narysowana plansza bedzie odsunieta od krawedzi panelu, na ktorym jest rysowana.<br />
	 * 
	 * Wartosc wyrazona w procentach szerokosci panelu.
	 */
	static final int iMargines = 5;
	/**
	 * Metoda konwertujaca wspolrzedne klikniecia na komponent na wspolrzedne kliknietego pola na planszy.<br />
	 * 
	 * Zwraca obiekt zawierajacy wspolrzedne kliknietego pola, lub wspolrzedne (-1, -1), jesli klikniecie
	 * nie trafilo w zadne pole.
	 * 
	 * @param iPanelSzerokosc Szerokosc panelu rysujacego plansze w pixelach.
	 * @param iPanelWysokosc Wysokosc panelu rysujacego plansze w pixelach.
	 * @param iPlanszaSzerokosc Szerokosc planszy (ilosc pol).
	 * @param iPlanszaWysokosc Wysokosc planszy (ilosc pol).
	 * @param iX Wspolrzedna X kliknietego pixela.
	 * @param iY Wspolrzedna Y kliknietego pixela.
	 * @return Zwraca obiekt pozycji ze wspolrzednymi kliknietego pola.
	 */
	public static Pozycja pixToField(int iPanelSzerokosc, int iPanelWysokosc, int iPlanszaSzerokosc, int iPlanszaWysokosc, int iX, int iY)
		{
		Pozycja oPozycja = new Pozycja(2);
		oPozycja.setX(-1);
		oPozycja.setY(-1);
		Pozycja oWspolrzedneTopLeft;
		Pozycja oWspolrzedneBottomRight;
		//znalezienie X
		for (int i = 0; i < iPlanszaSzerokosc; ++i)
			{
			oWspolrzedneTopLeft = PlanszaWspolrzedneRysowania.fieldToPixTopLeft(iPanelSzerokosc, iPanelWysokosc, iPlanszaSzerokosc, iPlanszaWysokosc, i, 0);
			oWspolrzedneBottomRight = PlanszaWspolrzedneRysowania.fieldToPixBottomRight(iPanelSzerokosc, iPanelWysokosc, iPlanszaSzerokosc, iPlanszaWysokosc, i, 0);
			if (oWspolrzedneTopLeft.getX() < iX && oWspolrzedneBottomRight.getX() > iX)
				{
				oPozycja.setX(i);
				break;
				}
			}
		//znalezienie Y
		for (int i = 0; i < iPlanszaWysokosc; ++i)
			{
			oWspolrzedneTopLeft = PlanszaWspolrzedneRysowania.fieldToPixTopLeft(iPanelSzerokosc, iPanelWysokosc, iPlanszaSzerokosc, iPlanszaWysokosc, 0, i);
			oWspolrzedneBottomRight = PlanszaWspolrzedneRysowania.fieldToPixBottomRight(iPanelSzerokosc, iPanelWysokosc, iPlanszaSzerokosc, iPlanszaWysokosc, 0, i);
			if (oWspolrzedneTopLeft.getY() < iY && oWspolrzedneBottomRight.getY() > iY)
				{
				oPozycja.setY(i);
				break;
				}
			}
		
		return oPozycja;
		}
	/**
	 * Metoda konwertujaca wspolrzedne pola na planszy do wspolrzednych lewego gornego pixela danego pola w narysowanej na panelu planszy.<br />
	 * 
	 * UWAGA! Wspolrzedne te znajduja sie juz poza polem (pola sa rozdzielone pojedynczym rzedem pixeli w celu rysowania siatki)
	 * i zawieraja miejsce krzyzowania sie siatki pomiedzy polami.
	 * 
	 * @param iPanelSzerokosc Szerokosc panelu w pixelach.
	 * @param iPanelWysokosc Wysokosc panelu w pixelach.
	 * @param iPlanszaSzerokosc Szerokosc planszy w polach.
	 * @param iPlanszaWysokosc Wysokosc planszy w polach.
	 * @param iXPola Wspolrzedna X konwertowanego pola (liczone od 0).
	 * @param iYPola Wspolrzedna Y konwertowanego pola (liczone od 0).
	 * @return Wspolrzedne pixela znajdujacego sie przy lewym gornym rogu rysowanego pola.
	 */
	public static Pozycja fieldToPixTopLeft(int iPanelSzerokosc, int iPanelWysokosc, int iPlanszaSzerokosc, int iPlanszaWysokosc, int iXPola, int iYPola)
		{
		Pozycja oPozycja = new Pozycja(2);
		oPozycja.setX(-1);
		oPozycja.setY(-1);
		//obliczenie szerokosci marginesow w pixelach
		int iMarginesPoziomyPx = (int)((float)iPanelSzerokosc * (float)((float)PlanszaWspolrzedneRysowania.iMargines / 100)); 
		int iMarginesPionowyPx = (int)((float)iPanelWysokosc * (float)((float)PlanszaWspolrzedneRysowania.iMargines / 100));
		//System.out.println(""+iMarginesPoziomyPx+","+iMarginesPionowyPx);
		//obliczenie szerokosc i wysokosci rysowanej planszy po odjeciu marginesow
		int iPlanszaSzerokoscPx = iPanelSzerokosc - (2 * iMarginesPoziomyPx);
		int iPlanszaWysokoscPx = iPanelWysokosc - (2 * iMarginesPionowyPx);
		//System.out.println(""+iPlanszaSzerokoscPx+","+iPlanszaWysokoscPx);
		//obliczenie wspolrzednych x i y dla lewego gornego rogu danego pola
		float fX = ((float)(iPlanszaSzerokoscPx - 1) / iPlanszaSzerokosc) * iXPola;
		float fY = ((float)(iPlanszaWysokoscPx - 1) / iPlanszaWysokosc) * iYPola;
		//System.out.println(""+fX+","+fY);
		//koretka wspolrzednych pol o margines
		fX+= (float)iMarginesPoziomyPx;
		fY+= (float)iMarginesPionowyPx;
		//wpisanie wspolrzednych do obiektu pozycji i zwrocenie obiektu
		oPozycja.setX((int)fX);
		oPozycja.setY((int)fY);
		return oPozycja;
		}
	/**
	 * Metoda w dzialaniu podobna do {@link #fieldToPixTopLeft(int, int, int, int, int, int)} z ta roznica,
	 * ze zwraca wspolrzedne pixela znajdujacego sie przy prawym dolnym rogu pola.
	 * 
	 * @param iPanelSzerokosc Szerokosc planszy w pixelach.
	 * @param iPanelWysokosc Wysokosc planszy w pixelach.
	 * @param iPlanszaSzerokosc Szerokosc planszy w polach.
	 * @param iPlanszaWysokosc Wysokosc planszy w polach.
	 * @param iXPola Wspolrzedna X konwertowanego pola (liczone od 0).
	 * @param iYPola Wspolrzedna Y konwertowanego pola (liczone od 0).
	 * @return Wspolrzedne pixela znajdujacego sie przy prawym dolnym rogu rysowanego pola.
	 */
	public static Pozycja fieldToPixBottomRight(int iPanelSzerokosc, int iPanelWysokosc, int iPlanszaSzerokosc, int iPlanszaWysokosc, int iXPola, int iYPola)
		{
		return PlanszaWspolrzedneRysowania.fieldToPixTopLeft(iPanelSzerokosc, iPanelWysokosc, iPlanszaSzerokosc, iPlanszaWysokosc, iXPola + 1, iYPola + 1);
		}
	}
