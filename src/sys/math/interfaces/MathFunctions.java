package sys.math.interfaces;

import java.math.MathContext;

public interface MathFunctions<T extends Number> extends SimpleMathFunctions<T>
{
	//2 val ops
	/**
	 * Returns a number whose value is <code>(this<sup>n</sup>)</code>.
	 *
	 * @param  n exponent to which this number is to be raised.
	 * @return <code>this<sup>n</sup></code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public abstract T pow(Number n);
	/**
	 * Returns a number whose value is <code>(<sup>n</sup>&#x221Athis)</code>.
	 *
	 * @param  n exponent to which this number is to be raised.
	 * @return <code><sup>n</sup>&#x221Athis</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public abstract T root(Number n);
	/**
	 * Returns a number whose value is <code>log<sub>e</sub>(this)</code>.
	 *
	 * @param  n base to which this number is to be logged.
	 * @return <code>log<sub>n</sub>(this)</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public abstract T logBase(Number n);
	//1 val ops
	/**
	 * Returns a number whose value is <code>(e<sup>this</sup>)</code>.
	 *
	 * @return <code>e<sup>this</sup></code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public abstract T exp();
	/**
	 * Returns a number whose value is <code>log<sub>e</sub>(this)</code>.
	 *
	 * @return <code>log<sub>e</sub>(this)</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public abstract T ln();
	/**
	 * Returns a number whose value is <code>log<sub>10</sub>(this)</code>.
	 *
	 * @return <code>log<sub>10</sub>(this)</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public abstract T log10();
	/**
	 * Returns a number whose value is <code>(&#x221Athis)</code>.
	 *
	 * @return <code>&#x221Athis</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public default T sqrt()//square root
	{
		return root(2);
	}
	/**
	 * Returns a number whose value is <code>(&#x221Bthis)</code>.
	 *
	 * @return <code>&#x221Bthis</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public default T cbrt()//cube root
	{
		return root(3);
	}
	/**
	 * Returns a number whose value is <code>(&#x221Cthis)</code>.
	 *
	 * @return <code>&#x221Cthis</code>
	 * @throws ArithmeticException if the operation yields an imaginary value.
	 */
	public default T qrrt()//4th root
	{
		return root(4);
	}
	/**
	 * Returns a number whose value is <code>&#915(this)</code>.
	 *
	 * @return <code>&#915(this)</code>
	 */
	public abstract T gamma();
	/**
	 * Returns a number whose value is <code>(this!)</code>.
	 *
	 * @return <code>this!</code>
	 * @see #gamma()
	 */
	public abstract T factorial();
	//rounding
	/**
	 * Returns a number whose value is <code>&#x2308;this!&#x2309;</code>.
	 * @return &#x2308;this!&#x2309;
	 * @see #round(MathContext)
	 */
	public abstract T ceil();
	/**
	 * Returns a number whose value is <code>&#x230A;this!&#x230B;</code>.
	 * @return &#x230A;this!&#x230B;
	 * @see #round(MathContext)
	 */
	public abstract T floor();
	/**
	 * Rounds this number to the closest integer
	 * @return {@code this}, rounded to the nearest integer
	 * @see #round(MathContext)
	 */
	public abstract T round();
	/**
	 * Rounds this number according to the specified {@link MathContext}
	 * @param mc the MathContext
	 * @return {@code this}, rounded according to {@code mc}
	 * @see #ceil()
	 * @see #floor()
	 * @see #round()
	 */
	public abstract T round(MathContext mc);
	/**
	 * Returns the fractional part of the number.
	 * @return the fractional part.
	 * @see #ceil()
	 * @see #floor()
	 */
	public default T frac()
	{
		return this.subtract(trunc());
	}
	/**
	 * Truncates the number to the specified decimal place.
	 * @return the number, truncated to the specified decimal place.
	 * @see #frac()
	 */
	public abstract T trunc(Number d);
	/**
	 * Truncates the number.
	 * @return the number, truncated.
	 * @see #trunc(int)
	 * @see #frac()
	 */
	public default T trunc()
	{
		return trunc(0);
	}
}
