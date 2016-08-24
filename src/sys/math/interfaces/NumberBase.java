package sys.math.interfaces;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface NumberBase extends Comparable<Number>
{
	//comparing
	/**
	 * Returns true if {@code n} is equal to {@code this}.
	 * @param n the number to compare
	 * @return {@code this==n}
	 */
	public default boolean equals(Number n)
	{
		return this.compareTo(n)==0;
	}
	/**
	 * Returns true if {@code n} is greater than {@code this}.
	 * @param n the number to compare
	 * @return {@code this>n}
	 */
	public default boolean greaterThan(Number n)
	{
		return this.compareTo(n)>0;
	}
	/**
	 * Returns true if {@code n} is less than {@code this}.
	 * @param n the number to compare
	 * @return {@code this<n}
	 */
	public default boolean lessThan(Number n)
	{
		return this.compareTo(n)<0;
	}
	/**
	 * Returns true if {@code n} is greater than or equal to {@code this}.
	 * @param n the number to compare
	 * @return {@code this>=n}
	 */
	public default boolean greaterThanEquals(Number n)
	{
		return this.compareTo(n)>=0;
	}
	/**
	 * Returns true if {@code n} is less than or equal to {@code this}.
	 * @param n the number to compare
	 * @return {@code this<=n}
	 */
	public default boolean lessThanEquals(Number n)
	{
		return this.compareTo(n)<=0;
	}
	/**
	 * Compares {@code this} to another number, {@code n}.
	 * @param n the number to be compared
	 * @return a negative integer, zero, or a positive integer as this number is less than, equal to, or greater than the specified number.
	 */
	public abstract int compareTo(Number n);
	//converting types
	/**
	 * Converts this number to a {@code String} object.
	 * @return the textual representation of this number.
	 */
	public abstract String toString();
	/**
	 * Returns the value of this object as a {@code byte} after a narrowing primitive conversion.
	 * @return the numeric value represented by this object after conversion to type {@code byte}.
	 */
	public abstract byte byteValue();
	/**
	 * Returns the value of this object as a {@code short} after a narrowing primitive conversion.
	 * @return the numeric value represented by this object after conversion to type {@code short}.
	 */
	public abstract short shortValue();
	/**
	 * Returns the value of this object as an {@code int} after a narrowing primitive conversion.
	 * @return the numeric value represented by this object after conversion to type {@code int}.
	 */
	public abstract int intValue();
	/**
	 * Returns the value of this object as a {@code long} after a narrowing primitive conversion.
	 * @return the numeric value represented by this object after conversion to type {@code long}.
	 */
	public abstract long longValue();
	/**
	 * Returns the value of this object as a {@code float} after a narrowing primitive conversion.
	 * @return the numeric value represented by this object after conversion to type {@code float}.
	 */
	public abstract float floatValue();
	/**
	 * Returns the value of this object as a {@code double} after a narrowing primitive conversion.
	 * @return the numeric value represented by this object after conversion to type {@code double}.
	 */
	public abstract double doubleValue();
	/**
	 * Returns the value of this object as a {@code boolean} after a narrowing primitive conversion.
	 * @return the numeric value represented by this object after conversion to type {@code boolean}.
	 */
	public abstract boolean booleanValue();
	/**
	 * Returns the value of this object as a {@code char} after a narrowing primitive conversion.
	 * @return the numeric value represented by this object after conversion to type {@code char}.
	 */
	public abstract char charValue();
	/**
	 * Converts this number to a <code>{@link java.math.BigInteger BigInteger}</code>.
	 * Any fractional part of this number will be discarded.
	 * @return this converted to a <code>{@link java.math.BigInteger BigInteger}</code>.
	 */
	public abstract BigInteger toBigInteger();
	/**
	 * Converts this number to a <code>{@link java.math.BigDecimal BigDecimal}</code>.
	 * @return this converted to a <code>{@link java.math.BigDecimal BigDecimal}</code>.
	 */
	public abstract BigDecimal toBigDecimal();
}
