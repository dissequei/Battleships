package ship;

import java.awt.EventQueue;
import components.JFrameGameWindow;

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
                    
                Splash splash = new Splash();
	                   
		EventQueue.invokeLater(new Runnable()
				{
				public void run()
					{
					Settings oUstawienia = new Settings();
					GameStatus oStatusGry = new GameStatus();
                                        
                                        JFrameGameWindow oOkno = new JFrameGameWindow(oStatusGry, oUstawienia, 800, 600);
					oOkno.setVisible(true);
					}
				}
			);
		}
	}
