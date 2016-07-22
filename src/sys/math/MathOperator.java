package sys.math;

public enum MathOperator implements MathSymbol<Character>
{
	Exponent      ("pow",       '^'     , 0, 2), 
	SquareRoot    ("sqrt",      '\u221A', 0, 1, 'R'),
	CubeRoot      ("cbrt",      '\u221B', 0, 1, 'R'),
	FourthRoot    ("qrrt",      '\u221C', 0, 1, 'R'),
	//TODO add nth root - But there is no root character?
	//NRoot         ("root",      '\u0000', 0, 2),
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
		fn=funcname;
		opc=op;
		prio=priority;
		numargs=(char)args;
		sarg=0;
		// TODO Auto-generated constructor stub
	}
	private MathOperator(String funcname, char op, int priority, int args, char sidearg)
	{
		this(funcname, op, priority, args);
		// TODO Auto-generated constructor stub
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
		/*
		 *<pre>
			{
				{
					{pow,2},
					{root2,1,'R'},//square root
					{root3,1,'R'},//cube root
					{root4,1,'R'}//4th root
				},
				{
					{mul,2},
					{div,2},
					{mod,2},
					{fact,1,'L'}
				},
				{
					{add,2},
					{sub,2}
				}
			};
		 </pre>*/
		// TODO Auto-generated method stub
		return null;
	}
	public char getOpChar()
	{
		return opc;
	}
	public static MathOperator forChar(char charAt)
	{
		for(MathOperator mop : MathOperator.values())
			if(mop.opc == charAt)
				return mop;
		return null;
	}
}
