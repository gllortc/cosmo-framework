package com.cosmo.util;

/**
 * Utilidades para cadenas de texto (String).
 * 
 * @author Gerard Llort
 */
public class StringUtils 
{
   /**
	 * Permet esbrinar si una cadena est� vac�a o nula. 
	 * 
	 * @param text Cadena de car�cteres a evaluar.
	 * @return {@code true} si la cadena est� vac�a o nula o {@code false} en qualsevol otro caso.
	 */
	public static boolean isNullOrEmpty(String text)
	{
		if (text == null) 
      {
         return true;
      }
		
		return (text.isEmpty());
	}
	
	/**
	 * Indica si una cadena est� vac�a o es nula. 
	 * 
	 * @param text Cadena de car�cteres a evaluar.
	 * @return {@code true} si la cadena est� vac�a o es nula o {@code false} en cualquier otro caso.
	 */
	public static boolean isNullOrEmptyTrim(String text)
	{
		if (text == null) 
      {
         return true;
      }
		
		text = text.trim();
		
		return (text.isEmpty());
	}
}
