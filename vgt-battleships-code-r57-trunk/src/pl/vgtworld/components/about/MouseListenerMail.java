package pl.vgtworld.components.about;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import javax.swing.JComponent;

/**
 * Implementacja interface'u nasluchujacego zdarzen zwiazanych z mysza
 * realizujaca funkcjonalnosc linka html'owego do adresu e-mail.
 * 
 * @author VGT
 */
public class MouseListenerMail
	implements MouseListener
	{
	private JComponent oComponent;
	private String sEmailAddress;
	private String sName;
	private Color oHoverColor;
	private Color oDefaultColor;
	/**
	 * Konstruktor.
	 * 
	 * @param oComponent Referencja do kompomentu, dla ktorego wykorzystany bedzie listener.
	 * @param sEmailAddress Adres E-Mail, do ktorego ma byc tworzona wiadomosc po kliknieciu na komponent.
	 */
	public MouseListenerMail(JComponent oComponent, String sEmailAddress)
		{
		this(oComponent, sEmailAddress, null, Color.RED);
		}
	/**
	 * Konstruktor.
	 * 
	 * @param oComponent Referencja do komponentu, dla ktorego wykorzystany bedzie listener.
	 * @param sEmailAddress Adres E-Mail, do ktorego ma byc tworzona wiadomosc po kliknieciu na komponent.
	 * @param sName Imie / nazwisko / nazwa adresata.
	 */
	public MouseListenerMail(JComponent oComponent, String sEmailAddress, String sName)
		{
		this(oComponent, sEmailAddress, sName, Color.RED);
		}
	/**
	 * Konstruktor.
	 * 
	 * @param oComponent Referencja do komponentu, dla ktorego wykorzystany bedzie listener.
	 * @param sEmailAddress Adres E-Mail, do ktorego ma byc tworzona wiadomosc po kliknieciu na komponent.
	 * @param oHoverColor Kolor foreground dla komponentu realizujacy efekt hover.
	 */
	public MouseListenerMail(JComponent oComponent, String sEmailAddress, Color oHoverColor)
		{
		this(oComponent, sEmailAddress, null, oHoverColor);
		}
	/**
	 * Konstruktor.
	 * 
	 * @param oComponent Referencja do komponenty, dla ktorego wykorzystany bedzie listener.
	 * @param sEmailAddress Adres E-Mail, do ktorego ma byc tworzona wiadomosc po kliknieciu na komponent.
	 * @param sName Imie / nazwisko / nazwa adresata.
	 * @param oHoverColor Kolor foreground dla komponentu realizujacy efekt hover.
	 */
	public MouseListenerMail(JComponent oComponent, String sEmailAddress, String sName, Color oHoverColor)
		{
		this.oComponent = oComponent;
		this.sEmailAddress = sEmailAddress;
		this.sName = sName;
		this.oHoverColor = oHoverColor;
		this.oDefaultColor = oComponent.getForeground();
		}
	/**
	 * Empty.
	 */
	public void mouseClicked(MouseEvent oEvent)
		{
		}
	/**
	 * Zmienia kolor foreground komponentu.
	 */
	public void mouseEntered(MouseEvent arg0)
		{
		oComponent.setForeground(oHoverColor);
		}
	/**
	 * Zmienia kolor foreground komponentu.
	 */
	public void mouseExited(MouseEvent arg0)
		{
		oComponent.setForeground(oDefaultColor);
		}
	/**
	 * Otwarcie okna tworzenia nowej wiadomosci w domyslnym kliencie poczty.
	 */
	public void mousePressed(MouseEvent arg0)
		{
		try
			{
			if (sName == null)
				Desktop.getDesktop().mail(new URI("mailto:" + sEmailAddress));
			else
				Desktop.getDesktop().mail(new URI("mailto:" + sName + "<" + sEmailAddress + ">"));
			}
		catch (Exception oException)
			{
			
			}
		}
	/**
	 * Empty.
	 */
	public void mouseReleased(MouseEvent arg0)
		{
		}
	}
