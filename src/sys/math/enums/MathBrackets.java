package sys.math.enums;

import sys.math.numbertypes.SuperNumber;

public enum MathBrackets implements MathSymbol<Character>
{
	Parentheses   ('(',     ')'),
	SquareBrackets('[',     ']'),
	CurlyBraces   ('{',     '}'),
	Pipes         ('|',     '|',      "abs"),
	Ceiling       ('\u2308','\u2309', "ceil"),
	Floor         ('\u230A','\u230B', "floor");
	
	private final char lc, rc;
	private final String fn;
	private MathBrackets(char lb, char rb)
	{
		MathSymbols.registerMathSymbol(this);
		lc=lb;
		rc=rb;
		fn=null;
	}
	private MathBrackets(char lb, char rb, String sn)
	{
		MathSymbols.registerMathSymbol(this);
		lc=lb;
		rc=rb;
		fn=sn;
	}
	public Character[] getArgs()
	{
		return new Character[]{lc, rc};
	}
	public static MathBrackets forArgs(Character[] del)
	{
		for(MathBrackets mbr : MathBrackets.values())
			if((mbr.lc == del[0]) && (mbr.rc == del[1]))
				return mbr;
		return null;
	}
	public SuperNumber invokeOn(SuperNumber n1)
	{
		SuperNumber outval=null;
		try
		{
			if(fn!=null)
				outval = (SuperNumber) n1.getClass().getMethod(fn).invoke(n1);
			else
				return n1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return outval;
	}
}
