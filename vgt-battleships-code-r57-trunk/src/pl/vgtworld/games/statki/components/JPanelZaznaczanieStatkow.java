package pl.vgtworld.games.statki.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pl.vgtworld.exceptions.ParametrException;
import pl.vgtworld.exceptions.ProgramistaException;
import pl.vgtworld.games.statki.Plansza;
import pl.vgtworld.games.statki.PlanszaTypPola;
import pl.vgtworld.games.statki.PlanszaWspolrzedneRysowania;
import pl.vgtworld.games.statki.StatekIterator;
import pl.vgtworld.games.statki.StatkiGenerator;
import pl.vgtworld.games.statki.StatkiPozycjoner;
import pl.vgtworld.games.statki.Ustawienia;
import pl.vgtworld.tools.Pozycja;

/**
 * Panel wykorzystywany do obslugi rozmieszczenia statkow na planszy przez gracza.
 * 
 * @author VGT
 * @version 1.0
 */
public class JPanelZaznaczanieStatkow
	extends JPanel
	{
	/**
	 * Obiekt ustawien rozgrywki.
	 */
	private Ustawienia oUstawienia;
	/**
	 * Plansza, na ktorej gracz zaznacza statki.
	 */
	private Plansza oPlansza;
	/**
	 * Kontener statkow tworzony dla gracza po zakonczeniu rozmieszczania statkow.
	 */
	private StatekIterator oStatki;
	/**
	 * Referencja do glownego okna gry.
	 */
	private JFrameOknoGry oOkno;
	/**
	 * Komponent, na ktorym wyswietlana jest plansza.
	 */
	private JComponentPlansza oComponentPlansza;
	/**
	 * Panel wyswietlajacy informacje na temat ilosci statkow poszczegolnych rozmiarow,
	 * ktore nalezy umiescic na planszy.
	 */
	private JPanelZaznaczanieStatkowLista oListaStatkowInfo;
	/**
	 * Panel zawierajacy informacje o wymaganych statkach oraz przyciski akcji.
	 */
	private JPanel oPanelPrawy;
	/**
	 * Obiekt obslugi akcji klikniecia myszki na planszy.
	 */
	private ZaznaczanieStatkowMouseListener oMouseListener;
	/**
	 * Klasa prywatna zawierajaca obsluge akcji wcisniecia przycisku zatwierdzajacego rozmieszczenie statkow na planszy.
	 */
	private class ActionZatwierdzStatki
		extends AbstractAction
		{
		public ActionZatwierdzStatki()
			{
			putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.shipPlacement.accept"));
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.shipPlacement.accept.desc"));
			}
		@Override public void actionPerformed(ActionEvent event)
			{
			StatkiGenerator oGenerator = new StatkiGenerator(oPlansza);
			oStatki = oGenerator.generujStatki();
			boolean bOK = true;
			//sprawdzenie, kolejnych warunkow rozmieszczenia statkow
			if (oStatki.getIloscStatkow() != oUstawienia.getIloscStatkow())
				bOK = false;
			for (int i = oStatki.getMaxRozmiarStatku(); i >= 1; --i)
				if (oStatki.getIloscStatkow(i) != oUstawienia.getIloscStatkow(i))
					bOK = false;
			if (oStatki.weryfikujRozmieszczenie(oUstawienia.getProsteLinie()) == false)
				bOK = false;
			//commit
			if (bOK == false)
				{
				JOptionPane.showMessageDialog(JPanelZaznaczanieStatkow.this, JFrameOknoGry.LANG.getProperty("errorMsg.shipPlacement.invalidShipPlacement"));
				oStatki = null;
				}
			else
				oOkno.rozpocznijRozgrywke();
			}
		}
	/**
	 * Klasa prywatna zawierajaca obsluge akcji wcisniecia przycisku usuwajacego wszystkie statki z planszy.
	 */
	private class ActionWyczysc
		extends AbstractAction
		{
		public ActionWyczysc()
			{
			putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.shipPlacement.clear"));
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.shipPlacement.clear.desc"));
			}
		@Override public void actionPerformed(ActionEvent event)
			{
			try
				{
				for (int i = 0; i < oPlansza.getSzerokosc(); ++i)
					for (int j = 0; j < oPlansza.getWysokosc(); ++j)
						if (oPlansza.getPole(i, j) == PlanszaTypPola.PLANSZA_STATEK)
							oPlansza.setPole(i, j, PlanszaTypPola.PLANSZA_POLE_PUSTE);
				repaint();
				}
			catch (ParametrException e)
				{
				throw new ProgramistaException(e);
				}
			}
		}
	/**
	 * Klasa prywatna zawierajaca obsluge wcisniecia przycisku rozmieszczajacego statki gracza losowo na planszy.
	 */
	private class ActionRozmiescLosowoStatkiGracza
		extends AbstractAction
		{
		public ActionRozmiescLosowoStatkiGracza()
			{
			putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.shipPlacement.random"));
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.shipPlacement.random.desc"));
			}
		@Override public void actionPerformed(ActionEvent event)
			{
			try
				{
				for (int i = 0; i < oPlansza.getSzerokosc(); ++i)
					for (int j = 0; j < oPlansza.getWysokosc(); ++j)
						if (oPlansza.getPole(i, j) == PlanszaTypPola.PLANSZA_STATEK)
							oPlansza.setPole(i, j, PlanszaTypPola.PLANSZA_POLE_PUSTE);
				StatekIterator oKontener = new StatekIterator(oPlansza);
				int[] aStatki = oUstawienia.getStatki();
				for (int iRozmiar: aStatki)
					oKontener.dodajStatek(iRozmiar);
				StatkiPozycjoner oPozycjoner = new StatkiPozycjoner();
				if (oPozycjoner.rozmiescStatki(oKontener, oUstawienia.getProsteLinie()) == false)
					JOptionPane.showMessageDialog(JPanelZaznaczanieStatkow.this, JFrameOknoGry.LANG.getProperty("errorMsg.shipPlacement.randomShipPlacementFail"));
				}
			catch (ParametrException e)
				{
				throw new ProgramistaException(e);
				}
			repaint();
			}
		}
	/**
	 * Klasa prywatna zawierajaca obsluge klikniecia gracza na planszy (zaznaczanie/odznaczanie pol statkow).
	 */
	private class ZaznaczanieStatkowMouseListener
		extends MouseAdapter
		{
		public ZaznaczanieStatkowMouseListener()
			{
			}
		@Override public void mousePressed(MouseEvent event)
			{
			int iPlanszaSzerokosc = oPlansza.getSzerokosc();
			int iPlanszaWysokosc = oPlansza.getWysokosc();
			int iKomponentSzerokosc = oComponentPlansza.getWidth();
			int iKomponentWysokosc = oComponentPlansza.getHeight();
			int iClickX = event.getX();
			int iClickY = event.getY();
			Pozycja oKliknietePole;
			oKliknietePole = PlanszaWspolrzedneRysowania.pixToField(iKomponentSzerokosc, iKomponentWysokosc, iPlanszaSzerokosc, iPlanszaWysokosc, iClickX, iClickY);
			try
				{
				if (oKliknietePole.getX() >= 0 && oKliknietePole.getX() < iPlanszaSzerokosc
					&& oKliknietePole.getY() >= 0 && oKliknietePole.getY() < iPlanszaWysokosc
					)
					{
					if (oPlansza.getPole(oKliknietePole.getX(), oKliknietePole.getY()) == PlanszaTypPola.PLANSZA_POLE_PUSTE)
						{
						oPlansza.setPole(oKliknietePole.getX(), oKliknietePole.getY(), PlanszaTypPola.PLANSZA_STATEK);
//						oComponentPlansza.aktywujWyroznienie(oKliknietePole.getX(), oKliknietePole.getY());
						}
					else if (oPlansza.getPole(oKliknietePole.getX(), oKliknietePole.getY()) == PlanszaTypPola.PLANSZA_STATEK)
						{
						oPlansza.setPole(oKliknietePole.getX(), oKliknietePole.getY(), PlanszaTypPola.PLANSZA_POLE_PUSTE);
//						oComponentPlansza.aktywujWyroznienie(oKliknietePole.getX(), oKliknietePole.getY());
						}
					Pozycja oWspTopLeft;
					Pozycja oWspBottomRight;
					oWspTopLeft = PlanszaWspolrzedneRysowania.fieldToPixTopLeft(iKomponentSzerokosc, iKomponentWysokosc, iPlanszaSzerokosc, iPlanszaWysokosc, oKliknietePole.getX(), oKliknietePole.getY());
					oWspBottomRight = PlanszaWspolrzedneRysowania.fieldToPixBottomRight(iKomponentSzerokosc, iKomponentWysokosc, iPlanszaSzerokosc, iPlanszaWysokosc, oKliknietePole.getX(), oKliknietePole.getY());
					oComponentPlansza.repaint(oWspTopLeft.getX(), oWspTopLeft.getY(), oWspBottomRight.getX()-oWspTopLeft.getX(), oWspBottomRight.getY() - oWspTopLeft.getY());
					}
				}
			catch (ParametrException e)
				{
				throw new ProgramistaException(e);
				}
			}
		}
	/**
	 * Konstruktor.
	 * 
	 * @param oUstawienia Glowne ustawienia gry.
	 * @param oOkno Glowne okno gry.
	 */
	public JPanelZaznaczanieStatkow(Ustawienia oUstawienia, JFrameOknoGry oOkno)
		{
		setLayout(new GridLayout(1, 2));
		this.oUstawienia = oUstawienia;
		this.oOkno = oOkno;
		oPlansza = new Plansza(oUstawienia.getPlanszaSzerokosc(), oUstawienia.getPlanszaWysokosc());
		oStatki = null;
		oComponentPlansza = new JComponentPlansza(oPlansza);
		oMouseListener = new ZaznaczanieStatkowMouseListener();
		
		addMouseListener(oMouseListener);
		
		//lewa polowka
		oComponentPlansza = new JComponentPlansza(oPlansza);
		add(oComponentPlansza);
		
		//prawa polowka
		oPanelPrawy = new JPanel();
		oPanelPrawy.setLayout(new BorderLayout());
		oListaStatkowInfo = new JPanelZaznaczanieStatkowLista(oUstawienia);
		JScrollPane oListaStatkowInfoScroll = new JScrollPane(oListaStatkowInfo);
		oListaStatkowInfoScroll.setBorder(null);
		
		JPanel oButtonContainer = new JPanel();
		oButtonContainer.setLayout(new GridLayout(1,3));
		JButton oButtonZatwierdz = new JButton(new ActionZatwierdzStatki());
		JButton oButtonWyczysc = new JButton(new ActionWyczysc());
		JButton oButtonLosuj = new JButton(new ActionRozmiescLosowoStatkiGracza());
		oButtonContainer.add(oButtonZatwierdz);
		oButtonContainer.add(oButtonLosuj);
		oButtonContainer.add(oButtonWyczysc);
		oPanelPrawy.add(oButtonContainer, BorderLayout.PAGE_END);
		oPanelPrawy.add(oListaStatkowInfoScroll, BorderLayout.CENTER);
		add(oPanelPrawy);
		}
	/**
	 * Metoda zwraca plansze, na ktorej zaznaczane sa statki.
	 * 
	 * @return Plansza z zaznaczonymi statkami.
	 */
	public Plansza getPlansza()
		{
		return oPlansza;
		}
	/**
	 * Metoda zwraca obiekt kontenera statkow stworznych przez gracza.<br />
	 * 
	 * Jesli statki nie zostaly rozmieszczone, lub zostaly rozmieszczone nieprawidlowo, metoda zwroci pusta referencje.
	 * 
	 * @return Kontener statkow gracza.
	 */
	public StatekIterator getStatki()
		{
		return oStatki;
		}
	/**
	 * Metoda usuwa wszystkie statki umieszczone na planszy.
	 */
	public void wyczyscPlansze()
		{
		oPlansza.wyczysc();
		}
	/**
	 * Metoda zmienia rozmiar planszy na podstawie aktualnego stanu obiektu ustawien.<br />
	 * 
	 * Wywolywana po zmianie ustawien rozgrywki.
	 */
	public void resetujPlansze()
		{
		//oPlansza = new Plansza(oUstawienia.getPlanszaSzerokosc(), oUstawienia.getPlanszaWysokosc());
		try
			{
			if (oUstawienia.getPlanszaSzerokosc() != oPlansza.getSzerokosc() || oUstawienia.getPlanszaWysokosc() != oPlansza.getWysokosc())
				oPlansza.zmienRozmiar(oUstawienia.getPlanszaSzerokosc(), oUstawienia.getPlanszaWysokosc());
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	/**
	 * Metoda aktualizuje panel z informacjami na temat ilosci wymaganych do umieszczenia statkow na planszy. 
	 */
	public void resetujOpis()
		{
		oListaStatkowInfo.odswiez();
		}
	}
