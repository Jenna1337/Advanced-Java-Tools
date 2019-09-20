package sys.math;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import sys.math.numberbuilder.AbstractNumberBuilder;
import sys.math.numberbuilder.UnsupportedLocaleException;

public class NumberBuilder2
{
	private NumberBuilder2(){}
	
	private static Locale defaultLocale = Locale.US;
	
	private static String wordify(String number){
		try{
			return wordify(number, defaultLocale);
		}
		catch(UnsupportedLocaleException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return number;
		}
	}
	private static String wordify(String number, Locale locale) throws UnsupportedLocaleException{
		AbstractNumberBuilder builder = getBuilder(locale);
		try{
			return (String)builder.getClass().getMethod("wordify", String.class).invoke(builder, number);
		}
		catch(IllegalAccessException | InvocationTargetException |
				NoSuchMethodException e){
			e.printStackTrace();
			return number;
		}
	}
	
	private static AbstractNumberBuilder getBuilder(String localeTitle) throws UnsupportedLocaleException{
		localeTitle = localeTitle.toUpperCase();
		if(builders.containsKey(localeTitle))
			return builders.get(localeTitle);
		try{
			Class<?> cls = clsLoader.loadClass(localePkgLocation + localeTitle);
			if(AbstractNumberBuilder.class.isAssignableFrom(cls)){
				builders.put(localeTitle, (AbstractNumberBuilder)cls.newInstance());
				return builders.get(localeTitle);
			}
			throw new UnsupportedLocaleException(localeTitle);
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException e){
			throw new UnsupportedLocaleException(localeTitle, e);
		}
	}
	private static AbstractNumberBuilder getBuilder(Locale locale) throws UnsupportedLocaleException{
		return getBuilder(locale.toString());
	}
	
	private static final String localePkgLocation = NumberBuilder2.class.getPackage().getName()+".numberbuilder.locales.";
	private static final ClassLoader clsLoader = ClassLoader.getSystemClassLoader();
	private static HashMap<String, AbstractNumberBuilder> builders = new HashMap<>();
	
	public static void setDefaultLocale(Locale locale) throws UnsupportedLocaleException{
		getBuilder(locale);
		defaultLocale = locale;
	}
	
	public static String getName(java.math.BigDecimal val){
		return wordify(val.toPlainString());
	}
	public static String getName(java.math.BigInteger val){
		return wordify(val.toString());
	}
	public static String getName(Number val)
	{
		return wordify(val.toString());
	}
	public static String getName(String val)
	{
		return wordify(val);
	}
	
	public static String getName(java.math.BigDecimal val, Locale locale) throws UnsupportedLocaleException{
		return wordify(val.toPlainString(), locale);
	}
	public static String getName(java.math.BigInteger val, Locale locale) throws UnsupportedLocaleException{
		return wordify(val.toString(), locale);
	}
	public static String getName(Number val, Locale locale) throws UnsupportedLocaleException
	{
		return wordify(val.toString(), locale);
	}
	public static String getName(String val, Locale locale) throws UnsupportedLocaleException
	{
		return wordify(val, locale);
	}
}
