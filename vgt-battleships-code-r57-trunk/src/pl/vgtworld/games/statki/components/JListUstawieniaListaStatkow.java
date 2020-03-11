package pl.vgtworld.games.statki.components;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import pl.vgtworld.exceptions.ParametrException;

/**
 * Obiekt listy wykorzystany w oknie ustawien do prezentacji listy zdefiniowanych statkow.
 * 
 * @author VGT
 * @version 1.0
 */
public class JListUstawieniaListaStatkow
	extends JList
	{
	/**
	 * Tablica przechowujaca rozmiary statkow.
	 */
	private ArrayList<Integer> oListaInt;
	/**
	 * Model listy przechowujacy rozmiary statkow.
	 */
	private DefaultListModel oJListLista;
	/**
	 * Konstruktor domyslny.
	 */
	public JListUstawieniaListaStatkow()
		{
		oListaInt = new ArrayList<Integer>();
		oJListLista = new DefaultListModel();
		setModel(oJListLista);
		}
	/**
	 * Zwraca ilosc statkow przechowywana na liscie.
	 * 
	 * @return Ilosc statkow na liscie.
	 */
	public int getIloscStatkow()
		{
		return oListaInt.size();
		}
	/**
	 * Zwraca tablice int zawierajaca rozmiar wszystkich statkow przechowywanych w liscie.
	 * 
	 * @return Tablica z rozmiarami statkow przechowywanych w liscie.
	 */
	public int[] getListaStatkow()
		{
		int[] aLista = new int[ oListaInt.size() ];
		for (int i = 0; i < oListaInt.size(); ++i)
			aLista[i] = oListaInt.get(i);
		return aLista;
		}
	/**
	 * Dodaje do listy statek o podanym rozmiarze.
	 * 
	 * @param iRozmiar Rozmiar dodawanego statku.
	 * @throws ParametrException Wyrzuca wyjatek, jesli podany rozmiar jest mniejszy od 1.
	 */
	public void listaDodaj(int iRozmiar) throws ParametrException
		{
		if (iRozmiar < 1)
			throw new ParametrException("iRozmiar = " + iRozmiar);
		oListaInt.add(iRozmiar);
		oJListLista.addElement(JListUstawieniaListaStatkow.statekNazwa(iRozmiar));
		}
	/**
	 * Zmienia rozmiar statku o podanym indexie.
	 * 
	 * @param iIndex Index statku na liscie (liczony od 0).
	 * @param iRozmiar Nowy rozmiar statku.
	 * @throws ParametrException Wyrzuca wyjatek, jesli index jest poza zakresem istniejacej listy statkow, lub rozmiar jest mniejszy od 1.
	 */
	public void listaZmien(int iIndex, int iRozmiar) throws ParametrException
		{
		if (iIndex < 0 || iIndex >= oListaInt.size())
			throw new ParametrException("iIndex = " + iIndex);
		if (iRozmiar < 1)
			throw new ParametrException("iRozmiar = " + iRozmiar);
		oListaInt.set(iIndex, iRozmiar);
		oJListLista.set(iIndex, JListUstawieniaListaStatkow.statekNazwa(iRozmiar));
		}
	/**
	 * Powieksza o 1 rozmiar statku o podanym indexie.
	 * 
	 * @param iIndex Index statku na liscie (liczone od 0).
	 * @throws ParametrException Wyrzuca wyjatek, jesli index jest poza zakresem istniejacej listy statkow.
	 */
	public void listaPowieksz(int iIndex) throws ParametrException
		{
		if (iIndex < 0 || iIndex >= oListaInt.size())
			throw new ParametrException("iIndex = " + iIndex);
		oListaInt.set(iIndex, oListaInt.get(iIndex) + 1);
		oJListLista.set(iIndex, JListUstawieniaListaStatkow.statekNazwa(oListaInt.get(iIndex)));
		}
	/**
	 * Pomniejsza o 1 rozmiar statku o podanym indexie.
	 * 
	 * @param iIndex Index statku na liscie (liczone od 0).
	 * @throws ParametrException Wyrzuca wyjatek, jesli index jest poza zakresem istniejacej listy statkow.
	 */
	public void listaPomniejsz(int iIndex) throws ParametrException
		{
		if (iIndex < 0 || iIndex >= oListaInt.size())
			throw new ParametrException("iIndex = " + iIndex);
		if (oListaInt.get(iIndex) > 1)
			{
			oListaInt.set(iIndex, oListaInt.get(iIndex) - 1);
			oJListLista.set(iIndex, JListUstawieniaListaStatkow.statekNazwa(oListaInt.get(iIndex)));
			}
		}
	/**
	 * Usuwa z listy statek o podanym indexie.
	 * 
	 * @param iIndex Index statku na liscie (liczone od 0).
	 * @throws ParametrException Wyrzuca wyjatek, jesli index jest poza zakresem istniejacej listy statkow.
	 */
	public void listaUsun(int iIndex) throws ParametrException
		{
		if (iIndex < 0 || iIndex >= oListaInt.size())
			throw new ParametrException("iIndex = " + iIndex);
		oListaInt.remove(iIndex);
		oJListLista.remove(iIndex);
		}
	/**
	 * Usuwa z listy wszystkie statki.
	 */
	public void listaWyczysc()
		{
		oListaInt.clear();
		oJListLista.clear();
		}
	/**
	 * Metoda generujaca nazwe statku do wyswietlania na liscie na podstawie podanego rozmiaru.
	 * 
	 * @param iRozmiar Rozmiar statku.
	 * @return Nazwa statku.
	 */
	private static String statekNazwa(int iRozmiar) throws ParametrException
		{
		if (iRozmiar < 1)
			throw new ParametrException("iRozmiar = " + iRozmiar);
		int iKlasaStatku = iRozmiar > 5 ? 5 : iRozmiar;
		return JFrameOknoGry.LANG.getProperty("shipName.size" + iKlasaStatku) + " ( " + iRozmiar + " )";
		}
	}
