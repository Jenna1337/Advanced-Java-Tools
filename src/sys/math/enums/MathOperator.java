package sys.math.enums;

import sys.math.Math11;
import sys.math.numbertypes.SuperNumber;

public enum MathOperator implements MathSymbol<Character>
{
	Exponent      ("pow",       '^'     , 0, 2), 
	SquareRoot    ("sqrt",      '\u221A', 0, 1, 'R'),
	CubeRoot      ("cbrt",      '\u221B', 0, 1, 'R'),
	FourthRoot    ("qrrt",      '\u221C', 0, 1, 'R'),
	NRoot         ("root",      "^(1/"+Math11.NID+")", 0, 2, false),
	Multiplication("multiply",  '*'     , 1, 2),
	Division      ("divide",    '/'     , 1, 2),
	Modulous      ("remainder", '%'     , 1, 2),
	Factorial     ("factorial", '!'     , 1, 1, 'L'),
	Addition      ("add",       '+'     , 2, 2),
	Subtraction   ("subtract",  '-'     , 2, 2),
	GammaFunction ("gamma",     '\u0393', 0, 1, 'R');
	
	private final String fn;
	private final char opc, numargs;
	private final int prio;
	private final char sarg;
	
	private MathOperator(String funcname, char op, int priority, int args)
	{
		registerSymbol();
		fn=funcname;
		opc=op;
		prio=priority;
		numargs=(char)args;
		sarg=0;
	}
	private MathOperator(String funcname, char op, int priority, int args, char sidearg)
	{
		registerSymbol();
		fn=funcname;
		opc=op;
		prio=priority;
		numargs=(char)args;
		sarg=sidearg;
	}
	private MathOperator(String funcname, String op, int priority, int args, boolean sidearg)
	{
		registerSymbol();
		fn=funcname;
		opc=SpecConstant.privuse++;
		new SpecConstant(op, ""+opc, sidearg);
		prio=priority;
		numargs=(char)args;
		sarg=0;
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
