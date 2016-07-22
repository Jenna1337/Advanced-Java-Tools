package utils;

public class StringTools
{
	private StringTools(){}
	
	public static String titleCase(String text)
	{
		return Character.toUpperCase(text.charAt(0))+text.toLowerCase().substring(1);
	}
	
	
}
