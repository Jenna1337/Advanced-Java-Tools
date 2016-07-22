package sys.math;

import java.util.Arrays;
import java.util.List;
import sys.math.numbertypes.SuperNumber;

public class MathSymbols
{
	/* //TODO add groups
	{'(',')'},
	{'[',']'},
	{'{','}'},
	{'|','|', 'a'}
	*/
	
	
	;
	
	
	
	
	
	private MathOperator[] operators = {};
	private MathConstant[] constants = {};
	private Class<? extends MathSymbol> K;
	private <T> MathSymbols(MathSymbol<T> symbol)
	{
		K = symbol.getClass();
		if(K.equals(MathOperator.class))
			operators = append(operators, (MathOperator)symbol);
		if(K.equals(MathConstant.class))
			constants = append(constants, (MathConstant)symbol);
	}
	public static Character[][][] getGroupVars()
	{
		//TODO
		return null;
	}
	public static Character[][][] getOperatorVars()
	{
		//TODO
		return null;
	}
	public static Object[][] getConstantVars()
	{
		//TODO
		return null;
	}
	
	
	private static <T> T[] append(T[] arr, T val)
	{
		List<T> lst = Arrays.asList(arr);
		lst.add(val);
		return lst.toArray(arr);
	}
}
interface MathSymbol<T>
{
	public abstract T[] getArgs();
}
