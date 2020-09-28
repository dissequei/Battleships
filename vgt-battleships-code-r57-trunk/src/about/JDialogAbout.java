package about;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Klasa wyswietlajaca okno dialogowe z informacjami kontaktowymi.
 * 
 * @author VGT
 */
public class JDialogAbout
	extends JDialog
	{
	private String sSoftwareName;
	private Frame oOwner;
	private ArrayList<JLabel> oEtykiety;
	private ArrayList<JLabel> oWartosci;
	private Font oFontName;
	private JButton oCloseButton;
	private JLabel oVersion;
	private Image oLogo;
	/**
	 * Klasa implementujaca akcje wcisniecia klawisza zamykajacego okno
	 * 
	 * @author VGT
	 */
	private class CloseAction
		extends AbstractAction
		{
		public CloseAction()
			{
			putValue(AbstractAction.NAME, "OK");
			}
		public void actionPerformed(ActionEvent oEvent)
			{
			JDialogAbout.this.setVisible(false);
			}
		}
	/**
	 * Konstruktor.
	 * 
	 * @param oOwner Okno nadrzedne wzgledem tworzonego okna dialogowego.
	 * @param sSoftName Nazwa programu, dla ktorego tworzone jest okno dialogowe.
	 */
	public JDialogAbout(Frame oOwner, String sSoftName)
		{
		super(oOwner, true);
		this.sSoftwareName = sSoftName;
		this.oOwner = oOwner;
		oEtykiety = new ArrayList<JLabel>();
		oWartosci = new ArrayList<JLabel>();
		oFontName = new Font("Serif", Font.BOLD, 18);
		oCloseButton = new JButton(new CloseAction());
		oVersion = null;
		
		URL oLogoUrl = getClass().getResource("logo.png");
		if (oLogoUrl != null)
			{
			try
				{
				oLogo = ImageIO.read(oLogoUrl);
				}
			catch (IOException oException)
				{
				oLogo = null;
				}
			}
		
		setLayout(new GridBagLayout());
		setTitle("About");
		setLocationRelativeTo(this.oOwner);
		//setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		//elements
		Cursor oCursorHand = new Cursor(Cursor.HAND_CURSOR);
		JLabel oMail = new JLabel("tomek@vgtworld.pl");
		MouseListenerMail oMailClick = new MouseListenerMail(oMail, "tomek@vgtworld.pl");
		JLabel oWebsite = new JLabel("www.vgtworld.pl");
		MouseListenerWebsite oWebsiteClick = new MouseListenerWebsite(oWebsite, "www.vgtworld.pl");
		oMail.setCursor(oCursorHand);
		oMail.addMouseListener(oMailClick);
		oWebsite.setCursor(oCursorHand);
		oWebsite.addMouseListener(oWebsiteClick);
		
		addElement("Author:", "VGT", false);
		addElement(new JLabel("E-Mail:"), oMail, false);
		addElement(new JLabel("Website:"), oWebsite, false);
		rebuild();
		
		pack();
		}
	/**
	 * Pozwala ustawic wersje programu, ktora bedzie wyswietlana, jako informacja w oknie dialogowym.
	 * 
	 * @param sVersion Wersja programu.
	 */
	public void setVersion(String sVersion)
		{
		oVersion = new JLabel("ver. " + sVersion);
		}
	/**
	 * Wykonuje wszystkie czynnosci zwiazane z rozmiesczeniem informacji w oknie dialogowym.
	 * 
	 * Metoda jest wywolywana automatycznie po dodaniu elementow za pomoca metod {@link #addElement(String, String)} i {@link #addElement(JLabel, JLabel)}.
	 * Jesli dodawane jest wiele elementow, jest to nie wskazane z powodow wydajnosciowych. Wtedy nalezy skorzystac z metod
	 * {@link #addElement(String, String, boolean)} i {@link #addElement(JLabel, JLabel, boolean)} przekazujac w trzecim parametrze FALSE
	 * i dopiero po dodaniu wszystkiech elementow wywolac metode rebuild.
	 */
	public void rebuild()
		{
		JLabel oNazwaLabel = new JLabel(sSoftwareName, JLabel.CENTER);
		GridBagConstraints oNazwaGBC = new GridBagConstraints();
		oNazwaGBC.gridx = 0;
		oNazwaGBC.gridy = 0;
		oNazwaGBC.gridwidth = 3;
		oNazwaGBC.gridheight = 1;
		oNazwaGBC.weightx = 100;
		oNazwaGBC.weighty = 100;
		oNazwaGBC.anchor = GridBagConstraints.CENTER;
		oNazwaGBC.insets = new Insets(10, 10, 10, 10);
		oNazwaLabel.setFont(oFontName);
		add(oNazwaLabel, oNazwaGBC);
		
		for (int i = 0; i < oEtykiety.size(); ++i)
			{
			GridBagConstraints oItemLabelGBC = new GridBagConstraints();
			oItemLabelGBC.gridx = 1;
			oItemLabelGBC.gridy = i + 1;
			oItemLabelGBC.gridwidth = 1;
			oItemLabelGBC.gridheight = 1;
			oItemLabelGBC.weightx = 0;
			oItemLabelGBC.weighty = 100;
			oItemLabelGBC.anchor = GridBagConstraints.WEST;
			oItemLabelGBC.insets = new Insets(2, 15, 2, 15);
			
			
			GridBagConstraints oItemGBC = new GridBagConstraints();
			oItemGBC.gridx = 2;
			oItemGBC.gridy = i + 1;
			oItemGBC.gridwidth = 1;
			oItemGBC.gridheight = 1;
			oItemGBC.weightx = 100;
			oItemGBC.weighty = 100;
			oItemGBC.anchor = GridBagConstraints.WEST;
			oItemGBC.insets = new Insets(2, 0, 2, 15);
			
			add(oEtykiety.get(i), oItemLabelGBC);
			add(oWartosci.get(i), oItemGBC);
			}
		
		GridBagConstraints oButtonCloseGBC = new GridBagConstraints();
		oButtonCloseGBC.gridx = 1;
		oButtonCloseGBC.gridy = oEtykiety.size() + 1;
		oButtonCloseGBC.gridwidth = 2;
		oButtonCloseGBC.gridheight = 1;
		oButtonCloseGBC.weightx = 100;
		oButtonCloseGBC.weighty = 100;
		oButtonCloseGBC.anchor = GridBagConstraints.CENTER;
		oButtonCloseGBC.insets = new Insets(10, 0, 10, 0);
		add(oCloseButton, oButtonCloseGBC);
		
		if (oVersion != null)
			{
			GridBagConstraints oVersionGBC = new GridBagConstraints();
			oVersionGBC.gridx = 1;
			oVersionGBC.gridy = oEtykiety.size() + 2;
			oVersionGBC.gridwidth = 2;
			oVersionGBC.gridheight = 1;
			oVersionGBC.weightx = 100;
			oVersionGBC.weighty = 100;
			oVersionGBC.anchor = GridBagConstraints.SOUTHEAST;
			oVersionGBC.insets = new Insets(0, 0, 5, 5);
			add(oVersion, oVersionGBC);
			}
		
		if (oLogo != null)
			{
			JLabel oLogo = new JLabel();
			oLogo.setIcon(new ImageIcon(this.oLogo));
			GridBagConstraints oLogoGBC = new GridBagConstraints();
			oLogoGBC.gridx = 0;
			oLogoGBC.gridy = 1;
			oLogoGBC.gridwidth = 1;
			if (oVersion != null)
				oLogoGBC.gridheight = oEtykiety.size() + 2;
			else
				oLogoGBC.gridheight = oEtykiety.size() + 1;
			oLogoGBC.weightx = 0;
			oLogoGBC.weighty = 100;
			oLogoGBC.anchor = GridBagConstraints.NORTH;
			oLogoGBC.insets = new Insets(0, 10, 0, 0);
			add(oLogo, oLogoGBC);
			}
		}
	/**
	 * Wykonuje centrowanie okna wzgledem okna nadrzednego.
	 * Metode nalezy wywolac w akcji wyswietlajacej okno dialogowe bezposrednio przed ustwieniem widocznosci.
	 */
	public void centerPosition()
		{
		int iX = oOwner.getX() + (oOwner.getWidth() - getWidth()) / 2;
		int iY = oOwner.getY() + (oOwner.getHeight() - getHeight()) / 2;
		setBounds(iX, iY, getWidth(), getHeight());
		}
	/**
	 * Dodaje nowy element do listy prezentowanych informacji.
	 * 
	 * Metoda po dodaniu elementu automatycznie wywoluje metode {@link #rebuild()} w celu aktualizacji widoku okna.
	 * Jesli jest to niewskazane, nalezy skorzystac z metody {@link #addElement(String, String, boolean)}.
	 * 
	 * @param sLabel Nazwa dodawanej wlasnosci.
	 * @param sValue Wartosc dodawanej wlasnosci.
	 */
	public void addElement(String sLabel, String sValue)
		{
		addElement(new JLabel(sLabel), new JLabel(sValue), true);
		}
	/**
	 * Dodaje nowy element do listy prezentowanych informacji.
	 * 
	 * @param sLabel Nazwa dodawanej wlasnosci.
	 * @param sValue Wartosc dodawanej wlasnosci.
	 * @param bRebuild Okresla, czy po dodaniu elementu ma byc wywolana metoda rebuild.
	 */
	public void addElement(String sLabel, String sValue, boolean bRebuild)
		{
		addElement(new JLabel(sLabel), new JLabel(sValue), bRebuild);
		}
	/**
	 * Dodaje nowy element do listy prezentowanych informacji.
	 * 
	 * Metoda po dodaniu elementu automatycznie wywoluje metode {@link #rebuild()} w celu aktualizacji widoku okna.
	 * Jesli jest to niewskazane. nalezy skorzystac z metody {@link #addElement(JLabel, JLabel, boolean)}.
	 * 
	 * @param oLabel Nazwa Dodawanej wlasnosci.
	 * @param oValue Wartosc dodawanej wlasnosci.
	 */
	public void addElement(JLabel oLabel, JLabel oValue)
		{
		addElement(oLabel, oValue, true);
		}
	/**
	 * Dodaje nowy element do listy prezentowanych informacji.
	 * 
	 * @param oLabel Nazwa dodawanej wlasnosci.
	 * @param oValue Wartosc dodawanej wlasnosci.
	 * @param bRebuild Okresla, czy po dodaniu elementu ma byc wywolana metoda rebuild.
	 */
	public void addElement(JLabel oLabel, JLabel oValue, boolean bRebuild)
		{
		oEtykiety.add(oLabel);
		oWartosci.add(oValue);
		if (bRebuild == true)
			rebuild();
		}
	}
