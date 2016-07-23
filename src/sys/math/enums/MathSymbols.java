package sys.math.enums;

import java.util.ArrayList;
import java.util.Arrays;

public final class MathSymbols
{
	private MathSymbols(){}
	
	private static MathOperator[] operators = {};
	private static MathConstant[] constants = {};
	private static MathBrackets[] bracketss = {};
	
	static <T> void registerMathSymbol(MathSymbol<T> symbol)
	{
		@SuppressWarnings("rawtypes")
		Class<? extends MathSymbol> K = symbol.getClass();
		if(K.equals(MathOperator.class))
			operators = append(operators, (MathOperator)symbol);
		if(K.equals(MathConstant.class))
			constants = append(constants, (MathConstant)symbol);
		if(K.equals(MathBrackets.class))
			bracketss = append(bracketss, (MathBrackets)symbol);
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
	static <T> T[] append(T[] arr, T val)
	{
		ArrayList<T> lst = new ArrayList<T>(Arrays.asList(arr));
		lst.add(val);
		return lst.toArray(arr);
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
