package sys.math.interfaces;

public interface TrigFunctions<T>
{
	//trigonomics
	/**
	 * The sine function.
	 * @return The sine of the radians represented by this value.
	 */
	public abstract T sin();
	/**
	 * The cosine function.
	 * @return The cosine of the radians represented by this value.
	 */
	public abstract T cos();
	/**
	 * The tangent function.
	 * @return The tangent of the radians represented by this value.
	 */
	public abstract T tan();
	/**
	 * The cosecant function.
	 * @return The cosecant of the radians represented by this value.
	 */
	public abstract T csc();
	/**
	 * The secant function.
	 * @return The secant of the radians represented by this value.
	 */
	public abstract T sec();
	/**
	 * The cotangent function.
	 * @return The cotangent of the radians represented by this value.
	 */
	public abstract T cot();
	
	//hyperbolics
	/**
	 * The hyperbolic sine function.
	 * @return The hyperbolic sine of the radians represented by this value.
	 */
	public abstract T sinh();
	/**
	 * The hyperbolic cosine function.
	 * @return The hyperbolic cosine of the radians represented by this value.
	 */
	public abstract T cosh();
	/**
	 * The hyperbolic tangent function.
	 * @return The hyperbolic tangent of the radians represented by this value.
	 */
	public abstract T tanh();
	/**
	 * The hyperbolic cosecant function.
	 * @return The hyperbolic cosecant of the radians represented by this value.
	 */
	public abstract T csch();
	/**
	 * The hyperbolic secant function.
	 * @return The hyperbolic secant of the radians represented by this value.
	 */
	public abstract T sech();
	/**
	 * The hyperbolic cotangent function.
	 * @return The hyperbolic cotangent of the radians represented by this value.
	 */
	public abstract T coth();
	
	//inverses
	/**
	 * The inverse sine function.
	 * @return The inverse sine of the radians represented by this value.
	 */
	public abstract T arcsin();
	/**
	 * The inverse cosine function.
	 * @return The inverse cosine of the radians represented by this value.
	 */
	public abstract T arccos();
	/**
	 * The inverse tangent function.
	 * @return The inverse tangent of the radians represented by this value.
	 */
	public abstract T arctan();
	/**
	 * The inverse cosecant function.
	 * @return The inverse cosecant of the radians represented by this value.
	 */
	public abstract T arccsc();
	/**
	 * The inverse secant function.
	 * @return The inverse secant of the radians represented by this value.
	 */
	public abstract T arcsec();
	/**
	 * The inverse cotangent function.
	 * @return The inverse cotangent of the radians represented by this value.
	 */
	public abstract T arccot();
	
	//inverse hyperbolics
	/**
	 * The inverse hyperbolic sine function.
	 * @return The inverse hyperbolic sine of the radians represented by this value.
	 */
	public abstract T arcsinh();
	/**
	 * The inverse hyperbolic cosine function.
	 * @return The inverse hyperbolic cosine of the radians represented by this value.
	 */
	public abstract T arccosh();
	/**
	 * The inverse hyperbolic tangent function.
	 * @return The inverse hyperbolic tangent of the radians represented by this value.
	 */
	public abstract T arctanh();
	/**
	 * The inverse hyperbolic cosecant function.
	 * @return The inverse hyperbolic cosecant of the radians represented by this value.
	 */
	public abstract T arccsch();
	/**
	 * The inverse hyperbolic secant function.
	 * @return The inverse hyperbolic secant of the radians represented by this value.
	 */
	public abstract T arcsech();
	/**
	 * The inverse hyperbolic cotangent function.
	 * @return The inverse hyperbolic cotangent of the radians represented by this value.
	 */
	public abstract T arccoth();
	
	//alternate inverses
	/**
	 * The inverse sine function.
	 * @return The inverse sine of the radians represented by this value.
	 * @see #arcsin()
	 */
	public default T asin(){return arcsin();}
	/**
	 * The inverse cosine function.
	 * @return The inverse cosine of the radians represented by this value.
	 * @see #arccos()
	 */
	public default T acos(){return arccos();}
	/**
	 * The inverse tangent function.
	 * @return The inverse tangent of the radians represented by this value.
	 * @see #arctan()
	 */
	public default T atan(){return arctan();}
	/**
	 * The inverse cosecant function.
	 * @return The inverse cosecant of the radians represented by this value.
	 * @see #arccsc()
	 */
	public default T acsc(){return arccsc();}
	/**
	 * The inverse secant function.
	 * @return The inverse secant of the radians represented by this value.
	 * @see #arcsec()
	 */
	public default T asec(){return arcsec();}
	/**
	 * The inverse cotangent function.
	 * @return The inverse cotangent of the radians represented by this value.
	 * @see #arccot()
	 */
	public default T acot(){return arccot();}
	
	//alternate inverse hyperbolics
	/**
	 * The inverse hyperbolic sine function.
	 * @return The inverse hyperbolic sine of the radians represented by this value.
	 * @see #arcsinh()
	 */
	public default T asinh(){return arcsinh();}
	/**
	 * The inverse hyperbolic cosine function.
	 * @return The inverse hyperbolic cosine of the radians represented by this value.
	 * @see #arccosh()
	 */
	public default T acosh(){return arccosh();}
	/**
	 * The inverse hyperbolic tangent function.
	 * @return The inverse hyperbolic tangent of the radians represented by this value.
	 * @see #arctanh()
	 */
	public default T atanh(){return arctanh();}
	/**
	 * The inverse hyperbolic cosecant function.
	 * @return The inverse hyperbolic cosecant of the radians represented by this value.
	 * @see #arccsch()
	 */
	public default T acsch(){return arccsch();}
	/**
	 * The inverse hyperbolic secant function.
	 * @return The inverse hyperbolic secant of the radians represented by this value.
	 * @see #arcsech()
	 */
	public default T asech(){return arcsech();}
	/**
	 * The inverse hyperbolic cotangent function.
	 * @return The inverse hyperbolic cotangent of the radians represented by this value.
	 * @see #arccoth()
	 */
	public default T acoth(){return arccoth();}
}

