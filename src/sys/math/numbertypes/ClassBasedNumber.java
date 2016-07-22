package sys.math.numbertypes;

import sys.math.interfaces.MathFunctions;

/**
 * @deprecated Use {@link MathFunctions} instead
 * @author Jonah
 *
 */
public abstract class ClassBasedNumber extends Number
{
	private static final long serialVersionUID = 1L;
	public String sign = "",
			intValue = "0",
			decValue = "0";
	public String COMPLEX_INFINITY = "\u221E\u0303";
	
	public abstract void add(Number num);
	public abstract void subtract(Number num);
	public abstract void multiply(Number num);
	public abstract void divide(Number num);
	public abstract void pow(Number num);
	public abstract void factorial();
	public abstract void abs();
	public abstract boolean greaterThan(Number n);
	public abstract boolean lessThan(Number n);
	public abstract boolean equals(Number n);
	@Override
	public abstract String toString();
	public abstract double doubleValue();
	public abstract float floatValue();
	public abstract long longValue();
	public abstract int intValue();
}
