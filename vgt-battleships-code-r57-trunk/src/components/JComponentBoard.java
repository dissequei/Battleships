package components;

import static ship.DrawCoordinates.*;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import exceptions.ParametrException;
import exceptions.DevException;
import ship.Board;
import ship.FieldTypeBoard;
import tools.Position;

/**
 * Komponent obslugujacy wyswietlanie planszy.
 * 
 * @author VGT
 * @version 1.0
 */
public class JComponentBoard
	extends JComponent
	implements ActionListener
	{
	/**
	 * Plansza, ktora ma wyswietlic panel.
	 */
	private Board oPlansza;
	/**
	 * Wlasciwosc przechowujaca informacje, czy na planszy maja byc takze wyswietlane nietrafione pola statkow.
	 */
	private boolean bWyswietlStatki;
	/**
	 * Kolor pola statku.
	 */
	private Color oKolorStatek;
	/**
	 * Kolor pola po strzale niecelnym.
	 */
	private Color oKolorStrzalCelny;
	/**
	 * Kolor pola po strzale celnym.
	 */
	private Color oKolorStrzalNiecelny;
	/**
	 * Kolor linii siatki rozdzielajacej pola.
	 */
	private Color oKolorSiatka;
	/**
	 * Kolor tla (uzywany w przyadku niepowodzenia zaladowania tla graficznego).
	 */
	private Color oKolorTla;
	/**
	 * Tablica przechowujaca kolejne kolory animacji strzalu na pole ze statkiem.
	 */
	private Color[] aKoloryWyroznieniaStatkow;
	/**
	 * Tablica przechowujaca kolejne kolory animacji strzalu na pole puste.
	 */
	private Color[] aKoloryWyroznieniaPolPustych;
	/**
	 * Index aktualnie wyswietlanego koloru wyroznienia.
	 */
	private int iNumerKoloruWyroznienia;
	/**
	 * Wspolrzedne pola wyswietlanego, jako wyroznione.
	 */
	private Position oWyroznionePole;
	/**
	 * Obrazek tla planszy.
	 */
	private static Image oImgTlo;
	/**
	 * Timer do obslugi animacji wyroznienia pola.
	 */
	private Timer oTimer;
	static
		{
		JComponentBoard.oImgTlo = null;
		}
	/**
	 * Konstruktor.
	 * 
	 * @param oPlansza Plansza, ktora ma byc wyswietlona na panelu.
	 */
	public JComponentBoard(Board oPlansza)
		{
		this.oPlansza = oPlansza;
		bWyswietlStatki = true;
		oKolorStatek = new Color(0, 0, 255, 127);
		oKolorStrzalCelny = new Color(0, 0, 0);
		oKolorStrzalNiecelny = new Color(150, 150, 50, 192);
		oKolorSiatka = new Color(0, 0, 0, 96);
		aKoloryWyroznieniaStatkow = new Color[100];
		aKoloryWyroznieniaPolPustych = new Color[100];
		for (int i = 0; i < 100; ++i)
			{
			aKoloryWyroznieniaStatkow[i] = new Color(255, 0, 0, (int)(((double)i / 100) * 255));
			aKoloryWyroznieniaPolPustych[i] = new Color(0, 255, 0, (int)(((double)i / 100) * 255));
			}
		iNumerKoloruWyroznienia = 0;
		oWyroznionePole = new Position(2);
		oWyroznionePole.setX(-1);
		oWyroznionePole.setY(-1);
		if (JComponentBoard.oImgTlo == null)
			{
			URL oImgUrl = getClass().getResource("/pl/vgtworld/games/statki/img/map-bg.jpg");
			
			if (oImgUrl != null)
				{
				try
					{
					JComponentBoard.oImgTlo = ImageIO.read(oImgUrl);
					}
				catch (IOException e)
					{
					JComponentBoard.oImgTlo = null;
					}
				}
			if (oImgUrl == null || JComponentBoard.oImgTlo == null)
				{
				JComponentBoard.oImgTlo = null;
				oKolorStrzalNiecelny = new Color(255, 255, 255, 127);
				oKolorTla = new Color(190, 160, 110);
				}
			}
		oTimer = new Timer(1000, this);
		oTimer.setRepeats(false);
		//setMinimumSize(new Dimension(iSzerokosc, iWysokosc));
		//setPreferredSize(new Dimension(iSzerokosc, iWysokosc));
		}
	/**
	 * Metoda umozliwia okreslenie, czy panel ma wyswietlac takze nietrafione pola statkow.
	 * 
	 * @param bStan Jesli TRUE, panel wyswietli nietrafione pola statkow.
	 */
	public void setWyswietlStatki(boolean bStan)
		{
		bWyswietlStatki = bStan;
		}
	public void aktywujWyroznienie(Position oPozycja)
		{
		aktywujWyroznienie(oPozycja.getX(), oPozycja.getY());
		}
	public void aktywujWyroznienie(int iX, int iY)
		{
		if (oTimer.isRunning())
			oTimer.stop();
		oWyroznionePole.setX(iX);
		oWyroznionePole.setY(iY);
		iNumerKoloruWyroznienia = 99;
		oTimer.start();
		repaint();
		}
	public void actionPerformed(ActionEvent oEvent)
		{
		iNumerKoloruWyroznienia-= 100;
		if (iNumerKoloruWyroznienia < 0)
			{
			iNumerKoloruWyroznienia = 0;
			//oTimer.stop();
			}
		repaint();
		}
	/**
	 * Przeciazona metoda rysujaca zawartosc panelu.
	 */
	@Override public void paintComponent(Graphics g)
		{
		super.paintComponent(g);
		int iSzerokosc = getWidth();
		int iWysokosc = getHeight();
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, iSzerokosc - 1, iWysokosc - 1);
		int iPlanszaXStart = fieldToPixTopLeft(iSzerokosc, iWysokosc, oPlansza.getSzerokosc(), oPlansza.getWysokosc(), 0, 0).getX();
		int iPlanszaYStart = fieldToPixTopLeft(iSzerokosc, iWysokosc, oPlansza.getSzerokosc(), oPlansza.getWysokosc(), 0, 0).getY();
		int iPlanszaXSzerokosc = fieldToPixTopLeft(iSzerokosc, iWysokosc, oPlansza.getSzerokosc(), oPlansza.getWysokosc(), oPlansza.getSzerokosc(), oPlansza.getWysokosc()).getX() - iPlanszaXStart + 1;
		int iPlanszaYSzerokosc = fieldToPixTopLeft(iSzerokosc, iWysokosc, oPlansza.getSzerokosc(), oPlansza.getWysokosc(), oPlansza.getSzerokosc(), oPlansza.getWysokosc()).getY() - iPlanszaYStart + 1;
		if (JComponentBoard.oImgTlo != null)
			{
			g.drawImage(JComponentBoard.oImgTlo, 0, 0, iSzerokosc, iWysokosc, null);
			}
		else
			{
			g.setColor(oKolorTla);
			g.fillRect(iPlanszaXStart, iPlanszaYStart, iPlanszaXSzerokosc, iPlanszaYSzerokosc);
			}
		//przygotowanie zmiennych
		Position oKrzyzowka;
		Position oKrzyzowka2;
		int iPoleX = 0;
		int iPoleY = 0;
		int iPoleSzerokosc = 0;
		int iPoleWysokosc = 0;
		int iCrossWielkosc = 0;
		boolean bRysuj = false;
		for (int i = 0; i < oPlansza.getSzerokosc(); ++i)
			for (int j = 0; j < oPlansza.getWysokosc(); ++j)
				{
				//obliczenie niezbednych danych
				oKrzyzowka = fieldToPixTopLeft(iSzerokosc, iWysokosc, oPlansza.getSzerokosc(), oPlansza.getWysokosc(), i, j);
				oKrzyzowka2 = fieldToPixBottomRight(iSzerokosc, iWysokosc, oPlansza.getSzerokosc(), oPlansza.getWysokosc(), i, j);
				iPoleX = oKrzyzowka.getX() + 1;
				iPoleY = oKrzyzowka.getY() + 1;
				iPoleSzerokosc = oKrzyzowka2.getX() - iPoleX;
				iPoleWysokosc = oKrzyzowka2.getY() - iPoleY;
				//skrzyzowania pomiedzy polami
				if (iCrossWielkosc == 0)
					{
					iCrossWielkosc = (int)((iPoleSzerokosc + iPoleWysokosc) * 0.03);
					if (iCrossWielkosc < 2)
						iCrossWielkosc = 2;
					}
				g.setColor(oKolorSiatka);
				g.drawLine(oKrzyzowka.getX() - iCrossWielkosc, oKrzyzowka.getY(), oKrzyzowka.getX() + iCrossWielkosc, oKrzyzowka.getY());
				g.drawLine(oKrzyzowka.getX(), oKrzyzowka.getY() - iCrossWielkosc, oKrzyzowka.getX(), oKrzyzowka.getY() + iCrossWielkosc);
				if (i + 1 == oPlansza.getSzerokosc())
					{
					g.drawLine(oKrzyzowka2.getX() - iCrossWielkosc, oKrzyzowka.getY(), oKrzyzowka2.getX() + iCrossWielkosc, oKrzyzowka.getY());
					g.drawLine(oKrzyzowka2.getX(), oKrzyzowka.getY() - iCrossWielkosc, oKrzyzowka2.getX(), oKrzyzowka.getY() + iCrossWielkosc);
					}
				if (j + 1 == oPlansza.getWysokosc())
					{
					g.drawLine(oKrzyzowka.getX() - iCrossWielkosc, oKrzyzowka2.getY(), oKrzyzowka.getX() + iCrossWielkosc, oKrzyzowka2.getY());
					g.drawLine(oKrzyzowka.getX(), oKrzyzowka2.getY() - iCrossWielkosc, oKrzyzowka.getX(), oKrzyzowka2.getY() + iCrossWielkosc);
					}
				if (i + 1 == oPlansza.getSzerokosc() && j + 1 == oPlansza.getWysokosc())
					{
					g.drawLine(oKrzyzowka2.getX() - iCrossWielkosc, oKrzyzowka2.getY(), oKrzyzowka2.getX() + iCrossWielkosc, oKrzyzowka2.getY());
					g.drawLine(oKrzyzowka2.getX(), oKrzyzowka2.getY() - iCrossWielkosc, oKrzyzowka2.getX(), oKrzyzowka2.getY() + iCrossWielkosc);
					}
				//zawartosc pola
				try
					{
					FieldTypeBoard eTyp = oPlansza.getPole(i, j);
					bRysuj = false;
					switch (eTyp)
						{
						case PLANSZA_STATEK:
							g.setColor(oKolorStatek);
							if (bWyswietlStatki == true)
								bRysuj = true;
							break;
						case PLANSZA_STRZAL_CELNY:
							g.setColor(oKolorStrzalCelny);
							bRysuj = true;
							break;
						case PLANSZA_POLE_NIEDOSTEPNE:
						case PLANSZA_STRZAL_NIECELNY:
							g.setColor(oKolorStrzalNiecelny);
							bRysuj = true;
							break;
						}
					if (bRysuj == true)
						{
						g.fillRect(iPoleX, iPoleY, iPoleSzerokosc, iPoleWysokosc);
						}
					//wyroznienie pola
					if (iNumerKoloruWyroznienia > 0 && oWyroznionePole.getX() == i && oWyroznionePole.getY() == j)
						{
						if (oPlansza.getPole(i, j) == FieldTypeBoard.PLANSZA_STATEK || oPlansza.getPole(i, j) == FieldTypeBoard.PLANSZA_STRZAL_CELNY)
							g.setColor(aKoloryWyroznieniaStatkow[iNumerKoloruWyroznienia]);
						else
							g.setColor(aKoloryWyroznieniaPolPustych[iNumerKoloruWyroznienia]);
						g.fillRect(iPoleX, iPoleY, iPoleSzerokosc, iPoleWysokosc);
						}
					}
				catch (ParametrException e)
					{
					throw new DevException(e);
					}
				}
		}
	}
