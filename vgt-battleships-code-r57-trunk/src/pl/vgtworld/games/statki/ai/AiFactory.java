package pl.vgtworld.games.statki.ai;

import pl.vgtworld.games.statki.StatekIterator;

/**
 * Fabryka obiektow AI.
 * 
 * @author VGT
 * @version 1.1
 */
abstract public class AiFactory
	{
	/**
	 * Metoda zwracajaca rozne obiekty AI na podstawie przekazanego w parametrze oczekiwanego poziomu trudnosci.
	 * 
	 * @param iPoziomTrudnosci Liczba z zakresu 1-100 informujaca o oczekiwanym poziomie trudnosci gracza komputerowego.
	 * @param bProsteLinie Okresla, czy statki moga byc tylko pionowymi/poziomymi liniami.
	 * Informacja jest zapisywana w tworzonym obiekcie Ai, gdyz jest niezbedna przy pozniejszym wyszukiwaniu pol do ostrzalu.
	 * @param oStatki Kontener statkow nalezacy do generowanego gracza komputerowego.
	 * @return Zwraca obiekt Ai zawierajacy sztuczna inteligencje gracza komputerowego.
	 */
	public static Ai getAi(int iPoziomTrudnosci, boolean bProsteLinie, StatekIterator oStatki)
		{
		Ai oAi;
		if (iPoziomTrudnosci > 66)
			oAi = new AiCheater(oStatki);
		else if (iPoziomTrudnosci > 33)
			oAi = new AiExtended(oStatki);
		else
			oAi = new AiBasic(oStatki);
		AiGeneric oAi2 = (AiGeneric)oAi;
		oAi2.setProsteLinie(bProsteLinie);
		return oAi;
		}
	}
