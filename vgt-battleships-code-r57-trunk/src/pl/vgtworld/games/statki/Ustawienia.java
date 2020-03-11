package pl.vgtworld.games.statki;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;
import pl.vgtworld.exceptions.ParametrException;
import pl.vgtworld.games.statki.components.JFrameOknoGry;

/**
 * Klasa przechowujaca glowne ustawienia rozgrywki.
 * 
 * @author VGT
 * @version 1.1
 */
public class Ustawienia
	{
	/**
	 * Przechowuje nazwe pliku, w ktorym przechowywane sa ustawienia domyslne.
	 */
	public static final String USTAWIENIA_DOMYSLNE = "settings.xml";
	/**
	 * Szerokosc planszy gry.
	 */
	private int iPlanszaSzerokosc;
	/**
	 * Wysokosc planszy gry.
	 */
	private int iPlanszaWysokosc;
	/**
	 * Poziom trudnosci AI.
	 */
	private int iPoziomTrudnosci;
	/**
	 * Ksztalt statkow limitowany do pionowych/poziomych linii.
	 * 
	 * @since 1.1
	 */
	private boolean bProsteLinie;
	/**
	 * Kontener przechowujacy rozmiar poszczegolnych statkow.
	 */
	private ArrayList<Integer> aStatki;
	/**
	 * Konstruktor domyslny.
	 */
	public Ustawienia()
		{
		try
			{
			//konfiguracja domyslna z pliku xml
			FileInputStream oStream = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + USTAWIENIA_DOMYSLNE);
			Properties oDomyslne = new Properties();
			oDomyslne.loadFromXML(oStream);
			iPlanszaSzerokosc = Integer.parseInt(oDomyslne.getProperty("plansza_szerokosc"));
			iPlanszaWysokosc = Integer.parseInt(oDomyslne.getProperty("plansza_wysokosc"));
			iPoziomTrudnosci = Integer.parseInt(oDomyslne.getProperty("poziom_trudnosci"));
			if ("tak".equals(oDomyslne.getProperty("proste_linie")))
				bProsteLinie = true;
			else
				bProsteLinie = true;
			aStatki = new ArrayList<Integer>();
			int iIloscStatkow = Integer.parseInt(oDomyslne.getProperty("ilosc_statkow"));
			for (int i = 1; i <= iIloscStatkow; ++i)
				aStatki.add(Integer.parseInt(oDomyslne.getProperty("statek"+i)));
			}
		catch(IOException e)
			{
			//konfiguracja domyslna
			iPlanszaSzerokosc = 10;
			iPlanszaWysokosc = 10;
			iPoziomTrudnosci = 50;
			bProsteLinie = false;
			aStatki = new ArrayList<Integer>(10);
			aStatki.add(1);
			aStatki.add(1);
			aStatki.add(1);
			aStatki.add(1);
			aStatki.add(2);
			aStatki.add(2);
			aStatki.add(2);
			aStatki.add(3);
			aStatki.add(3);
			aStatki.add(4);
			}
		}
	/**
	 * Metoda zwraca szerokosc planszy.
	 * 
	 * @return Zwraca ilosc pol bedacych szerokoscia planszy.
	 */
	public int getPlanszaSzerokosc()
		{
		return iPlanszaSzerokosc;
		}
	/**
	 * Metoda zwraca wysokosc planszy.
	 * 
	 * @return Zwraca ilosc pol bedacych wysokoscia planszy.
	 */
	public int getPlanszaWysokosc()
		{
		return iPlanszaWysokosc;
		}
	/**
	 * Metoda zwraca poziom trudnosci AI.
	 * 
	 * @return Zwraca liczbe z zakresu 1-100 bedaca poziomem trudnosci AI.
	 */
	public int getPoziomTrudnosci()
		{
		return iPoziomTrudnosci;
		}
	/**
	 * Zwraca informacje, czy statki moga byc tylko pionowymi/poziomymi liniami.
	 * 
	 * @return Zwraca TRUE, jesli statki moga byc tylko liniami, lub FALSE w przeciwnym wypadku.
	 * @since 1.1
	 */
	public boolean getProsteLinie()
		{
		return bProsteLinie;
		}
	/**
	 * Zwraca tablice zawierajaca rozmiar poszczegolnych statkow.
	 * 
	 * @return Tablica int zawierajaca rozmiar poszczegolnych statkow.
	 */
	public int[] getStatki()
		{
		int[] aDane = new int[ aStatki.size() ];
		for (int i = 0; i < aStatki.size(); ++i)
			aDane[i] = aStatki.get(i);
		return aDane;
		}
	/**
	 * Zwraca laczna ilosc statkow.
	 * 
	 * @return Laczna ilosc statkow.
	 */
	public int getIloscStatkow()
		{
		return (int)aStatki.size();
		}
	/**
	 * Zwraca ilosc statkow o podanym w parametrze rozmiarze.
	 * 
	 * @param iRozmiar Rozmiar statkow, ktore maja byc policzone.
	 * @return Ilosc statkow o podanym rozmiarze.
	 */
	public int getIloscStatkow(int iRozmiar)
		{
		int iIlosc = 0;
		for (int iStatek: aStatki)
			if (iStatek == iRozmiar)
				++iIlosc;
		return iIlosc;
		}
	/**
	 * Zwraca rozmiar najwiekszego statku.
	 * 
	 * @return Rozmiar najwiekszego statku.
	 */
	public int getMaxRozmiarStatku()
		{
		int iMax = 0;
		for (int iStatek: aStatki)
			if (iStatek > iMax)
				iMax = iStatek;
		return iMax;
		}
	/**
	 * Pozwala ustawic nowy rozmiar planszy gry.
	 * 
	 * @param iSzerokosc Nowa szerokosc planszy.
	 * @param iWysokosc Nowa wysokosc planszy.
	 */
	public void setPlanszaRozmiar(int iSzerokosc, int iWysokosc)
		{
		iPlanszaSzerokosc = iSzerokosc;
		iPlanszaWysokosc = iWysokosc;
		}
	/**
	 * Pozwala ustawic nowa szerokosc planszy.
	 * 
	 * @param iSzerokosc Nowa szerokosc planszy.
	 */
	public void setPlanszaSzerokosc(int iSzerokosc)
		{
		iPlanszaSzerokosc = iSzerokosc;
		}
	/**
	 * Pozwala ustawic nowa wysokosc planszy.
	 * 
	 * @param iWysokosc Nowa wysokosc planszy.
	 */
	public void setPlanszaWysokosc(int iWysokosc)
		{
		iPlanszaWysokosc = iWysokosc;
		}
	/**
	 * Pozwala ustawic wlasciwosc okreslajaca dozwolony ksztalt statkow.
	 * 
	 * @param bProsteLinie Wartosc TRUE oznacza, ze statki moga byc tylko pionowymi/poziomymi liniami.
	 * @since 1.1
	 */
	public void setProsteLinie(boolean bProsteLinie)
		{
		this.bProsteLinie = bProsteLinie;
		}
	/**
	 * Pozwala ustawic nowy poziom trudnosci.
	 * 
	 * @param iPoziomTrudnosci Poziom trudnosci AI.
	 * @throws ParametrException Wyrzuca wyjatek, jesli ustawiany poziom trudnosci nie miesci sie w zakresie 1-100
	 */
	public void setPoziomTrudnosci(int iPoziomTrudnosci) throws ParametrException
		{
		if (iPoziomTrudnosci < 1 || iPoziomTrudnosci > 100)
			throw new ParametrException("iPoziomTrudnosci = " + iPoziomTrudnosci);
		this.iPoziomTrudnosci = iPoziomTrudnosci;
		}
	/**
	 * Dodaje do listy statkow kolejny statek o podanym rozmiarze.
	 * 
	 * @param iRozmiar Rozmiar statku do dodania.
	 * @throws ParametrException Wyrzuca wyjatek, jesli podany rozmiar jest mniejszy od 1.
	 */
	public void dodajStatek(int iRozmiar) throws ParametrException
		{
		if (iRozmiar < 1)
			throw new ParametrException("iRozmiar = " + iRozmiar);
		aStatki.add(iRozmiar);
		}
	/**
	 * Usuwa statek o podanym indexie z kontenera.
	 * 
	 * @param iIndex Index statku do usuniecia (liczone od 0).
	 * @throws ParametrException Wyrzuca wyjatek, jesli statek o podanym indexie nie istnieje.
	 */
	public void usunStatek(int iIndex) throws ParametrException
		{
		if (iIndex >= aStatki.size() || iIndex < 0)
			throw new ParametrException("iIndex = " + iIndex);
		aStatki.remove(iIndex);
		}
	/**
	 * Usuwa wszystkie statki z tablicy.
	 */
	public void usunStatki()
		{
		aStatki.clear();
		}
	/**
	 * zapisuje aktualne ustawienia do pliku, w ktorym przechowywane sa ustawienia domyslne wczytywane podczas tworzenia obiektu
	 */
	public void zapiszUstawieniaDomyslne()
		{
		try
			{
			Properties oDomyslne = new Properties();
			oDomyslne.setProperty("plansza_szerokosc", String.valueOf(iPlanszaSzerokosc));
			oDomyslne.setProperty("plansza_wysokosc", String.valueOf(iPlanszaWysokosc));
			oDomyslne.setProperty("poziom_trudnosci", String.valueOf(iPoziomTrudnosci));
			if (bProsteLinie == true)
				oDomyslne.setProperty("proste_linie", "tak");
			else
				oDomyslne.setProperty("proste_linie", "nie");
			int[] aStatki = getStatki();
			oDomyslne.setProperty("ilosc_statkow", String.valueOf(aStatki.length));
			for (int i = 0; i < aStatki.length; ++i)
				oDomyslne.setProperty("statek"+(i+1), String.valueOf(aStatki[i]));
			FileOutputStream oStream = new FileOutputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + USTAWIENIA_DOMYSLNE);
			oDomyslne.storeToXML(oStream, null);
			}
		catch (IOException e)
			{
			JOptionPane.showMessageDialog(null, JFrameOknoGry.LANG.getProperty("errorMsg.settings.saveDefault") , JFrameOknoGry.LANG.getProperty("errorMsg.windowTitle"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
