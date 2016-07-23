package sys.math.interfaces;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import sys.math.numbertypes.SuperNumber;

public interface MathFunctions extends Comparable<Number>
{
	//2 val ops
	/**
	 * Returns a SuperNumber whose value is {@code (this + n)}.
	 *
	 * @param  n value to be added to this SuperNumber.
	 * @return {@code this + n}
	 */
	public abstract SuperNumber add(Number n);
	/**
	 * Returns a SuperNumber whose value is {@code (this - n)}.
	 *
	 * @param  n value to be subtracted from this SuperNumber.
	 * @return {@code this - n}
	 */
	public abstract SuperNumber subtract(Number n);
	/**
	 * Returns a SuperNumber whose value is {@code (this * n)}.
	 *
	 * @param  n value to be multiplied by this SuperNumber.
	 * @return {@code this * n}
	 */
	public abstract SuperNumber multiply(Number n);
	/**
	 * Returns a SuperNumber whose value is {@code (this / n)}.
	 *
	 * @param  n value by which this SuperNumber is to be divided.
	 * @return {@code this / n}
	 * @throws ArithmeticException if {@code n} is zero.
	 */
	public abstract SuperNumber divide(Number n);
	/**
	 * Returns a SuperNumber whose value is {@code (this % n)}.
	 *
	 * @param  n value by which this SuperNumber is to be divided, and the
	 *         remainder computed.
	 * @return {@code this % n}
	 * @throws ArithmeticException if {@code n} is zero.
	 */
	public abstract SuperNumber remainder(Number n);
	/**
	 * Returns a SuperNumber whose value is {@code (this mod n}).  This method
	 * differs from {@code remainder} in that it always returns a
	 * <i>non-negative</i> SuperNumber.
	 *
	 * @param  m the modulus.
	 * @return {@code this mod n}
	 * @throws ArithmeticException {@code n} &le; 0
	 * @see    #remainder
	 */
	public abstract SuperNumber mod(Number n);
	/**
	 * Returns a SuperNumber whose value is <code>(this<sup>n</sup>)</code>.
	 *
	 * @param  n exponent to which this SuperNumber is to be raised.
	 * @return <code>this<sup>n</sup></code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public abstract SuperNumber pow(Number n);
	/**
	 * Returns a SuperNumber whose value is <code>(<sup>n</sup>&#x221Athis)</code>.
	 *
	 * @param  n exponent to which this SuperNumber is to be raised.
	 * @return <code><sup>n</sup>&#x221Athis</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public abstract SuperNumber root(Number n);
	//TODO document this function
	public abstract SuperNumber min(Number n);
	//TODO document this function
	public abstract SuperNumber max(Number n);
	//TODO document this function
	public abstract SuperNumber logBase(Number n);
	//1 val ops
	//TODO document this function
	public abstract SuperNumber ln();
	//TODO document this function
	public abstract SuperNumber log10();
	/**
	 * Returns a SuperNumber whose value is <code>(&#x221Athis)</code>.
	 *
	 * @return <code>&#x221Athis</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public default SuperNumber sqrt()//square root
	{
		return root(2);
	}
	/**
	 * Returns a SuperNumber whose value is <code>(&#x221Bthis)</code>.
	 *
	 * @return <code>&#x221Bthis</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public default SuperNumber cbrt()//cube root
	{
		return root(3);
	}
	/**
	 * Returns a SuperNumber whose value is <code>(&#x221Cthis)</code>.
	 *
	 * @return <code>&#x221Cthis</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public default SuperNumber qrrt()//4th root
	{
		return root(4);
	}
	/**
	 * Returns a SuperNumber whose value is <code>&#915(this)</code>.
	 *
	 * @return <code>&#915(this)</code>
	 */
	public abstract SuperNumber gamma();
	/**
	 * Returns a SuperNumber whose value is {@code (1 / this)}.
	 *
	 * @return {@code 1 / this}
	 * @see #divide()
	 */
	public SuperNumber invert();
	/**
	 * Returns a SuperNumber whose value is {@code (this!)}.
	 *
	 * @return {@code this!}
	 * @see #gamma()
	 */
	public abstract SuperNumber factorial();
	/**
	 * Returns a SuperNumber whose value is {@code (-this)}.
	 *
	 * @return {@code -this}
	 */
	public abstract SuperNumber negate();
	/**
	 * Returns a SuperNumber whose value is the absolute value of this
	 * SuperNumber.
	 *
	 * @return {@code abs(this)}
	 */
	public abstract SuperNumber abs();
	/**
	 * Returns the signum function of this SuperNumber.
	 *
	 * @return -1, 0 or 1 as the value of this SuperNumber is negative, zero or
	 *         positive.
	 */
	public abstract int signum();
	//comparing
	//TODO document this function
	public abstract boolean equals(Number n);
	//TODO document this function
	public abstract boolean greaterThan(Number n);
	//TODO document this function
	public abstract boolean lessThan(Number n);
	//TODO document this function
	public abstract boolean greaterThanEquals(Number n);
	//TODO document this function
	public abstract boolean lessThanEquals(Number n);
	//TODO document this function
	public abstract int compareTo(Number n);
	//converting types
	//TODO document this function
	public abstract String toString();
	//TODO document this function
	public abstract byte byteValue();
	//TODO document this function
	public abstract short shortValue();
	//TODO document this function
	public abstract int intValue();
	//TODO document this function
	public abstract long longValue();
	//TODO document this function
	public abstract float floatValue();
	//TODO document this function
	public abstract double doubleValue();
	//TODO document this function
	public abstract boolean booleanValue();
	//TODO document this function
	public abstract char charValue();
	//TODO document this function
	public abstract BigInteger toBigInteger();
	//TODO document this function
	public abstract BigDecimal toBigDecimal();
	//rounding
	//TODO document this function
	public abstract SuperNumber ceil();
	//TODO document this function
	public abstract SuperNumber floor();
	//TODO document this function
	public abstract SuperNumber round();
	//TODO document this function
	public abstract SuperNumber round(MathContext mc);
}
