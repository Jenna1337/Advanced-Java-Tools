package sys.math.enums;

import sys.math.Math11;
import sys.math.numbertypes.SuperNumber;

public enum MathOperator implements MathSymbol<Character>
{
	Exponent      ("pow",       1, 2, '^'), 
	SquareRoot    ("sqrt",      1, 1, '\u221A', 'R'),
	SquareRoot2   ("sqrt",       2, 2, "sqrt", false),
	CubeRoot      ("cbrt",      1, 1, '\u221B', 'R'),
	FourthRoot    ("qrrt",      1, 1, '\u221C', 'R'),
	NRoot         ("root",      1, 2, "^(1/"+Math11.NID+")", false),
	Multiplication("multiply",  2, 2, '*'),
	Division      ("divide",    2, 2, '/'),
	Modulous      ("remainder", 2, 2, '%'),
	Modulous2     ("mod",       2, 2, "mod", false),
	Factorial     ("factorial", 2, 1, '!', 'L'),
	Addition      ("add",       3, 2, '+'),
	Subtraction   ("subtract",  3, 2, '-'),
	GammaFunction ("gamma",     2, 1, '\u0393', 'R'),
	Gamma         ("gamma",     2, 1, "Gamma("+Math11.NID+")", 'R'),
	FractionPart  ("frac",      0, 1, "frac("+Math11.NID+")", 'R'),
	Truncate      ("trunc",     0, 1, "trunc("+Math11.NID+")", 'R'),
	Ceiling       ("ceil",      0, 1, "ceil("+Math11.NID+")", 'R'),
	Floor         ("floor",     0, 1, "floor("+Math11.NID+")", 'R'),
	Signum        ("signum",    0, 1, "signum("+Math11.NID+")", 'R'),
	Signum2       ("signum",    0, 1, "sign("+Math11.NID+")", 'R'),
	Signum3       ("signum",    0, 1, "sgn("+Math11.NID+")", 'R'),
	Sine          ("sin",       0, 1, "sine("+Math11.NID+")", 'R'),
	Cosine        ("cos",       0, 1, "cosine("+Math11.NID+")", 'R'),
	Tangent       ("tan",       0, 1, "tangent("+Math11.NID+")", 'R'),
	Cosecant      ("csc",       0, 1, "cosecant("+Math11.NID+")", 'R'),
	Secant        ("sec",       0, 1, "secant("+Math11.NID+")", 'R'),
	Cotangent     ("cot",       0, 1, "cotangent("+Math11.NID+")", 'R'),
	Sine2         ("sin",       0, 1, "sin("+Math11.NID+")", 'R'),
	Cosine2       ("cos",       0, 1, "cos("+Math11.NID+")", 'R'),
	Tangent2      ("tan",       0, 1, "tan("+Math11.NID+")", 'R'),
	Cosecant2     ("csc",       0, 1, "csc("+Math11.NID+")", 'R'),
	Secant2       ("sec",       0, 1, "sec("+Math11.NID+")", 'R'),
	Cotangent2    ("cot",       0, 1, "cot("+Math11.NID+")", 'R'),
	HSine         ("sinh",      0, 1, "sinh("+Math11.NID+")", 'R'),
	HCosine       ("cosh",      0, 1, "cosh("+Math11.NID+")", 'R'),
	HTangent      ("tanh",      0, 1, "tanh("+Math11.NID+")", 'R'),
	HCosecant     ("csch",      0, 1, "csch("+Math11.NID+")", 'R'),
	HSecant       ("sech",      0, 1, "sech("+Math11.NID+")", 'R'),
	HCotangent    ("coth",      0, 1, "coth("+Math11.NID+")", 'R'),
	ASine         ("asin",      0, 1, "asin("+Math11.NID+")", 'R'),
	ACosine       ("acos",      0, 1, "acos("+Math11.NID+")", 'R'),
	ATangent      ("atan",      0, 1, "atan("+Math11.NID+")", 'R'),
	ACosecant     ("acsc",      0, 1, "acsc("+Math11.NID+")", 'R'),
	ASecant       ("asec",      0, 1, "asec("+Math11.NID+")", 'R'),
	ACotangent    ("acot",      0, 1, "acot("+Math11.NID+")", 'R'),
	AHSine        ("asinh",     0, 1, "asinh("+Math11.NID+")", 'R'),
	AHCosine      ("acosh",     0, 1, "acosh("+Math11.NID+")", 'R'),
	AHTangent     ("atanh",     0, 1, "atanh("+Math11.NID+")", 'R'),
	AHCosecant    ("acsch",     0, 1, "acsch("+Math11.NID+")", 'R'),
	AHSecant      ("asech",     0, 1, "asech("+Math11.NID+")", 'R'),
	AHCotangent   ("acoth",     0, 1, "acoth("+Math11.NID+")", 'R'),
	ArcSine       ("arcsin",    0, 1, "arcsin("+Math11.NID+")", 'R'),
	ArcCosine     ("arccos",    0, 1, "arccos("+Math11.NID+")", 'R'),
	ArcTangent    ("arctan",    0, 1, "arctan("+Math11.NID+")", 'R'),
	ArcCosecant   ("arccsc",    0, 1, "arccsc("+Math11.NID+")", 'R'),
	ArcSecant     ("arcsec",    0, 1, "arcsec("+Math11.NID+")", 'R'),
	ArcCotangent  ("arccot",    0, 1, "arccot("+Math11.NID+")", 'R'),
	ArcHSine      ("arcsinh",   0, 1, "arcsinh("+Math11.NID+")", 'R'),
	ArcHCosine    ("arccosh",   0, 1, "arccosh("+Math11.NID+")", 'R'),
	ArcHTangent   ("arctanh",   0, 1, "arctanh("+Math11.NID+")", 'R'),
	ArcHCosecant  ("arccsch",   0, 1, "arccsch("+Math11.NID+")", 'R'),
	ArcHSecant    ("arcsech",   0, 1, "arcsech("+Math11.NID+")", 'R'),
	ArcHCotangent ("arccoth",   0, 1, "arccoth("+Math11.NID+")", 'R'),
	;
	private final String fn;
	private final char opc, numargs;
	private final int prio;
	private final char sarg;
	
