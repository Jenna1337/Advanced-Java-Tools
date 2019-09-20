package sys.math.numberbuilder;

public class UnsupportedLocaleException extends Exception
{
	public UnsupportedLocaleException(String localeTitle,
			Exception e){
		this(localeTitle);
		initCause(e);
	}
	public UnsupportedLocaleException(String localeTitle){
		super("Locale \""+localeTitle+"\" is not currently supported.");
	}
}
