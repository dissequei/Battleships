package about;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import javax.swing.JComponent;

/**
 * Implementacja interface'u nasluchujacego zdarzen zwiazanych z mysza
 * realizujaca funkcjonalnosc linka html'owego otwierajacego adres www.
 * 
 * @author VGT
 */
public class MouseListenerWebsite
	implements MouseListener
	{
	private JComponent oComponent;
	private String sWebsiteAddress;
	private Color oHoverColor;
	private Color oDefaultColor;
	/**
	 * Konstruktor.
	 * 
	 * @param oComponent Referencja do komponentu, dla ktorego wykorzystany bedzie listener.
	 * @param sWebsiteAddress Adres www, ktory ma zostac otwarty po kliknieciu na komponent.
	 */
	public MouseListenerWebsite(JComponent oComponent, String sWebsiteAddress)
		{
		this(oComponent, sWebsiteAddress, Color.RED);
		}
	/**
	 * Konstruktor.
	 * 
	 * @param oComponent Referencja do komponentu, dla ktorego wykorzystany bedzie listener.
	 * @param sWebsiteAddress Adres www, ktory ma zostac otwart po kliknieciu na komponent.
	 * @param oHoverColor Kolor Foreground dla komponentu realizujacy efekt hover.
	 */
	public MouseListenerWebsite(JComponent oComponent, String sWebsiteAddress, Color oHoverColor)
		{
		this.oComponent = oComponent;
		this.sWebsiteAddress = sWebsiteAddress;
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
	 * Zmienia kolor foreground komponenty.
	 */
	public void mouseExited(MouseEvent arg0)
		{
		oComponent.setForeground(oDefaultColor);
		}
	/**
	 * Wywolanie adresu www w domyslnej przegladarce.
	 */
	public void mousePressed(MouseEvent arg0)
		{
		try
			{
			Desktop.getDesktop().browse(new URI(sWebsiteAddress));
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
