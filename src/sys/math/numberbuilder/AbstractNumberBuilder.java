package sys.math.numberbuilder;

public abstract class AbstractNumberBuilder
{
	protected abstract String wordify(String number);
	
	public final String getName(java.math.BigDecimal val){
		return wordify(val.toPlainString());
	}
	public final String getName(java.math.BigInteger val){
		return wordify(val.toString());
	}
	public final String getName(Number val)
	{
		return wordify(val.toString());
	}
	public final String getName(String val)
	{
		return wordify(val);
	}
}
