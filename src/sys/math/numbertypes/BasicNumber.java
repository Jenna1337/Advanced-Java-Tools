package sys.math.numbertypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import sys.math.interfaces.SimpleMathFunctions;

public abstract class BasicNumber extends Number implements SimpleMathFunctions<BasicNumber>
{
	public double doubleValue()
	{
		return this.toBigDecimal().doubleValue();
	}
	public float floatValue()
	{
		return this.toBigDecimal().floatValue();
	}
	public byte byteValue()
	{
		return this.toBigDecimal().byteValue();
	}
	public short shortValue()
	{
		return this.toBigDecimal().shortValue();
	}
	public int intValue()
	{
		return this.toBigDecimal().intValue();
	}
	public long longValue()
	{
		return this.toBigDecimal().longValue();
	}
	/**
	 * @return {@code true} if this is equal to 0.
	 */
	public boolean booleanValue()
	{
		return this.equals(0);
	}
	public char charValue()
	{
		return (char)this.toBigDecimal().shortValue();
	}
	public int compareTo(Number n)
	{
		return this.toBigDecimal().compareTo(new BigDecimal(n.toString()));
	}
	public BigInteger toBigInteger()
	{
		return this.toBigDecimal().toBigInteger();
	}
	public abstract BigDecimal toBigDecimal();
	
	
	//2 val ops
	public abstract BasicNumber add(Number n);
	public abstract BasicNumber subtract(Number n);
	public abstract BasicNumber multiply(Number n);
	public abstract BasicNumber divide(Number n);
	public abstract BasicNumber remainder(Number n);
	public BasicNumber mod(Number n)
	{
		BasicNumber b = remainder(n);
		if(b.lessThan(0))
			b.add(n);
		return b;
	}
	public BasicNumber max(Number n)
	{
		return this.greaterThanEquals(n) ? this : this.subtract(this).add(n);
	}
	public BasicNumber min(Number n)
	{
		return this.lessThanEquals(n) ? this : this.subtract(this).add(n);
	}
	//1 val ops
	public BasicNumber invert(){
		return this.subtract(this).add(1).divide(this);
	}
	public BasicNumber negate(){
		return this.multiply(-1);
	}
	public BasicNumber abs(){
		return this.lessThan(0) ? this.negate() : this;
	}
}
