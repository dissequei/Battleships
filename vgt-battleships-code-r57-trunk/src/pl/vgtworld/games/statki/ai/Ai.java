package pl.vgtworld.games.statki.ai;

import pl.vgtworld.games.statki.StatekIterator;

/**
 * Interface zawierajacy metody wymagane dla klas AI.
 * 
 * @author VGT
 * @version 1.0
 */
public interface Ai
	{
	/**
	 * Glowna metoda obslugujaca strzal na plansze przeciwnika.
	 * 
	 * @param oStatkiPrzeciwnika Kontener statkow przeciwnika.
	 * @return Zwraca TRUE w przypadku trafenia statku, lub FALSE, jesli strzal byl niecelny.
	 */
	public boolean strzal(StatekIterator oStatkiPrzeciwnika);
	}