	private MathOperator(String funcname, int priority, int args, char op)
	{
		fn=funcname;
		opc=op;
		prio=priority;
		numargs=(char)args;
		sarg=0;
		registerSymbol();
	}
	private MathOperator(String funcname, int priority, int args, char op, char sidearg)
	{
		fn=funcname;
		opc=op;
		prio=priority;
		numargs=(char)args;
		sarg=sidearg;
		registerSymbol();
	}
	private MathOperator(String funcname, int priority, int args, String op, boolean sidearg)
	{
		fn=funcname;
		opc=SpecConstant.privuse++;
		new SpecConstant(op, ""+opc, sidearg);
		prio=priority;
		numargs=(char)args;
		sarg=0;
		registerSymbol();
	}
	private MathOperator(String funcname, int priority, int args, String op, char sidearg)
	{
		fn=funcname;
		opc=SpecConstant.privuse++;
		new SpecConstant(op, ""+opc, true);
		prio=priority;
		numargs=(char)args;
		sarg=sidearg;
		registerSymbol();
	}
	public Character[] getArgs()
	{
		//{opc, numargs, [sarg]}
		switch(numargs)
		{
			case 2:
				return new Character[]{opc, numargs};
			case 1:
				return new Character[]{opc, numargs, sarg};
		}
		return null;
	}
	public char getOpChar()
	{
		return opc;
	}
	public char getSideArg()
	{
		return sarg;
	}
	public static MathOperator forChar(char charAt)
	{
		for(MathOperator mop : MathOperator.values())
			if(mop.opc == charAt)
				return mop;
		return null;
	}
	public SuperNumber invokeOn(SuperNumber n1, SuperNumber n2)
	{
		SuperNumber outval=null;
		try
		{
			if(numargs == 2)
				outval = (SuperNumber) n1.getClass().getMethod(fn, Number.class).invoke(n1, n2);
			if(numargs == 1)
			{
				switch(sarg)
				{
					case 'L':
						outval = (SuperNumber) n1.getClass().getMethod(fn).invoke(n1);
						break;
					case 'R':
						outval = (SuperNumber) n2.getClass().getMethod(fn).invoke(n2);
						break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new InternalError("Invalid values for "+this.toString()+": "+n1+", "+n2);
		}
		if(!(outval!=null))
		{
			throw new InternalError("Invalid values: "+n1+", "+n2);
		}
		
		return outval;
	}
	public int getPriority()
	{
		return prio;
	}
}
