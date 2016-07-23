package utils;

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
	public String Unicode(int codepoint)
	{
		char[] cs = Character.toChars(codepoint);
		String s="";
		for(char c : cs)
			s+=c;
		return s;
	}
}
