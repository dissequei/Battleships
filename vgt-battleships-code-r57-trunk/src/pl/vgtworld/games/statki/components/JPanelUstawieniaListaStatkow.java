package pl.vgtworld.games.statki.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pl.vgtworld.exceptions.ParametrException;
import pl.vgtworld.exceptions.ProgramistaException;


/**
 * Panel zawierajacy narzedzia do zarzadzania lista statkow w ustawieniach.
 * 
 * @author VGT
 * @version 1.0
 */
public class JPanelUstawieniaListaStatkow
	extends JPanel
	{
	/**
	 * Obiekt listy przechowujacy statki.
	 */
	private JListUstawieniaListaStatkow oListaStatkow;
	/**
	 * Klasa prywatna realizujaca akcje dodania statku.
	 */
	private class ActionDodaj
		extends AbstractAction
		{
		public ActionDodaj()
			{
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.settings.shipList.add.desc"));
			URL oImgUrl = getClass().getResource("/pl/vgtworld/games/statki/img/button-add.png");
			if (oImgUrl == null)
				putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.settings.shipList.add"));
			else
				{
				Image oImg = Toolkit.getDefaultToolkit().getImage(oImgUrl);
				putValue(Action.SMALL_ICON, new ImageIcon(oImg));
				}
			}
		public void actionPerformed(ActionEvent oEvent)
			{
			try
				{
				oListaStatkow.listaDodaj(1);
				}
			catch (ParametrException e)
				{
				throw new ProgramistaException(e);
				}
			}
		}
	/**
	 * Klasa prywatna realizujaca akcje usuniecia zaznaczonych statkow.
	 */
	private class ActionUsun
		extends AbstractAction
		{
		public ActionUsun()
			{
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.settings.shipList.delete.desc"));
			URL oImgUrl = getClass().getResource("/pl/vgtworld/games/statki/img/button-delete.png");
			if (oImgUrl == null)
				putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.settings.shipList.delete"));
			else
				{
				Image oImg = Toolkit.getDefaultToolkit().getImage(oImgUrl);
				putValue(Action.SMALL_ICON, new ImageIcon(oImg));
				}
			}
		public void actionPerformed(ActionEvent oEvent)
			{
			int[] aZaznaczone = oListaStatkow.getSelectedIndices();
			if (aZaznaczone.length == 0)
				JOptionPane.showMessageDialog(JPanelUstawieniaListaStatkow.this, JFrameOknoGry.LANG.getProperty("errorMsg.settings.shipList.noShipSelected"));
			try
				{
				for (int i = aZaznaczone.length - 1; i >= 0; --i)
					oListaStatkow.listaUsun(aZaznaczone[i]);
				}
			catch (ParametrException e)
				{
				throw new ProgramistaException(e);
				}
			}
		}
	/**
	 * Klasa prywatna realizujaca akcje powiekszenia rozmiaru zaznaczonych statkow.
	 */
	private class ActionPowieksz
		extends AbstractAction
		{
		public ActionPowieksz()
			{
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.settings.shipList.increase.desc"));
			URL oImgUrl = getClass().getResource("/pl/vgtworld/games/statki/img/button-up.png");
			if (oImgUrl == null)
				putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.settings.shipList.increase"));
			else
				{
				Image oImg = Toolkit.getDefaultToolkit().getImage(oImgUrl);
				putValue(Action.SMALL_ICON, new ImageIcon(oImg));
				}
			}
		public void actionPerformed(ActionEvent oEvent)
			{
			int[] aZaznaczone = oListaStatkow.getSelectedIndices();
			if (aZaznaczone.length == 0)
				JOptionPane.showMessageDialog(JPanelUstawieniaListaStatkow.this, JFrameOknoGry.LANG.getProperty("errorMsg.settings.shipList.noShipSelected"));
			try
				{
				for (int iZaznaczony: aZaznaczone)
					oListaStatkow.listaPowieksz(iZaznaczony);
				}
			catch (ParametrException e)
				{
				throw new ProgramistaException(e);
				}
			}
		}
	/**
	 * Klasa prywatna realizujaca akcje pomniejszenia rozmiaru zaznaczonych statkow.
	 */
	private class ActionPomniejsz
		extends AbstractAction
		{
		public ActionPomniejsz()
			{
			//putValue(Action.NAME, "Pomniejsz zaznaczone");
			putValue(Action.SHORT_DESCRIPTION, JFrameOknoGry.LANG.getProperty("action.settings.shipList.decrease.desc"));
			URL oImgUrl = getClass().getResource("/pl/vgtworld/games/statki/img/button-down.png");
			if (oImgUrl == null)
				putValue(Action.NAME, JFrameOknoGry.LANG.getProperty("action.settings.shipList.decrease"));
			else
				{
				Image oImg = Toolkit.getDefaultToolkit().getImage(oImgUrl);
				putValue(Action.SMALL_ICON, new ImageIcon(oImg));
				}
			}
		public void actionPerformed(ActionEvent oEvent)
			{
			int[] aZaznaczone = oListaStatkow.getSelectedIndices();
			if (aZaznaczone.length == 0)
				JOptionPane.showMessageDialog(JPanelUstawieniaListaStatkow.this, JFrameOknoGry.LANG.getProperty("errorMsg.settings.shipList.noShipSelected"));
			try
				{
				for (int iZaznaczony: aZaznaczone)
					oListaStatkow.listaPomniejsz(iZaznaczony);
				}
			catch (ParametrException e)
				{
				throw new ProgramistaException(e);
				}
			}
		}
	/**
	 * Konstruktor domyslny.
	 */
	public JPanelUstawieniaListaStatkow()
		{
		setLayout(new BorderLayout());
		
		JLabel oListaStatkowLabel = new JLabel(JFrameOknoGry.LANG.getProperty("settings.shipList.title"), JLabel.CENTER);
		oListaStatkow = new JListUstawieniaListaStatkow();
		JScrollPane oListaStatkowScroll = new JScrollPane(oListaStatkow);
		//panel przyciskow
		JPanel oPanelPrzyciski = new JPanel();
		oPanelPrzyciski.setLayout(new GridLayout(1, 4));
		oPanelPrzyciski.add(new JButton(new ActionDodaj()));
		oPanelPrzyciski.add(new JButton(new ActionUsun()));
		oPanelPrzyciski.add(new JButton(new ActionPowieksz()));
		oPanelPrzyciski.add(new JButton(new ActionPomniejsz()));
		
		add(oListaStatkowLabel, BorderLayout.PAGE_START);
		add(oListaStatkowScroll, BorderLayout.CENTER);
		add(oPanelPrzyciski, BorderLayout.PAGE_END);
		}
	/**
	 * Metoda zwracajaca obiekt listy statkow.
	 * 
	 * @return Lista statkow.
	 */
	public JListUstawieniaListaStatkow getListaStatkow()
		{
		return oListaStatkow;
		}
	}
