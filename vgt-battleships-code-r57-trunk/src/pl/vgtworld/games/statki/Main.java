package pl.vgtworld.games.statki;

import java.awt.EventQueue;
import pl.vgtworld.games.statki.components.JFrameOknoGry;

/**
 * Klasa z startowa metoda main().
 * 
 * @author VGT
 * @version 1.0
 */
public class Main
	{
	/**
	 * Metoda startowa programu.
	 */
	public static void main(String[] args)
		{
                    
                new Splash();
                                            
	                   
		EventQueue.invokeLater(
			new Runnable()
				{
				public void run()
					{
					Ustawienia oUstawienia = new Ustawienia();
					StatusGry oStatusGry = new StatusGry();
                                        JFrameOknoGry oOkno = new JFrameOknoGry(oStatusGry, oUstawienia/* ,800, 600***REFATORAÇÃO CHANGE FUNCTION DECLARATION****/);
					oOkno.setVisible(true);
                                       
					}
				}
			);
   		}
	}
