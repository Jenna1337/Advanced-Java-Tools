package sys.math.enums;

import sys.math.Math11;

public class SpecConstant implements MathSymbol<Object>
{
	private static enum SpecConstEnum
	{
		SquareRootSymbol (Math11.NID+"^(1/2)", "\u221A", true),
		CubeRootSymbol   (Math11.NID+"^(1/3)", "\u221B", true),
		FourthRootSymbol (Math11.NID+"^(1/4)", "\u221C", true);
		
		@SuppressWarnings("unused")
		private SpecConstEnum(String original, String replacement, boolean isleft)
		{
			new SpecConstant(original, replacement, isleft);
		}
	}
	static volatile char privuse = '\uE011';
	@SuppressWarnings("unused")
	private static boolean init=init();
	private static final boolean init(){
		//Initialize the values in the class
		SpecConstEnum.values();
		return true;
	}
	
	private final String orig, repl;
	private final boolean sd;
	SpecConstant(String original, String replacement, boolean left)
	{
		orig=original;
		repl=replacement;
		sd = left;
		registerSymbol();
	}
	public Object[] getArgs()
	{
		return new Object[]{orig, repl, sd};
	}
}
