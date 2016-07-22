package sys.math.interfaces;

import sys.math.numbertypes.SuperNumber;

public interface TrigFunctions
{
	//trigonomics
	/**
	 * The sine function.
	 * @return The sine of the radians represented by this value.
	 */
	public abstract SuperNumber sin();
	/**
	 * The cosine function.
	 * @return The cosine of the radians represented by this value.
	 */
	public abstract SuperNumber cos();
	/**
	 * The tangent function.
	 * @return The tangent of the radians represented by this value.
	 */
	public abstract SuperNumber tan();
	/**
	 * The cosecant function.
	 * @return The cosecant of the radians represented by this value.
	 */
	public abstract SuperNumber csc();
	/**
	 * The secant function.
	 * @return The secant of the radians represented by this value.
	 */
	public abstract SuperNumber sec();
	/**
	 * The cotangent function.
	 * @return The cotangent of the radians represented by this value.
	 */
	public abstract SuperNumber cot();
	
	//hyperbolics
	/**
	 * The hyperbolic sine function.
	 * @return The hyperbolic sine of the radians represented by this value.
	 */
	public abstract SuperNumber sinh();
	/**
	 * The hyperbolic cosine function.
	 * @return The hyperbolic cosine of the radians represented by this value.
	 */
	public abstract SuperNumber cosh();
	/**
	 * The hyperbolic tangent function.
	 * @return The hyperbolic tangent of the radians represented by this value.
	 */
	public abstract SuperNumber tanh();
	/**
	 * The hyperbolic cosecant function.
	 * @return The hyperbolic cosecant of the radians represented by this value.
	 */
	public abstract SuperNumber csch();
	/**
	 * The hyperbolic secant function.
	 * @return The hyperbolic secant of the radians represented by this value.
	 */
	public abstract SuperNumber sech();
	/**
	 * The hyperbolic cotangent function.
	 * @return The hyperbolic cotangent of the radians represented by this value.
	 */
	public abstract SuperNumber coth();
	
	//inverses
	/**
	 * The inverse sine function.
	 * @return The inverse sine of the radians represented by this value.
	 */
	public abstract SuperNumber arcsin();
	/**
	 * The inverse cosine function.
	 * @return The inverse cosine of the radians represented by this value.
	 */
	public abstract SuperNumber arccos();
	/**
	 * The inverse tangent function.
	 * @return The inverse tangent of the radians represented by this value.
	 */
	public abstract SuperNumber arctan();
	/**
	 * The inverse cosecant function.
	 * @return The inverse cosecant of the radians represented by this value.
	 */
	public abstract SuperNumber arccsc();
	/**
	 * The inverse secant function.
	 * @return The inverse secant of the radians represented by this value.
	 */
	public abstract SuperNumber arcsec();
	/**
	 * The inverse cotangent function.
	 * @return The inverse cotangent of the radians represented by this value.
	 */
	public abstract SuperNumber arccot();
	
	//inverse hyperbolics
	/**
	 * The inverse hyperbolic sine function.
	 * @return The inverse hyperbolic sine of the radians represented by this value.
	 */
	public abstract SuperNumber arcsinh();
	/**
	 * The inverse hyperbolic cosine function.
	 * @return The inverse hyperbolic cosine of the radians represented by this value.
	 */
	public abstract SuperNumber arccosh();
	/**
	 * The inverse hyperbolic tangent function.
	 * @return The inverse hyperbolic tangent of the radians represented by this value.
	 */
	public abstract SuperNumber arctanh();
	/**
	 * The inverse hyperbolic cosecant function.
	 * @return The inverse hyperbolic cosecant of the radians represented by this value.
	 */
	public abstract SuperNumber arccsch();
	/**
	 * The inverse hyperbolic secant function.
	 * @return The inverse hyperbolic secant of the radians represented by this value.
	 */
	public abstract SuperNumber arcsech();
	/**
	 * The inverse hyperbolic cotangent function.
	 * @return The inverse hyperbolic cotangent of the radians represented by this value.
	 */
	public abstract SuperNumber arccoth();
	
	//alternate inverses
	/**
	 * The inverse sine function.
	 * @return The inverse sine of the radians represented by this value.
	 * @see #arcsin()
	 */
	public default SuperNumber asin(){return arcsin();}
	/**
	 * The inverse cosine function.
	 * @return The inverse cosine of the radians represented by this value.
	 * @see #arccos()
	 */
	public default SuperNumber acos(){return arccos();}
	/**
	 * The inverse tangent function.
	 * @return The inverse tangent of the radians represented by this value.
	 * @see #arctan()
	 */
	public default SuperNumber atan(){return arctan();}
	/**
	 * The inverse cosecant function.
	 * @return The inverse cosecant of the radians represented by this value.
	 * @see #arccsc()
	 */
	public default SuperNumber acsc(){return arccsc();}
	/**
	 * The inverse secant function.
	 * @return The inverse secant of the radians represented by this value.
	 * @see #arcsec()
	 */
	public default SuperNumber asec(){return arcsec();}
	/**
	 * The inverse cotangent function.
	 * @return The inverse cotangent of the radians represented by this value.
	 * @see #arccot()
	 */
	public default SuperNumber acot(){return arccot();}
	
	//alternate inverse hyperbolics
	/**
	 * The inverse hyperbolic sine function.
	 * @return The inverse hyperbolic sine of the radians represented by this value.
	 * @see #arcsinh()
	 */
	public default SuperNumber asinh(){return arcsinh();}
	/**
	 * The inverse hyperbolic cosine function.
	 * @return The inverse hyperbolic cosine of the radians represented by this value.
	 * @see #arccosh()
	 */
	public default SuperNumber acosh(){return arccosh();}
	/**
	 * The inverse hyperbolic tangent function.
	 * @return The inverse hyperbolic tangent of the radians represented by this value.
	 * @see #arctanh()
	 */
	public default SuperNumber atanh(){return arctanh();}
	/**
	 * The inverse hyperbolic cosecant function.
	 * @return The inverse hyperbolic cosecant of the radians represented by this value.
	 * @see #arccsch()
	 */
	public default SuperNumber acsch(){return arccsch();}
	/**
	 * The inverse hyperbolic secant function.
	 * @return The inverse hyperbolic secant of the radians represented by this value.
	 * @see #arcsech()
	 */
	public default SuperNumber asech(){return arcsech();}
	/**
	 * The inverse hyperbolic cotangent function.
	 * @return The inverse hyperbolic cotangent of the radians represented by this value.
	 * @see #arccoth()
	 */
	public default SuperNumber acoth(){return arccoth();}
}

