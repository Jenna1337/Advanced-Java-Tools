package sys.math.enums;

import sys.math.numbertypes.SuperNumber;

public enum MathConstant implements MathSymbol<Object>
{
	//original, replacement, caseSensitive
	COMPLEX_INFINITY ("\u221E\u0303", "\u221E\u0303", false),
	GoldenRatio      ("\u03C6", SuperNumber.GoldenRatio.toString(), true),
	GoldenRatio2     ("\u03D5", SuperNumber.GoldenRatio.toString(), true),
	GoldenRatio3     ("\u0278", SuperNumber.GoldenRatio.toString(), true),
	GoldenRatioInvert("\u03A6", SuperNumber.GoldenRatio.invert().toString(), true),
	Pi               ("Pi", SuperNumber.Pi.toString(), false),
	e                ("e", SuperNumber.E.toString(), true),
	E                ("E", "*10^", true),
	EulerGamma       ("\u0393", SuperNumber.EulerGamma.toString(), true);
	
	
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
