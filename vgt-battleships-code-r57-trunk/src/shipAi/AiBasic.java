package shipAi;

import ship.ShipIterator;

/**
 * Najprostrza mozliwa implementacja Ai.<br />
 * 
 * Strzela zawsze w losowo wybrane pole na planszy niezaleznie od tego, czy sa jakies trafione, niezatopione statki.
 * 
 * @author VGT
 * @version 1.1
 */
public class AiBasic
	extends AiGeneric
	implements Ai
	{
	/**
	 * Konstruktor.
	 * 
	 * @param oStatki Kontener statkow nalezacych do gracza sterowanego przez dany obiekt AI.
	 */
	public AiBasic(ShipIterator oStatki)
		{
		super(oStatki);
		}
	/**
	 * Implementacja metody interface'u Ai.
	 */
	public boolean strzal(ShipIterator oStatkiPrzeciwnika)
		{
		return strzalLosowy(oStatkiPrzeciwnika);
		}
	}
