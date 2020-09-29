package pl.vgtworld.games.statki.components;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pl.vgtworld.games.statki.Ustawienia;

/**
 * Panel wyswietlajacy informacje na temat ilosci wymaganych statkow w poszczegolnych rozmiarach.
 * 
 * @author VGT
 * @version 1.0
 */
public class JPanelZaznaczanieStatkowLista
	extends JPanel
	{
	/**
	 * Obiekt ustawien, z ktorego sa wczytywane informacje na temat wymaganych statkow.
	 */
	private Ustawienia oUstawienia;
	/**
	 * Kontener etykiet.
	 */
	private ArrayList<JLabel> oEtykiety;
	/**
	 * Kolor czcionki wyswietlanych informacji.
	 */
	private Color oTextColor;
	/**
	 * Konstruktor.
	 * 
	 * @param oUstawienia Obiekt ustawien glownych gry.
	 */
	public JPanelZaznaczanieStatkowLista(Ustawienia oUstawienia)
		{
		setBackground(Color.BLACK);
		oTextColor = new Color(230, 230, 230);
		this.oUstawienia = oUstawienia;
		oEtykiety = new ArrayList<JLabel>();
		BoxLayout oLay = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(oLay);
		
		odswiez();
		}
	/**
	 * Metoda odswieza wyswietlane informacje wczytujac je na nowo z obiektu ustawien.
	 */
	public void odswiez()
		{
		//utworzenie etykiet
		if (oEtykiety.size() == 0)
			{
			JLabel oEtykieta = new JLabel(JFrameOknoGry.LANG.getProperty("shipPlacement.list.header"));
			oEtykieta.setForeground(oTextColor);
			oEtykieta.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			oEtykiety.add(oEtykieta);
			}
		int iMaxRozmiar = oUstawienia.getMaxRozmiarStatku();
		int iLinieTekstu = 1;
		int iIlosc;
		String sText;
		for (int i = iMaxRozmiar; i >= 1; --i)
			{
			iIlosc = oUstawienia.getIloscStatkow(i);
			if (iIlosc > 0)
				{
				int iKlasaStatku = i > 5 ? 5 : i;
				if (i == 1)
					sText = JFrameOknoGry.LANG.getProperty("shipPlacement.list.prefix") + " " + JFrameOknoGry.LANG.getProperty("shipName.size1.plural") + " (" + JFrameOknoGry.LANG.getProperty("shipPlacement.list.size") +" 1): ";
				else
					sText = JFrameOknoGry.LANG.getProperty("shipPlacement.list.prefix") + " " + JFrameOknoGry.LANG.getProperty("shipName.size" + iKlasaStatku + ".plural") + " (" + JFrameOknoGry.LANG.getProperty("shipPlacement.list.size") + " " + i + "): ";
				++iLinieTekstu;
				if (oEtykiety.size() < iLinieTekstu)
					{
					JLabel oEtykieta = new JLabel(sText + iIlosc);
					oEtykieta.setForeground(oTextColor);
					oEtykieta.setAlignmentX(JLabel.CENTER_ALIGNMENT);
					oEtykiety.add(oEtykieta);
					}
				else
					oEtykiety.get(iLinieTekstu - 1).setText(sText + iIlosc);
				}
			}
		
		//wrzucenie etykiet w panel
		removeAll();
		for (int i = 0; i < iLinieTekstu; ++i)
			add(oEtykiety.get(i));
		
		validate();
		repaint();
		}
	}
