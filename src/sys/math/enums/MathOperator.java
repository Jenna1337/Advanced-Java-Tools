package sys.math.enums;

import sys.math.MathEval;
import sys.math.numbertypes.SuperNumber;

public enum MathOperator implements MathSymbol<Character>
{
	Exponent      ("pow",       1, 2, '^'), 
	SquareRoot    ("sqrt",      1, 1, '\u221A', 'R'),
	SquareRoot2   ("sqrt",       2, 2, "sqrt", false),
	CubeRoot      ("cbrt",      1, 1, '\u221B', 'R'),
	FourthRoot    ("qrrt",      1, 1, '\u221C', 'R'),
	NRoot         ("root",      1, 2, "^(1/"+MathEval.NID+")", false),
	Multiplication("multiply",  2, 2, '*'),
	Division      ("divide",    2, 2, '/'),
	Modulous      ("remainder", 2, 2, '%'),
	Modulous2     ("mod",       2, 2, "mod", false),
	Factorial     ("factorial", 2, 1, '!', 'L'),
	Addition      ("add",       3, 2, '+'),
	Subtraction   ("subtract",  3, 2, '-'),
	GammaFunction ("gamma",     2, 1, '\u0393', 'R'),
	Gamma         ("gamma",     2, 1, "Gamma("+MathEval.NID+")", 'R'),
	FractionPart  ("frac",      0, 1, "frac("+MathEval.NID+")", 'R'),
	Truncate      ("trunc",     0, 1, "trunc("+MathEval.NID+")", 'R'),
	Ceiling       ("ceil",      0, 1, "ceil("+MathEval.NID+")", 'R'),
	Floor         ("floor",     0, 1, "floor("+MathEval.NID+")", 'R'),
	Signum        ("signum",    0, 1, "signum("+MathEval.NID+")", 'R'),
	Signum2       ("signum",    0, 1, "sign("+MathEval.NID+")", 'R'),
	Signum3       ("signum",    0, 1, "sgn("+MathEval.NID+")", 'R'),
	Sine          ("sin",       0, 1, "sine("+MathEval.NID+")", 'R'),
	Cosine        ("cos",       0, 1, "cosine("+MathEval.NID+")", 'R'),
	Tangent       ("tan",       0, 1, "tangent("+MathEval.NID+")", 'R'),
	Cosecant      ("csc",       0, 1, "cosecant("+MathEval.NID+")", 'R'),
	Secant        ("sec",       0, 1, "secant("+MathEval.NID+")", 'R'),
	Cotangent     ("cot",       0, 1, "cotangent("+MathEval.NID+")", 'R'),
	Sine2         ("sin",       0, 1, "sin("+MathEval.NID+")", 'R'),
	Cosine2       ("cos",       0, 1, "cos("+MathEval.NID+")", 'R'),
	Tangent2      ("tan",       0, 1, "tan("+MathEval.NID+")", 'R'),
	Cosecant2     ("csc",       0, 1, "csc("+MathEval.NID+")", 'R'),
	Secant2       ("sec",       0, 1, "sec("+MathEval.NID+")", 'R'),
	Cotangent2    ("cot",       0, 1, "cot("+MathEval.NID+")", 'R'),
	HSine         ("sinh",      0, 1, "sinh("+MathEval.NID+")", 'R'),
	HCosine       ("cosh",      0, 1, "cosh("+MathEval.NID+")", 'R'),
	HTangent      ("tanh",      0, 1, "tanh("+MathEval.NID+")", 'R'),
	HCosecant     ("csch",      0, 1, "csch("+MathEval.NID+")", 'R'),
	HSecant       ("sech",      0, 1, "sech("+MathEval.NID+")", 'R'),
	HCotangent    ("coth",      0, 1, "coth("+MathEval.NID+")", 'R'),
	ASine         ("asin",      0, 1, "asin("+MathEval.NID+")", 'R'),
	ACosine       ("acos",      0, 1, "acos("+MathEval.NID+")", 'R'),
	ATangent      ("atan",      0, 1, "atan("+MathEval.NID+")", 'R'),
	ACosecant     ("acsc",      0, 1, "acsc("+MathEval.NID+")", 'R'),
	ASecant       ("asec",      0, 1, "asec("+MathEval.NID+")", 'R'),
	ACotangent    ("acot",      0, 1, "acot("+MathEval.NID+")", 'R'),
	AHSine        ("asinh",     0, 1, "asinh("+MathEval.NID+")", 'R'),
	AHCosine      ("acosh",     0, 1, "acosh("+MathEval.NID+")", 'R'),
	AHTangent     ("atanh",     0, 1, "atanh("+MathEval.NID+")", 'R'),
	AHCosecant    ("acsch",     0, 1, "acsch("+MathEval.NID+")", 'R'),
	AHSecant      ("asech",     0, 1, "asech("+MathEval.NID+")", 'R'),
	AHCotangent   ("acoth",     0, 1, "acoth("+MathEval.NID+")", 'R'),
	ArcSine       ("arcsin",    0, 1, "arcsin("+MathEval.NID+")", 'R'),
	ArcCosine     ("arccos",    0, 1, "arccos("+MathEval.NID+")", 'R'),
	ArcTangent    ("arctan",    0, 1, "arctan("+MathEval.NID+")", 'R'),
	ArcCosecant   ("arccsc",    0, 1, "arccsc("+MathEval.NID+")", 'R'),
	ArcSecant     ("arcsec",    0, 1, "arcsec("+MathEval.NID+")", 'R'),
	ArcCotangent  ("arccot",    0, 1, "arccot("+MathEval.NID+")", 'R'),
	ArcHSine      ("arcsinh",   0, 1, "arcsinh("+MathEval.NID+")", 'R'),
	ArcHCosine    ("arccosh",   0, 1, "arccosh("+MathEval.NID+")", 'R'),
	ArcHTangent   ("arctanh",   0, 1, "arctanh("+MathEval.NID+")", 'R'),
	ArcHCosecant  ("arccsch",   0, 1, "arccsch("+MathEval.NID+")", 'R'),
	ArcHSecant    ("arcsech",   0, 1, "arcsech("+MathEval.NID+")", 'R'),
	ArcHCotangent ("arccoth",   0, 1, "arccoth("+MathEval.NID+")", 'R'),
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
		new SpecConstant(op, ""+opc, sidearg).registerSymbol();
		prio=priority;
		numargs=(char)args;
		sarg=0;
		registerSymbol();
	}
	private MathOperator(String funcname, int priority, int args, String op, char sidearg)
	{
		fn=funcname;
		opc=SpecConstant.privuse++;
		new SpecConstant(op, ""+opc, true).registerSymbol();
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
			default:
				throw new InternalError(numargs+" is not a valid number of arguments!");
		}
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
					default:
						throw new IllegalArgumentException("\'"+sarg+"\' is not a valid side argument!");
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
