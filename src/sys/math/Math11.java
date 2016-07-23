package sys.math;

import sys.math.enums.MathBrackets;
import sys.math.enums.MathConstant;
import sys.math.enums.MathOperator;
import sys.math.enums.MathSymbols;
import sys.math.numbertypes.SuperNumber;

public class Math11
{
	/**
	 * @see MathBrackets
	 * @see MathSymbols
	 */
	private static final Character[][] groups = MathSymbols.getBracketsVars();
	/**@see #SingleOp(SuperNumber, char, SuperNumber, boolean)*/
	private static final char 
	negative = '`', 
	trueneg = '-';
	/**
	 * @see MathOperator
	 * @see MathSymbols
	 */
	private static final Character[][][] opsWithVars = MathSymbols.getOperatorVars();
	/**
	 * @see MathConstant
	 * @see MathSymbols
	 */
	private static final Object[][] repl = MathSymbols.getConstantVars();
	/**
	 * Evaluates the mathematical expression and returns the result as a String.<br>
	 * @param quest the String to be evaluated
	 * @return The computed value.
	 * @throws ArithmeticException if <code>quest</code> has mismatched delimiters.
	 * @throws NumberFormatException if <code>quest</code> is attempting to perform an operation  on something that is not a number.
	 * @throws IllegalArgumentException if <code>quest</code> is not a valid input String.
	 */
	public static String eval(String quest)
	{
		for(Object[] re : repl)
		{
			String a1 = re[0].toString();
			String a2 = re[1].toString();
			quest = quest.replaceAll(a1, a2);
			if(re[2].equals(Boolean.FALSE))
			{
				quest = quest.replaceAll(a1.toUpperCase(), a2);
				quest = quest.replaceAll(a1.toLowerCase(), a2);
			}
		}
		return evalExt3(quest.replace(" ",""), true).toString().replace(""+negative,""+trueneg);
	}
	private static Object evalExt3(String q, boolean debug) 
	{
		if(debug)
			System.out.println(q);
		try
		{
			return new SuperNumber(q);
		}
		catch(Exception e)
		{
			try
			{
				String query=q.replace(" ","");
				query = abspar(query, debug);
				String toAdd = "";
				if(debug)
					System.out.println(query);
				MathOperator mop = MathOperator.forChar(query.charAt(0));
				if(mop!=null)
				{
					switch(mop)
					{
						case Addition:
						case Subtraction:
							toAdd="0";
						default:
							break;
					}
				}
				query=toAdd+query;
				String n1,n2;
				SuperNumber d1=new SuperNumber(0), d2=new SuperNumber(0);
				String[] vals;
				char op;
				Character[][] ops = getOps();
				int index;
				//Different i than above
				for(int i=0;i<ops.length;++i)
				{
					while(containsAny(query,ops[i]))
					{
						index=getOpsIndex(query, ops[i]);
						op = query.charAt(index);
						Character[] props = getProps(opsWithVars[i], op);
						vals = getNumbers(query, index, props);
						n1=vals[0];
						n2=vals[1];
						int vars=props[1];
						if((vars==2) || ((vars==1)&&(props[2].equals('L'))))
							d1 = new SuperNumber(n1.replace(negative,trueneg));
						if((vars==2) || ((vars==1)&&(props[2].equals('R'))))
							d2 = new SuperNumber(n2.replace(negative,trueneg));
						
						if(debug)
							System.out.println(query+" "+(n1+op+n2).replace("null","")+" "+SingleOp(d1,op,d2, debug));
						
						query = query.replace((""+n1+op+n2).replace("null",""), ""+SingleOp(d1,op,d2, debug));
					}
				}
				return query;
			}
			catch(IndexOutOfBoundsException e2)
			{
				throw new IllegalArgumentException("Invalid input String: \""+q+"\"");
			}
		}
	}
	private static String abspar(String query, boolean debug)
	{
		while(containsAny2(query, groups))
		{
			Character[] del = getFirst(query, groups, debug);
			query = parenth(query, del, debug);
			if(debug)
				System.out.println(query);
		}
		return query;
	}
	private static Character[] getFirst(String query, Character[][] groups, boolean debug)
	{
		
		int k = Integer.MAX_VALUE;
		int[][] loc = new int[groups.length][2];
		for(int i=0; i<loc.length; ++i)
		{
			for(int j=0; j<2; ++j)
			{
				loc[i][j] = query.indexOf(groups[i][j]);
				if(loc[i][j]>=0 && loc[i][j]<k)
					k=i;
			}
			if(loc[i][0]>loc[i][1])
			{
				throw new ArithmeticException("Mismatched dilimiters for String \""+query+"\"");
			}
		}
		if(debug)
			System.out.println(k);
		return groups[k];
	}
	private static Character[] getProps(Character[][] varList, char op)
	{
		for(Character[] i : varList)
			if(i[0].equals(op))
				return i;
		throw new InternalError("Operator not found: "+op);
	}
	private static Character[][] getOps()
	{
		Character[][] ops = new Character[opsWithVars.length][];
		for(int i=0; i<opsWithVars.length; ++i)
		{
			int rowLength = opsWithVars[i].length;
			ops[i] = new Character[rowLength];
			for(int i2 = 0; i2<rowLength; ++i2)
				ops[i][i2] = opsWithVars[i][i2][0];
		}
		return ops;
	}
	private static String parenth(String query, Character[] del, boolean debug)
	{
		char cL = del[0], cR = del[1];
		if(debug)
			System.out.println(query+"$ "+cL+" ... "+cR);
		String sL=""+cL, sR=""+cR;
		if((count(query,cL)!=count(query,cR)) || (query.indexOf(sL)>query.indexOf(sR)) || (query.lastIndexOf(sL)>query.lastIndexOf(sR)))
		{
			throw new ArithmeticException("Mismatched dilimiters for String \""+query+"\"");
		}
		String pip;
		int left, right;
		while(query.contains(sL)||query.contains(sR))
		{
			pip=""; left=0; right=0;
			for(int i=0;i<query.length();++i)
			{
				if(query.charAt(i)==cL)
				{
					left=i;
					break;
				}
			}
			int parsleft=1;
			for(int i=left+1;i<query.length();++i)
			{
				if(query.charAt(i)==cR)
					parsleft-=1;
				else if(query.charAt(i)==cL)
					parsleft+=1;
				if(parsleft==0)
				{
					right=i;
					break;
				}
			}
			for(int k=left+1;k<right;++k)
				pip+=query.charAt(k);
			if(debug)
				System.out.println(query+" "+pip);
			String rep = evalExt3(pip,debug).toString();
			MathBrackets mbrkts = MathBrackets.forArgs(del);
			if(mbrkts!=null)
				rep = mbrkts.invokeOn(new SuperNumber(query)).toString();
			if(debug)
				System.out.println(rep);
			query = query.replace(sL+pip+sR, rep);
		}
		return query;
	}
	private static int getOpsIndex(String query, Character[] ops)
	{
		for(int i=0;i<query.length();++i)
			if(equalsAny(query.charAt(i), ops))
				return i;
		throw new InternalError("No valid operator was found in \""+query+"\"");
	}
	private static String SingleOp(SuperNumber n1, char op, SuperNumber n2, boolean debug)
	{
		if(debug)
			System.out.println("\nn1="+n1+", op="+op+", n2="+n2);
		
		MathOperator mop = MathOperator.forChar(op);
		
		if(mop!=null)
			return mop.invokeOn(n1, n2).toString().replace(trueneg,negative);
		else
			throw new InternalError("Invalid operator: "+op+"");
	}
	private static String[] getNumbers(String context, int index, Character[] props)
	{
		String fn=null, sn=null;
		Character c='0';
		Character[] nums={'.','0','1','2','3','4','5','6','7','8','9',trueneg};
		int vars = props[1];
		if((vars==2) || ((vars==1)&&(props[2].equals('L'))))
		{
			fn=context.charAt(index-1)+"";
			for(int i=index-2;i>=0;--i)
			{
				c=context.charAt(i);
				if(equalsAny(c,nums))
					fn=c+fn;
				else break;
			}
		}
		if((vars==2) || ((vars==1)&&(props[2].equals('R'))))
		{
			sn=context.charAt(index+1)+"";
			c='0';
			for(int i=index+2;i<context.length();++i)
			{
				c=context.charAt(i);
				if(equalsAny(c,nums))
					sn=sn+c;
				else break;
			}
		}
		String[] array = {fn,sn};
		return array;
	}
	private static boolean equalsAny(Character a, Character[] b)
	{
		for(Object i : b)
			if(a.equals(i))
				return true;
		return false;
	}
	private static boolean containsAny(String a, Character[] b)
	{
		for(Character i : b)
			if(a.toString().contains(i.toString()))
				return true;
		return false;
	}
	private static boolean containsAny2(String a, Character[][] c)
	{
		for(Character[] b : c)
			containsAny(a, b);
		return false;
	}
	private static int count(String text, Character toFind)
	{
		int found = 0;
		for(char c : text.toCharArray())
			if(c==toFind)
				found+=1;
		return found;
	}
}