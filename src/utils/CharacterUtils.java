package utils;

import java.lang.reflect.Field;
import java.util.Map;

public class CharacterUtils
{
	private CharacterUtils()
	{
	}
	public static void writeUnicodeData(Integer MIN, Integer MAX)
	{
		if(MIN==null)
			MIN=Character.MIN_CODE_POINT;
		if(MAX==null)
			MAX=Character.MAX_CODE_POINT;
		for(int c = MIN; c <= MAX; ++c)
		{
			if(Character.isDefined(c))
			{
				System.out.printf("%6s= %s;%s\n", String.format("%04x", c),
						getWritableName(c), getType(c));
				
				
				Character.isValidCodePoint(c);
				Character.isBmpCodePoint(c);
				Character.isLowerCase(c);
				Character.isUpperCase(c);
				Character.isTitleCase(c);
				Character.isDigit(c);
				Character.isDefined(c);
				Character.isLetter(c);
				Character.isLetterOrDigit(c);
				Character.isAlphabetic(c);
				Character.isIdeographic(c);
				Character.isSpaceChar(c);
				Character.isISOControl(c);
				Character.getName(c);
				
			}
		}
	}
	private static Object getWritableName(int codePoint)
	{
		return isPrintable(codePoint) ? StringUtils.Unicode(codePoint)
				: Character.getName(codePoint);
	}
	public static boolean isPrintable(int codePoint)
	{
		return Character.isDefined(codePoint)
				&& !Character.isSpaceChar(codePoint)
				&& !Character.isISOControl(codePoint);
	}
	
	private static Map<Byte, String> types = getTypes();
	
	private static Map<Byte, String> getTypes()
	{
		Map<Byte, String> map = new java.util.TreeMap<Byte, String>();
		Field[] fields = ClassUtils.getFieldsOfType(Character.class,
				byte.class);
		for(Field f : fields)
		{
			try
			{
				if(!f.getName().startsWith("DIRECTIONALITY"))
					map.put((Byte) f.get(null), f.getName());
			}
			catch(Exception e)
			{
				throw new InternalError(e);
			}
		}
		return map;
	}
	public static String getType(int codePoint)
	{
		return types.get((byte) Character.getType(codePoint));
	}
}
