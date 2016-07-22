package sys.math;

import sys.math.numbertypes.SuperNumber;

public enum MathConstant implements MathSymbol<Object>
{
	COMPLEX_INFINITY("\u221E\u0303", "\u221E\u0303", false),
	GoldenRatio     ("", SuperNumber.GoldenRatio.toString(), true),//TODO
	Pi              ("Pi", SuperNumber.Pi.toString(), false),
	e               ("e", SuperNumber.E.toString(), true),
	E               ("E", "*10^", true),
	EulerGamma      ("\u03B3", SuperNumber.EulerGamma.toString(), true);
	
	
	private MathConstant(String original, String replacement, boolean caseSensitive)
	{
		// TODO Auto-generated constructor stub
	}
	public Object[] getArgs()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
