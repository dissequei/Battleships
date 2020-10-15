package pl.vgtworld.games.statki.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import pl.vgtworld.components.about.JDialogAbout;
import pl.vgtworld.exceptions.ParametrException;
import pl.vgtworld.exceptions.ProgramistaException;
import pl.vgtworld.games.statki.Plansza;
import pl.vgtworld.games.statki.PlanszaTypPola;
import pl.vgtworld.games.statki.PlanszaWspolrzedneRysowania;
import pl.vgtworld.games.statki.StatekIterator;
import pl.vgtworld.games.statki.StatkiPozycjoner;
import pl.vgtworld.games.statki.StatusGry;
import pl.vgtworld.games.statki.Ustawienia;
import pl.vgtworld.games.statki.ai.Ai;
import pl.vgtworld.games.statki.ai.AiFactory;
import pl.vgtworld.tools.Pozycja;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Glowne okno gry.
 * 
 * @author VGT
 * @version 1.0
 */
public class JFrameOknoGry
	extends JFrame
	{
	/**
	 * Zmienna przechowujaca wersje programu odczytana z pliku.
	 */
	public static String sWersja;
	/**
	 * Stala przechowujaca minimalna szerokosc okna glownego gry.
	 */
	public static final int MIN_SZEROKOSC = 640;
	/**
	 * Stala przechowujaca minimalna wysokosc okna glownego gry.
	 */
	public static final int MIN_WYSOKOSC = 480;
	/**
	 * Plik jezykowy.
	 */
	public static Properties LANG;
	/**
	 * Obiekt statusu gry.
	 */
	private StatusGry oStatusGry;
	/**
	 * Obiekt ustawien rozgrywki.
	 */
	private Ustawienia oUstawienia;
	/**
	 * Okno ustawien rozgrywki.
	 */
	private JDialogUstawienia oOknoUstawienia;
	/**
	 * Okno informacji o autorze.
	 */
	private JDialogAbout oOknoAbout;
	/**
	 * Menu glownego okna gry.
	 */
	private JMenuBar oMenu;
	/**
	 * Panel przechowujacy plansze wyswietlany w trakcie gry.
	 */
	private JPanel oPanelPlanszeKontener;
	/**
	 * Panel wyswietlany po rozpoczeciu nowej gry. Realizuje obsluge rozmieszczenia statkow przez gracza.
	 */
	private JPanelZaznaczanieStatkow oPanelZaznaczanieStatkow;
	/**
	 * Panel startowy wyswietlany po uruchomieniu programu zawierajacy przyciski do uruchomienia gry,
	 * zmiany ustawien i zakonczenia rozgrywki.
	 */
	private JPanel oPanelPrzyciski;
	/**
	 * Komponent wyswietlany w gornej czesci okna prezentujacy komunikaty na temat wydarzen na planszy poszczegolnych graczy.
	 */
	private JComponentWydarzenia oComponentWydarzenia;
	/**
	 * Komponent wyswietlany w dolnej czesci okna prezentujacy informacje na temat stanu aktualnej rozgrywki.
	 */
	private JComponentStatusGry oComponentStatusGry;
	/**
	 * Kontener statkow gracza.
	 */
	private StatekIterator oStatkiGracz;
	/**
	 * Kontener statkow komputera.
	 */
	private StatekIterator oStatkiKomputer;
	/**
	 * Sztuczna inteligencja komputera.
	 */
	private Ai oAi;
	/**
	 * Zmienna okreslajaca, czy jest kolej gracza na oddanie strzalu.<br />
	 * 
	 * Wykorzystywana w celu zablokowania oddania strzalu, gdy jest kolej komputera, lub gra zakonczyla sie
	 * zwyciestwem ktoregos gracza.
	 */
	private boolean bKolejGracza;
	/**
	 * Klasa prywatna zawierajaca obsluge akcji rozpoczecia nowej rozgrywki.
	 */
	private class ActionNowaGra
		extends AbstractAction
		{
		public ActionNowaGra()
			{
			putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.newGame"));
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.newGame.desc"));
			}
		public void actionPerformed(ActionEvent event)
			{
			oStatusGry.rozpocznijNowaGre();
			oPanelPrzyciski.setVisible(false);
			oPanelPlanszeKontener.setVisible(false);
			oPanelZaznaczanieStatkow.setVisible(true);
			add(oPanelZaznaczanieStatkow, BorderLayout.CENTER);
			oPanelZaznaczanieStatkow.wyczyscPlansze();
			repaint();
			}
		}
	/**
	 * Klasa prywatna zawierajaca obsluge akcji wywolania okna ustawien rozgrywki.
	 */
	private class ActionUstawienia
		extends AbstractAction
		{
		public ActionUstawienia()
			{
			putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.settings"));
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.settings.desc"));
			}
		public void actionPerformed(ActionEvent oEvent)
			{
			oOknoUstawienia.reset();
			oOknoUstawienia.setVisible(true);
			}
		}
	/**
	 * Klasa prywatna zawierajaca obsluge akcji zakonczenia programu.
	 */
	private class ActionZakoncz
		extends AbstractAction
		{
		public ActionZakoncz()
			{
			putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.exit"));
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.exit.desc"));
			}
		public void actionPerformed(ActionEvent event)
			{
			System.exit(0);
			}
		}
	/**
	 * Klasa prywatna zawierajaca obsluge akcji wyswietlenia okna informacji o autorze.
	 */
	private class ActionAbout
		extends AbstractAction
		{
		public ActionAbout()
			{
			putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.about"));
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.about.desc"));
			}
		public void actionPerformed(ActionEvent oEvent)
			{
			oOknoAbout.centerPosition();
			oOknoAbout.setVisible(true);
			}
		}
	/**
	 * Klasa prywatna obslugujaca przebieg pojedynczego cyklu rozgrywki.<br />
	 * 
	 * - oddanie strzalu przez gracza poprzez klikniecie na plansze komputera realizowane przez metode mousePressed().<br />
	 * - oddanie strzalu przez komputer na plansze gracza wywolywane z metody actionPerformed() za pomoca timera.
	 */
	private class RozgrywkaMouseListener
		extends MouseAdapter
		implements ActionListener
		{
		private Plansza oPlansza;
		private JComponentPlansza oCompPlansza;
		private Timer oTimer;
		public RozgrywkaMouseListener(Plansza oPlansza, JComponentPlansza oCompPlansza)
			{
			this.oPlansza = oPlansza;
			this.oCompPlansza = oCompPlansza;
			oTimer = new Timer(1000, this);
			oTimer.setRepeats(false);
			}
		public void setComponent(JComponentPlansza oCompPlansza)
			{
			this.oCompPlansza = oCompPlansza;
			}
		public boolean isSetComponent()
			{
			if (oCompPlansza == null)
				return false;
			else
				return true;
			}
		@Override public void mousePressed(MouseEvent event)
			{
			int iPlanszaSzerokosc = oPlansza.getSzerokosc();
			int iPlanszaWysokosc = oPlansza.getWysokosc();
			int iKomponentSzerokosc = oCompPlansza.getWidth();
			int iKomponentWysokosc = oCompPlansza.getHeight();
			int iClickX = event.getX();
			int iClickY = event.getY();
			Pozycja oKliknietePole;
			oKliknietePole = PlanszaWspolrzedneRysowania.pixToField(iKomponentSzerokosc, iKomponentWysokosc, iPlanszaSzerokosc, iPlanszaWysokosc, iClickX, iClickY);
			try
				{
				if (bKolejGracza == true
					&& oKliknietePole.getX() >= 0 && oKliknietePole.getX() < iPlanszaSzerokosc
					&& oKliknietePole.getY() >= 0 && oKliknietePole.getY() < iPlanszaWysokosc
					&& (oPlansza.getPole(oKliknietePole.getX(), oKliknietePole.getY()) == PlanszaTypPola.PLANSZA_POLE_PUSTE
						|| oPlansza.getPole(oKliknietePole.getX(), oKliknietePole.getY()) == PlanszaTypPola.PLANSZA_STATEK
						)
					)
					{
					bKolejGracza = false;
					int iIloscZatopionychPrzedStrzalem = oStatkiKomputer.getIloscZatopionychStatkow();
					
					//strzal na plansze komputera
					boolean bTrafienie;
					bTrafienie = oStatkiKomputer.strzal(oKliknietePole.getX(), oKliknietePole.getY());
					JComponentPlansza oComponentPlansza = (JComponentPlansza)oPanelPlanszeKontener.getComponent(1);
					oComponentPlansza.aktywujWyroznienie(oKliknietePole);
					//obsluga sprawdzania, czy koniec gry
					if (bTrafienie == true && oStatkiKomputer.getIloscStatkow() == oStatkiKomputer.getIloscZatopionychStatkow())
						{
						oStatusGry.zwyciestwoGracza();
						oComponentStatusGry.updateData();
						JOptionPane.showMessageDialog(JFrameOknoGry.this, LANG.getProperty("message.win"));
						return;
						}
					else if (bTrafienie == true)
						{
                                                    
                                                //COLOCAR AUDIO DE EXPLOSÃO
                                                
                                                InputStream in;
                                                
                                                    try {
                                                        
                                                        in = new FileInputStream(new File ("src\\pl\\vgtworld\\games\\statki\\components\\explosao.mp3"));
                                                        AudioStream ad = new AudioStream(in);
                                                        AudioPlayer.player.start(ad);
                                                  
                                                    } catch (Exception e) {
                                                    }
                                                    
						oComponentStatusGry.updateData();
						if (iIloscZatopionychPrzedStrzalem != oStatkiKomputer.getIloscZatopionychStatkow())
							oComponentWydarzenia.ustawPrawyKomunikat(LANG.getProperty("message.hit2"));
						else
							oComponentWydarzenia.ustawPrawyKomunikat(LANG.getProperty("message.hit1"));
						}
					
					oTimer.start();
					}
				}
			catch (ParametrException e)
				{
				throw new ProgramistaException(e);
				}
			}
		public void actionPerformed(ActionEvent oEvent)
			{
			int iIloscZatopionychPrzedStrzalem = oStatkiGracz.getIloscZatopionychStatkow();
			//strzal na plansze gracza
			boolean bTrafienie = oAi.strzal(oStatkiGracz);
			JComponentPlansza oComponentPlansza = (JComponentPlansza)oPanelPlanszeKontener.getComponent(0);
			oComponentPlansza.aktywujWyroznienie(oStatkiGracz.getOstatniStrzal());
			//obsluga sprawdzania, czy koniec gry
			if (bTrafienie == true && oStatkiGracz.getIloscStatkow() == oStatkiGracz.getIloscZatopionychStatkow())
				{
				oStatusGry.zwyciestwoKomputera();
				oComponentStatusGry.updateData();
				int iIloscKomponentow = oPanelPlanszeKontener.getComponentCount();
				JComponentPlansza oCompPlansza;
				for (int i = 0; i < iIloscKomponentow; ++i)
					{
					oCompPlansza = (JComponentPlansza)oPanelPlanszeKontener.getComponent(i);
					if (oCompPlansza != null)
						oCompPlansza.setWyswietlStatki(true);
					}
				oPanelPlanszeKontener.repaint();
				JOptionPane.showMessageDialog(JFrameOknoGry.this, LANG.getProperty("message.lose"));
				return;
				}
			else if (bTrafienie == true)
				{
				oComponentStatusGry.updateData();
				if (iIloscZatopionychPrzedStrzalem != oStatkiGracz.getIloscZatopionychStatkow())
					oComponentWydarzenia.ustawLewyKomunikat(LANG.getProperty("message.hit2"));
				else
					oComponentWydarzenia.ustawLewyKomunikat(LANG.getProperty("message.hit1"));
				}
			
			bKolejGracza = true;
			
			}
		}
	/**
	 * Konstruktor.
	 * 
	 * @param oStatusGry Obiekt przechowujacy informacje na temat aktualnego statusu gry.
	 * @param oUstawienia Obiekt przechowujacy ustawienia dotyczace rozgrywki.
	 */
	public JFrameOknoGry(StatusGry oStatusGry, Ustawienia oUstawienia)
		{
		this(oStatusGry, oUstawienia, MIN_SZEROKOSC, MIN_WYSOKOSC);
		}
	/**
	 * konstruktor przeciazaony pozwalajacy zdefiniowac rozmiar okna gry.
	 * 
	 * @param oStatusGry Obiekt przechowujacy informacje na temat aktualnego statusu gry.
	 * @param oUstawienia Obiekt przechowujacy ustawienia dotyczace rozgrywki.
	 * @param iSzerokosc Szerokosc okno gry w pixelach.
	 * @param iWysokosc Wysokosc okna gry w pixelach.
	 */
	public JFrameOknoGry(StatusGry oStatusGry, Ustawienia oUstawienia, int iSzerokosc, int iWysokosc)
		{
		InputStream oPlik = getClass().getResourceAsStream("/wersja.txt");
		if (oPlik != null)
			{
			Scanner oVer = new Scanner(oPlik);
			sWersja = oVer.nextLine();
			}
		else
			sWersja = null;
		
		Properties oDefaultLang = new Properties();
		try
			{
			InputStream oDefaultLangStream = getClass().getResourceAsStream("/pl/vgtworld/games/statki/lang/en_US.lang");
			if (oDefaultLangStream == null)
				{
				System.err.println("default language file not found");
				JOptionPane.showMessageDialog(null, "default language file not found", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
				}
			oDefaultLang.load(oDefaultLangStream);
			InputStream oLangStream = getClass().getResourceAsStream("/pl/vgtworld/games/statki/lang/" + Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry() + ".lang");
			LANG = new Properties(oDefaultLang);
			if (oLangStream != null)
				{
				BufferedReader oBuffereLangStream = new BufferedReader(new InputStreamReader(oLangStream, "UTF-8"));
				LANG.load(oBuffereLangStream);
				}
			}
		catch (IOException e)
			{
			System.err.println("error reading file");
			JOptionPane.showMessageDialog(null, "error reading file", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
			}

		//setMinimumSize(new Dimension(MIN_SZEROKOSC, MIN_WYSOKOSC));
		
		setTitle(JFrameOknoGry.LANG.getProperty("mainWindow.title"));
		//setSize(iSzerokosc, iWysokosc);
                setExtendedState(JFrameOknoGry.MAXIMIZED_BOTH);
                setUndecorated(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.oStatusGry = oStatusGry;
		this.oUstawienia = oUstawienia;
		oOknoUstawienia = new JDialogUstawienia(this, oUstawienia);
		oOknoUstawienia.setLocationRelativeTo(this);
		oOknoAbout = new JDialogAbout(this, JFrameOknoGry.LANG.getProperty("mainWindow.title"));
		if (sWersja != null)
			{
			oOknoAbout.setVersion(sWersja);
			oOknoAbout.rebuild();
			}
		
		//panel z planszami graczy
		oPanelPlanszeKontener = new JPanel();
		oPanelPlanszeKontener.setLayout(new GridLayout());
		if (oStatusGry.getGraRozpoczeta() == true && oStatusGry.getStatkiRozmieszczone() == true)
			add(oPanelPlanszeKontener, BorderLayout.CENTER);
		
		//panel z plansza do zaznaczania statkow po rozpoczeciu gry
		oPanelZaznaczanieStatkow = new JPanelZaznaczanieStatkow(oUstawienia, this);
		if (oStatusGry.getGraRozpoczeta() == true && oStatusGry.getStatkiRozmieszczone() == false)
			add(oPanelZaznaczanieStatkow, BorderLayout.CENTER);
		
		//obiekty akcji
		ActionNowaGra oActionNowaGra = new ActionNowaGra();
		ActionZakoncz oActionZakoncz = new ActionZakoncz();
		ActionUstawienia oActionUstawienia = new ActionUstawienia();
		
		//panel zastepujacy plansze przed rozpoczeciem rozgrywki
		oPanelPrzyciski = new JPanel();
		oPanelPrzyciski.setBackground(Color.BLACK);
		//oPanelPrzyciski.setLayout(new GridLayout());
		JButton oButtonNowaGra = new JButton(oActionNowaGra);
		JButton oButtonUstawienia = new JButton(oActionUstawienia);
		JButton oButtonZakoncz = new JButton(oActionZakoncz);
		oPanelPrzyciski.add(oButtonNowaGra);
		oPanelPrzyciski.add(oButtonUstawienia);
		oPanelPrzyciski.add(oButtonZakoncz);
		if (oStatusGry.getGraRozpoczeta() == false)
			add(oPanelPrzyciski, BorderLayout.CENTER);
		
		//obszar rysowania wydarzen
		oComponentWydarzenia = new JComponentWydarzenia();
		add(oComponentWydarzenia, BorderLayout.PAGE_START);
		
		//obszar rysowania statusu gry
		oComponentStatusGry = new JComponentStatusGry(this.oStatusGry);
		add(oComponentStatusGry, BorderLayout.PAGE_END);
		
		//menu
		oMenu = new JMenuBar();
		setJMenuBar(oMenu);
		
		//menu "statki"
		JMenu oMenuStatki = new JMenu(JFrameOknoGry.LANG.getProperty("menu.game"));
		JMenuItem oMenuStatkiNew = new JMenuItem(oActionNowaGra);
		JMenuItem oMenuStatkiClose = new JMenuItem(oActionZakoncz);
		oMenuStatki.add(oMenuStatkiNew);
		oMenuStatki.add(oMenuStatkiClose);
		oMenu.add(oMenuStatki);

		//menu "opcje"
		JMenu oMenuOpcje = new JMenu(JFrameOknoGry.LANG.getProperty("menu.options"));
		JMenuItem oMenuOpcjeConf = new JMenuItem(oActionUstawienia);
		oMenuOpcje.add(oMenuOpcjeConf);
		oMenu.add(oMenuOpcje);
		
		//menu "help"
		JMenu oMenuPomoc = new JMenu(JFrameOknoGry.LANG.getProperty("menu.help"));
		JMenuItem oMenuPomocAbout = new JMenuItem(new ActionAbout());
		oMenuPomoc.add(oMenuPomocAbout);
		oMenu.add(oMenuPomoc);
		
		//mapa wejsc
		InputMap oIMap = oPanelPrzyciski.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		oIMap.put(KeyStroke.getKeyStroke("F2"), "action.nowaGra");
		
		//mapa akcji
		ActionMap oAMap = oPanelPrzyciski.getActionMap();
		oAMap.put("action.nowaGra", oActionNowaGra);
		}
	/**
	 * Metoda dodaje do kontenera plansz przekazana w parametrze plansze.
	 * 
	 * @param oPlansza Plansza, ktora ma byc wyswietlana w kontenerze plansz.
	 * @param bWyswietlStatki Zmienna okreslajaca, czy na planszy maja byc wyswietlane takze nietrafione pola statkow.
	 */
	public void dodajPlansze(Plansza oPlansza, boolean bWyswietlStatki)
		{
		dodajPlansze(oPlansza, bWyswietlStatki, null);
		}
	/**
	 * Metoda dodaje do kontenera plansz przekazana w parametrze plansze.<br />
	 * 
	 * Wersja przeciazona, ktora dodatkowo pozwala przekazac listener klikniec na plansze.
	 * 
	 * @param oPlansza Plansza, ktora ma byc wyswietlana w kontenerze plansz.
	 * @param bWyswietlStatki Zmienna okreslajaca, czy na planszy maja byc wyswietlane takze nietrafione pola statkow.
	 * @param oMouseListener Obiekt obslugi zdarzen klikniec dla dodawanej planszy.
	 */
	public void dodajPlansze(Plansza oPlansza, boolean bWyswietlStatki, RozgrywkaMouseListener oMouseListener)
		{
		JComponentPlansza oCompPlansza = new JComponentPlansza(oPlansza);
		if (oMouseListener != null)
			{
			if (oMouseListener.isSetComponent() == false)
				oMouseListener.setComponent(oCompPlansza);
			oCompPlansza.addMouseListener(oMouseListener);
			}
		oCompPlansza.setWyswietlStatki(bWyswietlStatki);
		oPanelPlanszeKontener.add(oCompPlansza);
		}
	/**
	 * Metoda wywolywana przez panel rozmieszczania statkow, po tym jak zostanie zatwierdzone prawidlowe rozmieszczenie statkow gracza.
	 */
	public void rozpocznijRozgrywke()
		{
		oStatkiGracz = oPanelZaznaczanieStatkow.getStatki();
		oStatkiKomputer = JFrameOknoGry.generujGracza(oUstawienia);
		oComponentStatusGry.setStatkiGracz(oStatkiGracz);
		oComponentStatusGry.setStatkiKomputer(oStatkiKomputer);
		oComponentStatusGry.updateData();
		oAi = AiFactory.getAi(oUstawienia.getPoziomTrudnosci(), oUstawienia.getProsteLinie(), oStatkiKomputer);
		StatkiPozycjoner oPozycjoner = new StatkiPozycjoner();
		boolean bUdaneRozmieszczenie = false;
		boolean bContinue = true;
		try
			{
			while (bUdaneRozmieszczenie == false && bContinue == true)
				{
				bUdaneRozmieszczenie = oPozycjoner.rozmiescStatki(oStatkiKomputer, oUstawienia.getProsteLinie());
				if (bUdaneRozmieszczenie == false)
					{
					if (JOptionPane.showConfirmDialog(this, LANG.getProperty("errorMsg.shipPlacement.computerShipPlacementError"), LANG.getProperty("errorMsg.windowTitle"), JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
						bContinue = false;
					}
				}
			}
		catch(ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		if (bUdaneRozmieszczenie == false)
			{
			JOptionPane.showMessageDialog(this, LANG.getProperty("errorMsg.shipPlacement.computerShipPlacementFail"));
			}
		else
			{
			bKolejGracza = true;
			oPanelPlanszeKontener.removeAll();
			if (oPanelPlanszeKontener.getComponentCount() == 0)
				{
				dodajPlansze(oStatkiGracz.getPlansza(), true);
				dodajPlansze(oStatkiKomputer.getPlansza(), false, new RozgrywkaMouseListener(oStatkiKomputer.getPlansza(), null));
				}
			oPanelPrzyciski.setVisible(false);
			oPanelZaznaczanieStatkow.setVisible(false);
			oPanelPlanszeKontener.setVisible(true);
			add(oPanelPlanszeKontener, BorderLayout.CENTER);
			repaint();
			}
		}
	/**
	 * Metoda wywolywana przez okno ustawien w przypadku zmian w ustawieniach rozgrywki
	 * (zmiana wielkosci planszy, ilosci i/lub wielkosci statkow, poziomu trudnosci).<br />
	 * 
	 * Koryguje wymagane obiekty, aby dopasowac je do nowych ustawien i jesli byla rozpoczeta gra, anuluje ja i rozpoczyna nowa.
	 */
	public void zmianaUstawien()
		{
		oPanelZaznaczanieStatkow.resetujPlansze();
		oPanelZaznaczanieStatkow.resetujOpis();
		//oPanelZaznaczanieStatkow.repaint();
		if (oStatusGry.getGraRozpoczeta() == true)
			{
			Timer oTimer = new Timer(10, new ActionNowaGra());
			oTimer.setRepeats(false);
			oTimer.start();
			}
		}
	/**
	 * Metoda tworzy nowa plansze i nowy kontener zawierajacy statki dla gracza i zwraca obiekt kontenera.<br />
	 * 
	 * Rozmiar planszy, ilosc i rozmiar statkow sa ustalane na podstawie ustawien gry przekazanych w parametrze.
	 * 
	 * @param oUstawienia Ustawienia glowne gry.
	 * @return Zwraca kontener statkow gracza.
	 */
	public static StatekIterator generujGracza(Ustawienia oUstawienia)
		{
		Plansza oPlansza = new Plansza(oUstawienia.getPlanszaSzerokosc(), oUstawienia.getPlanszaWysokosc());
		StatekIterator oStatki = new StatekIterator(oPlansza);
		int[] aListaStatkow = oUstawienia.getStatki();
		for (int iRozmiar: aListaStatkow)
			oStatki.dodajStatek(iRozmiar);
		return oStatki;
		}
	}
