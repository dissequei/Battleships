package pl.vgtworld.games.statki.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import pl.vgtworld.exceptions.ParametrException;
import pl.vgtworld.exceptions.ProgramistaException;
import pl.vgtworld.games.statki.Ustawienia;

/**
 * Okno ustawien gry.
 * 
 * @author VGT
 * @version 1.0
 */
public class JDialogUstawienia
	extends JDialog
	{
	/**
	 * Szerokosc okna ustawien.
	 */
	public static final int SZEROKOSC = 600;
	/**
	 * Wysokosc okna ustawien.
	 */
	public static final int WYSOKOSC = 450; 
	/**
	 * Referencja do glownego okna gry.
	 */
	private JFrameOknoGry oOknoGlowne;
	/**
	 * Obiekt przechowujacy ustawienia rozgrywki.
	 */
	private Ustawienia oUstawienia;
	/**
	 * Slider pozwalajacy ustawic szerokosc planszy.
	 */
	private JSlider oPlanszaSzerokoscSlider;
	/**
	 * Slider pozwalajacy ustawic wysokosc planszy.
	 */
	private JSlider oPlanszaWysokoscSlider;
	/**
	 * Slider pozwalajacy ustawic poziom trudnosci komputera.
	 */
	private JSlider oPoziomTrudnosci;
	/**
	 * Pole tekstowe przechowujace szerokosc planszy.
	 */
	private JTextField oPlanszaSzerokosc;
	/**
	 * Pole tekstowe przechowujace wysokosc planszy.
	 */
	private JTextField oPlanszaWysokosc;
	/**
	 * Checkbox zawierajacy informacje, czy statki moga byc tylko prostymi liniami.
	 */
	private JCheckBox oStatkiProsteLinie;
	/**
	 * Przycisk zapisujacy zmiany w ustawieniach.
	 */
	private JButton oButtonZapisz;
	/**
	 * Przycisk anulujacy zmiany w ustawieniach.
	 */
	private JButton oButtonAnuluj;
	/**
	 * Panel do obslugi tworzenia listy statkow.
	 */
	private JPanelUstawieniaListaStatkow oListaStatkow;
	/**
	 * Checkbox okreslajacy, czy zapisac aktualne ustawienia, jako domyslne.
	 */
	private JCheckBox oZapiszUstawienia;
	/**
	 * Klasa prywatna zawierajaca obsluge akcji przesuniecia slidera okreslajacego szerokosc planszy.
	 */
	private class ActionSzerokoscSlider
		implements ChangeListener
		{
		public void stateChanged(ChangeEvent oEvent)
			{
			JSlider oSource = (JSlider)oEvent.getSource();
			oPlanszaSzerokosc.setText(String.valueOf(oSource.getValue()));
			}
		}
	/**
	 * Klasa prywatna zawierajaca obsluge akcji przesuniecia slidera okreslajacego wysokosc planszy.
	 */
	private class ActionWysokoscSlider
		implements ChangeListener
		{
		public void stateChanged(ChangeEvent oEvent)
			{
			JSlider oSource = (JSlider)oEvent.getSource();
			oPlanszaWysokosc.setText(String.valueOf(oSource.getValue()));
			}
		}
	/**
	 * Klasa prywatna zawierajaca obsluge akcji wcisniecia przycisku anulujacego zmiany w ustawieniach. 
	 */
	private class ActionAnuluj
		extends AbstractAction
		{
		public ActionAnuluj()
			{
			putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.settings.cancel"));
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.settings.cancel.desc"));
			}
		public void actionPerformed(ActionEvent oEvent)
			{
			setVisible(false);
			}
		}
	/**
	 * Klasa prywatna zawierajaca obsluge akcji wcisniecia przycisku zatwierdzajacego zmiany w ustawieniach.
	 */
	private class ActionZapisz
		extends AbstractAction
		{
		public ActionZapisz()
			{
			putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.settings.save"));
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.settings.save.desc"));
			}
		public void actionPerformed(ActionEvent oEvent)
			{
			try
				{
				//sprawdzenie blednych danych w wartosciach ustawien
				int iPlanszaSzerokosc;
				int iPlanszaWysokosc;
				try
					{
					iPlanszaSzerokosc = Integer.parseInt(oPlanszaSzerokosc.getText());
					iPlanszaWysokosc = Integer.parseInt(oPlanszaWysokosc.getText());
					}
				catch (NumberFormatException e)
					{
					JOptionPane.showMessageDialog(JDialogUstawienia.this, JFrameOknoGry.LANG.getProperty("errorMsg.settings.invalidBoardSize"));
					return;
					}
				if (iPlanszaSzerokosc < 1 || iPlanszaWysokosc < 1)
					{
					JOptionPane.showMessageDialog(JDialogUstawienia.this, JFrameOknoGry.LANG.getProperty("errorMsg.settings.invalidBoardSize"));
					return;
					}
				if (oListaStatkow.getListaStatkow().getIloscStatkow() == 0)
					{
					JOptionPane.showMessageDialog(JDialogUstawienia.this, JFrameOknoGry.LANG.getProperty("errorMsg.settings.noShip"));
					return;
					}
				//zapisanie ustawien
				oUstawienia.setPlanszaSzerokosc(iPlanszaSzerokosc);
				oUstawienia.setPlanszaWysokosc(iPlanszaWysokosc);
				oUstawienia.setPoziomTrudnosci(oPoziomTrudnosci.getValue());
				if (oStatkiProsteLinie.isSelected() == true)
					oUstawienia.setProsteLinie(true);
				else
					oUstawienia.setProsteLinie(false);
				oUstawienia.usunStatki();
				int[] aLista = oListaStatkow.getListaStatkow().getListaStatkow();
				for (int iRozmiar: aLista)
					oUstawienia.dodajStatek(iRozmiar);
				
				oOknoGlowne.zmianaUstawien();
				
				if (oZapiszUstawienia.isSelected())
					oUstawienia.zapiszUstawieniaDomyslne();
				}
			catch (ParametrException e)
				{
				throw new ProgramistaException(e);
				}
			setVisible(false);
			}
		}
	/**
	 * Konstruktor.
	 */
	public JDialogUstawienia(JFrameOknoGry oOknoGlowne, Ustawienia oUstawienia)
		{
		super(oOknoGlowne, true);
		this.oOknoGlowne = oOknoGlowne;
		this.oUstawienia = oUstawienia;
		//szerokosc planszy
		JLabel oPlanszaSzerokoscLabel = new JLabel(JFrameOknoGry.LANG.getProperty("settings.boardWidth"), JLabel.CENTER);
		oPlanszaSzerokoscSlider = new JSlider(5, 25, oUstawienia.getPlanszaSzerokosc());
		oPlanszaSzerokoscSlider.addChangeListener(new ActionSzerokoscSlider());
		oPlanszaSzerokosc = new JTextField(5);
		oPlanszaSzerokosc.setHorizontalAlignment(JTextField.RIGHT);
		oPlanszaSzerokosc.setText(String.valueOf(oPlanszaSzerokoscSlider.getValue()));
		//wysokosc planszy
		JLabel oPlanszaWysokoscLabel = new JLabel(JFrameOknoGry.LANG.getProperty("settings.boardHeight"), JLabel.CENTER);
		oPlanszaWysokoscSlider = new JSlider(5, 25, oUstawienia.getPlanszaWysokosc());
		oPlanszaWysokoscSlider.addChangeListener(new ActionWysokoscSlider());
		oPlanszaWysokosc = new JTextField(5);
		oPlanszaWysokosc.setHorizontalAlignment(JTextField.RIGHT);
		oPlanszaWysokosc.setText(String.valueOf(oPlanszaWysokoscSlider.getValue()));
		//poziom trudnosci
		JLabel oPoziomTrudnosciLabel = new JLabel(JFrameOknoGry.LANG.getProperty("settings.difficulty"), JLabel.CENTER);
		oPoziomTrudnosci = new JSlider(1, 100, oUstawienia.getPoziomTrudnosci());
		//ksztalkt statkow
		JLabel oStatkiProsteLinieLabel = new JLabel(JFrameOknoGry.LANG.getProperty("settings.shipsShape"), JLabel.CENTER);
		oStatkiProsteLinie = new JCheckBox(JFrameOknoGry.LANG.getProperty("settings.shipsShapeCheckbox"));
		if (oUstawienia.getProsteLinie() == true)
			oStatkiProsteLinie.setSelected(true);
		//lista statkow
		oListaStatkow = new JPanelUstawieniaListaStatkow();
		int[] aStatki = oUstawienia.getStatki();
		try
			{
			for (int iRozmiar: aStatki)
				oListaStatkow.getListaStatkow().listaDodaj(iRozmiar);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		
		//wstawienie elementow do frame'a
		setLayout(new BorderLayout());
		JPanel oPanelLewy = new JPanel();
		oPanelLewy.setLayout(new GridLayout(4, 1));
		
		JPanel oPlanszaSzerokoscContainer2 = new JPanel();
		JPanel oPlanszaSzerokoscContainer = new JPanel();
		oPlanszaSzerokoscContainer.setLayout(new GridLayout(3, 1));
		oPlanszaSzerokoscContainer.add(oPlanszaSzerokoscLabel);
		JPanel oPlanszaSzerokoscTextfieldContainer = new JPanel();
		oPlanszaSzerokoscTextfieldContainer.add(oPlanszaSzerokosc);
		oPlanszaSzerokoscContainer.add(oPlanszaSzerokoscSlider);
		oPlanszaSzerokoscContainer.add(oPlanszaSzerokoscTextfieldContainer);
		oPlanszaSzerokoscContainer2.add(oPlanszaSzerokoscContainer);
		oPanelLewy.add(oPlanszaSzerokoscContainer2);
		
		JPanel oPlanszaWysokoscContainer2 = new JPanel();
		JPanel oPlanszaWysokoscContainer = new JPanel();
		oPlanszaWysokoscContainer.setLayout(new GridLayout(3, 1));
		oPlanszaWysokoscContainer.add(oPlanszaWysokoscLabel);
		JPanel oPlanszaWysokoscTextfieldContainer = new JPanel();
		oPlanszaWysokoscTextfieldContainer.add(oPlanszaWysokosc);
		oPlanszaWysokoscContainer.add(oPlanszaWysokoscSlider);
		oPlanszaWysokoscContainer.add(oPlanszaWysokoscTextfieldContainer);
		oPlanszaWysokoscContainer2.add(oPlanszaWysokoscContainer);
		oPanelLewy.add(oPlanszaWysokoscContainer2);
		
		JPanel oPoziomTrudnosciContainer2 = new JPanel();
		JPanel oPoziomTrudnosciContainer = new JPanel();
		oPoziomTrudnosciContainer.setLayout(new GridLayout(2,1));
		oPoziomTrudnosciContainer.add(oPoziomTrudnosciLabel);
		JPanel oPoziomTrudnosciSliderContainer = new JPanel();
		oPoziomTrudnosciSliderContainer.add(oPoziomTrudnosci);
		oPoziomTrudnosciContainer.add(oPoziomTrudnosciSliderContainer);
		oPoziomTrudnosciContainer2.add(oPoziomTrudnosciContainer);
		oPanelLewy.add(oPoziomTrudnosciContainer2);
		
		JPanel oStatkiKsztaltContainer2 = new JPanel();
		JPanel oStatkiKsztaltContainer = new JPanel();
		oStatkiKsztaltContainer.setLayout(new GridLayout(2, 1));
		
                oStatkiKsztaltContainer.add(oStatkiProsteLinieLabel);
		oStatkiKsztaltContainer.add(oStatkiProsteLinie);
                
		oStatkiKsztaltContainer2.add(oStatkiKsztaltContainer);
		oPanelLewy.add(oStatkiKsztaltContainer2);
		
		JPanel oPanelPrawy = new JPanel();
		oPanelPrawy.setLayout(new BorderLayout());
		oPanelPrawy.add(oListaStatkow, BorderLayout.CENTER);
		
		JPanel oPanele = new JPanel();
		oPanele.setLayout(new GridLayout(1, 2));
		oPanele.add(oPanelLewy);
		oPanele.add(oPanelPrawy);
		add(oPanele, BorderLayout.CENTER);
		JPanel oButtony = new JPanel();
		oButtonAnuluj = new JButton(new ActionAnuluj());
		oButtonZapisz = new JButton(new ActionZapisz());
		oButtony.add(oButtonZapisz);
		oButtony.add(oButtonAnuluj);
		
		JPanel oPanelZapiszUstawienia = new JPanel();
		oZapiszUstawienia = new JCheckBox(JFrameOknoGry.LANG.getProperty("settings.saveAsDefault"));
		oPanelZapiszUstawienia.add(oZapiszUstawienia);
		
		JPanel oPanelOpcjeDolne = new JPanel();
		oPanelOpcjeDolne.setLayout(new GridLayout(2, 1));
		oPanelOpcjeDolne.add(oPanelZapiszUstawienia);
		oPanelOpcjeDolne.add(oButtony);
		
		add(oPanelOpcjeDolne, BorderLayout.PAGE_END);
		
		
		//pozostale ustawienia
		setMinimumSize(new Dimension(SZEROKOSC, WYSOKOSC));
		setTitle(JFrameOknoGry.LANG.getProperty("settingsWindow.title"));
		setLocationRelativeTo(null);
		setResizable(true);
		}
	/**
	 * Metoda przywraca ustawienia wszystkich sliderow i pol tekstowych na wartosci z obiektu ustawien.
	 */
	public void reset()
		{
		//rest pozycji okna
		int iPozycjaX = oOknoGlowne.getX() + (oOknoGlowne.getWidth() - SZEROKOSC) / 2;
		int iPozycjaY = oOknoGlowne.getY() + (oOknoGlowne.getHeight() - WYSOKOSC) / 2;
		setBounds(iPozycjaX, iPozycjaY, SZEROKOSC, WYSOKOSC);
		//reset ustawien
		oPlanszaSzerokosc.setText(String.valueOf(oUstawienia.getPlanszaSzerokosc()));
		oPlanszaSzerokoscSlider.setValue(oUstawienia.getPlanszaSzerokosc());
		oPlanszaWysokosc.setText(String.valueOf(oUstawienia.getPlanszaWysokosc()));
		oPlanszaWysokoscSlider.setValue(oUstawienia.getPlanszaWysokosc());
		oPoziomTrudnosci.setValue(oUstawienia.getPoziomTrudnosci());
		if (oUstawienia.getProsteLinie() == true)
			oStatkiProsteLinie.setSelected(true);
		else
			oStatkiProsteLinie.setSelected(false);
		oListaStatkow.getListaStatkow().listaWyczysc();
		int[] aStatki = oUstawienia.getStatki();
		try
			{
			for (int iRozmiar: aStatki)
				oListaStatkow.getListaStatkow().listaDodaj(iRozmiar);
			}
		catch (ParametrException e)
			{
			throw new ProgramistaException(e);
			}
		}
	}
