package utils;

import java.util.ArrayList;

public class StringTools
{
	private StringTools(){}
	
	/**
	 * Capitalizes the first {@code Character} in the {@code String}.
	 * <br><br>
	 * <b>Note:</b> This method cannot handle 
	 * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html#supplementary">
	 * supplementary</a> characters.
	 * @param text The text to convert
	 * @return {@code text}, with the first {@code Character} capitalized.
	 * @see Character#toUpperCase(char)
	 */
	public static String titleCase(String text)
	{
		return Character.toUpperCase(text.charAt(0))+text.toLowerCase().substring(1);
	}
	/**
	 * Returns a {@code String} object representing the specified {@code int} (Unicode code point). 
	 * The result is a string consisting of the character specified by the Unicode code point.
	 * @param codepoint the Unicode code point to be converted
	 * @return the string representation of the specified Unicode code point
	 */
	public static String Unicode(int codepoint)
	{
		return new String(Character.toChars(codepoint));
	}
	public static ArrayList<String> split(String string, String delim)
	{
		ArrayList<String> splts = new ArrayList<String>();
		int floc;
		while((floc=string.indexOf(delim))>=0)
		{
			string = string.substring(floc);
			int eloc = string.indexOf(delim, delim.length());
			splts.add(string.substring(0, eloc>=0?eloc:string.length()));
		}
		return splts;
	}
	public static boolean endsWithIgnoreCase(String string, String suffix)
	{
		try
		{
			return string.substring(string.length()-suffix.length()).equalsIgnoreCase(suffix);
		}
		catch(StringIndexOutOfBoundsException e)
		{
			return false;
		}
	}
}
