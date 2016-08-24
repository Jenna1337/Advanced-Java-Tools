package sys.math.interfaces;

public interface SimpleMathFunctions<T extends Number> extends NumberBase
{
	//2 val ops
	/**
	 * Returns a number whose value is {@code (this + n)}.
	 *
	 * @param  n value to be added to this number.
	 * @return {@code this + n}
	 */
	public abstract T add(Number n);
	/**
	 * Returns a number whose value is {@code (this - n)}.
	 *
	 * @param  n value to be subtracted from this number.
	 * @return {@code this - n}
	 */
	public abstract T subtract(Number n);
	/**
	 * Returns a number whose value is {@code (this * n)}.
	 *
	 * @param  n value to be multiplied by this number.
	 * @return {@code this * n}
	 */
	public abstract T multiply(Number n);
	/**
	 * Returns a number whose value is {@code (this / n)}.
	 *
	 * @param  n value by which this number is to be divided.
	 * @return {@code this / n}
	 * @throws ArithmeticException if {@code n} is zero.
	 */
	public abstract T divide(Number n);
	/**
	 * Returns a number whose value is {@code (this % n)}.
	 *
	 * @param  n value by which this number is to be divided, and the
	 *         remainder computed.
	 * @return {@code this % n}
	 * @throws ArithmeticException if {@code n} is zero.
	 */
	public abstract T remainder(Number n);
	/**
	 * Returns a number whose value is {@code (this mod n}).  This method
	 * differs from {@code remainder} in that it always returns a
	 * <i>non-negative</i> number.
	 *
	 * @param  n the modulus.
	 * @return {@code this mod n}
	 * @throws ArithmeticException {@code n} &le; 0
	 * @see    #remainder
	 */
	public abstract T mod(Number n);
	/**
	 * Compares this number to another number and returns the smaller one. 
	 * @param n the number to be compared
	 * @return The number with the lower value.
	 */
	public abstract T min(Number n);
	/**
	 * Compares this number to another number and returns the bigger one. 
	 * @param n the number to be compared
	 * @return The number with the higher value.
	 */
	public abstract T max(Number n);
	//1 val ops
	/**
	 * Returns a number whose value is {@code (1 / this)}.
	 *
	 * @return {@code 1 / this}
	 * @see #divide()
	 */
	public T invert();
	/**
	 * Returns a number whose value is {@code (-this)}.
	 *
	 * @return {@code -this}
	 */
	public abstract T negate();
	/**
	 * Returns a number whose value is the absolute value of this
	 * number.
	 *
	 * @return {@code |this|}
	 */
	public abstract T abs();
	/**
	 * Returns the signum function of this number.
	 *
	 * @return -1, 0 or 1 if the value of this number is negative, zero or positive.
	 * @see #compareTo(Number)
	 */
	public abstract int signum();
}
