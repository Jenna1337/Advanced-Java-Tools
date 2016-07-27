package sys.math.enums;

import utils.ArrayUtils;
import java.util.ArrayList;

public final class MathSymbols
{
	private static MathOperator[] operators = new MathOperator[0];
	private static MathConstant[] constants = new MathConstant[0];
	private static MathBrackets[] bracketss = new MathBrackets[0];
	private static SpecConstant[] specconst = new SpecConstant[0];
	
	@SuppressWarnings("unused")
	private static final MathSymbols msymbs = new MathSymbols();
	private MathSymbols()
	{
		//Initialize the values in the classes
		MathOperator.values();
		MathConstant.values();
		MathBrackets.values();
	}
	
	static <T> void registerMathSymbol(MathSymbol<T> symbol)
	{
		@SuppressWarnings("rawtypes")
		Class<? extends MathSymbol> K = symbol.getClass();
		if(K.equals(SpecConstant.class))
			specconst = ArrayUtils.append(specconst, (SpecConstant)symbol);
		if(K.equals(MathOperator.class))
			operators = ArrayUtils.append(operators, (MathOperator)symbol);
		if(K.equals(MathConstant.class))
			constants = ArrayUtils.append(constants, (MathConstant)symbol);
		if(K.equals(MathBrackets.class))
			bracketss = ArrayUtils.append(bracketss, (MathBrackets)symbol);
	}
	public static Character[][] getBracketsVars()
	{
		MathBrackets[] vals = bracketss;
		Character[][] objs = new Character[vals.length][3];
		for(int i=0; i<vals.length; ++i)
			objs[i] = vals[i].getArgs();
		return objs;
	}
	public static Character[][][] getOperatorVars()
	{
		ArrayList<ArrayList<Character[]>> arrs = new ArrayList<ArrayList<Character[]>>();
		for(MathOperator curop : operators)
		{
			int pr = curop.getPriority();
			while(pr >= arrs.size())
				arrs.add(new ArrayList<Character[]>());
			arrs.get(pr).add(curop.getArgs());
		}
		Character[][][] outvals = new Character[arrs.size()][][];
		for(int i=0; i<outvals.length; ++i)
			outvals[i]=arrs.get(i).toArray(new Character[0][]);
		return outvals;
	}
	public static Object[][] getConstantVars()
	{
		MathConstant[] vals = constants;
		Object[][] objs = new Object[vals.length][3];
		for(int i=0; i<vals.length; ++i)
			objs[i] = vals[i].getArgs();
		return objs;
	}
	public static Object[][] getSpecialCVars()
	{
		SpecConstant[] spec = specconst;
		Object[][] objs = new Object[spec.length][3];
		for(int i=0; i<spec.length; ++i)
			objs[i] = spec[i].getArgs();
		return objs;
	}
}
interface MathSymbol<T>
{
	public default void registerSymbol()
	{
		MathSymbols.registerMathSymbol(this);
	}
	public abstract T[] getArgs();
}
