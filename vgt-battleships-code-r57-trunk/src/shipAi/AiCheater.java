package shipAi;

import ship.ShipIterator;

/**
 * Oszukujaca implmentacja interface'u AI.<br />
 * 
 * Dopoki komputer wygrywa, lub rozgrwka jest wyrownana, jedyne co AI realizuje, to ostrzeliwanie wczesniej
 * trafionych pol staku, lub strzelanie losowe, jesli zadnych pol trafionych nie ma.<br />
 * 
 * Gdy AI przegrywa, zaczyna oszukiwac i po wylosowaniu miejsca strzalu sprawdza, czy na danym polu przeciwnika
 * jest statek. Jesli pole jest puste, nie oddaje strzalu, tylko losuje ponownie inne pole.<br />
 * 
 * Ilosc prob uzalezniona jest od tego, jak bardzo AI przegrywa wzgledem gracza.
 * 
 * @author VGT
 * @version 1.0
 */
public class AiCheater
	extends AiGeneric
	implements Ai
	{
	/**
	 * Konstruktor.
	 * 
	 * @param oStatki Kontener statkow nalezacych do gracza sterowanego przez dany obiekt Ai.
	 */
	public AiCheater(ShipIterator oStatki)
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
			{
			//ustalenie, czy komputer przegrywa
			int iRoznica = oStatki.getIloscTrafionychStatkow() + oStatki.getIloscZatopionychStatkow() - oStatkiPrzeciwnika.getIloscTrafionychStatkow() - oStatkiPrzeciwnika.getIloscZatopionychStatkow();
			if (iRoznica > 0)
				{
				//komputer przegrywa
				int iIloscDozwolonychProb = 1 + (oStatkiPrzeciwnika.getIloscNieuszkodzonychStatkow() - oStatki.getIloscNieuszkodzonychStatkow());
				return strzalWielokrotny(oStatkiPrzeciwnika, iIloscDozwolonychProb);
				}
			else
				{
				//komputer wygrywa
				return strzalLosowy(oStatkiPrzeciwnika);
				}
			}
		}
	}
