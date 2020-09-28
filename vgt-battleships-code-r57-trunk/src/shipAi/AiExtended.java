package shipAi;

import ship.ShipIterator;

/**
 * Prosta implementacja interface'u Ai.<br />
 * 
 * Strzela w losowo wybrane pole, dopoki nie trafi w statek.
 * Po trafieniu w statek, jesli ma on wiecej pol,
 * sa ostrzeliwane sasiednie pola az do zatopienia statku.
 *  
 * @author VGT
 * @version 1.0
 */
public class AiExtended
	extends AiGeneric
	implements Ai
	{
	/**
	 * Konstruktor.
	 * 
	 * @param oStatki Kontener statkow nalezacych do gracza sterowanego przez dany obiekt Ai.
	 */
	public AiExtended(ShipIterator oStatki)
		{
		super(oStatki);
		}
	/**
	 * Implementacja metody interface'u Ai.
	 */
	public boolean strzal(ShipIterator oStatkiPrzeciwnika)
		{
		if (oUzyteczneTrafienia.size() > 0)
			{
			return strzalSasiadujacy(oStatkiPrzeciwnika);
			}
		else
			return strzalLosowy(oStatkiPrzeciwnika);
		}
	}
