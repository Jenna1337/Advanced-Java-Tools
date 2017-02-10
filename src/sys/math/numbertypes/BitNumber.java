package sys.math.numbertypes;

import java.math.BigDecimal;

public class BitNumber extends BasicNumber
{
	public BitNumber(Number n)
	{
		this(n.toString());
	}
	public BitNumber(String val)
	{
		//TODO
	}
	public static SuperNumber parse(String val)
	{
		return new SuperNumber(val);
	}
	public static SuperNumber parse(Number n)
	{
		return new SuperNumber(n.toString());
	}
	public BigDecimal toBigDecimal()
	{
		// TODO Auto-generated method stub
		return null;
	}
	public BasicNumber add(Number n)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public BasicNumber subtract(Number n)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public BasicNumber multiply(Number n)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public BasicNumber divide(Number n)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public BasicNumber remainder(Number n)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public BasicNumber mod(Number n)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
