package sys;

import sys.math.numbertypes.ClassBasedNumber;

@SuppressWarnings("deprecation")
@Deprecated
public class SuperInteger extends ClassBasedNumber
{
	private static final long serialVersionUID = -4260429594548738221L;
	public String sign = "",
			intValue = "0";
	public SuperInteger(Number n)
	{
		String val = n.toString();
		if(val.contains("@"))
			val = n.intValue()+"";
		SuperInteger nv = new SuperInteger(val);
		this.sign = nv.sign;
		this.intValue = nv.intValue;
	}
	public SuperInteger(String n)
	{
		try
		{
			int start =0;
			if((n.charAt(0)+"").equals("-"))
			{
				this.sign = "-";
				start =1;
			}
			if(n.contains("."))
				n = n.split("\\.")[0];//Truncates
			this.intValue = isNumber(n.substring(start, n.length()));
		}
		catch(Exception e)
		{
			throw new NumberFormatException("Invalid Constant \""+n+"\"");
		}
	}
	public void add(Number num)
	{
		SuperInteger n = new SuperInteger(num.toString());
		if(n.intValue.equals("0"))
			return;
		if(n.sign.equals("-"))
		{
			this.subtract(SuperInteger.abs(num));
			return;
		}
		Integer[] ans = toIntArray(this.intValue);
		Integer[] toAdd = toIntArray(n.intValue);
		while(ans.length<toAdd.length)
		{
			ans = makeBigger(ans, 0);
		}
		for(int k=0; k<toAdd.length; ++k)
		{
			ans[k] += toAdd[k];
			for(int g=getIndex(ans, 10); g>=0; g=getIndex(ans, 10))
			{
				String[] temp = String.valueOf(ans[g]/10.0).split("\\.");
				ans[g]=new Integer(temp[1]);
				try{
					ans[g+1]+=new Integer(temp[0]);}
				catch(
						ArrayIndexOutOfBoundsException aioe){
					ans = makeBigger(ans, new Integer(temp[0]));}
			}
		}
		this.intValue = backToString(ans);
	}
	public void subtract(Number num)
	{
		SuperInteger n = new SuperInteger(num.toString());
		if(n.sign.equals("-"))
		{
			this.add(SuperInteger.abs(num));
			return;
		}
		Integer[] A = toIntArray(this.intValue);
		Integer[] B = toIntArray(n.intValue);
		Integer[] C = new Integer[Math.max(A.length, B.length)];
		for(int k=0; k<C.length; ++k)
		{
			if(k>=B.length)
				C[k]=A[k];
			else if(A[k]<B[k])
			{
				for(int j=k+1; j<C.length; ++j)
				{
					if(A[j]==0)
						A[j] = 9;
					else
					{
						A[j]-=1;
						break;
					}
				}
				A[k] += 10;
				C[k]=A[k]-B[k];
			}
			else
				C[k]=A[k]-B[k];
		}
		this.intValue = backToString(C).replace("null","");
	}
	public void multiply(Number num)
	{
		SuperInteger sv = new SuperInteger(this.toString()),
				n2 = new SuperInteger(num.toString());
		if(n2.intValue.equals("0"))
		{
			this.intValue="0";
			return;
		}
		for(SuperInteger i=new SuperInteger("1"); i.lessThan(n2); i.add(1))
			this.add(sv);
	}
	public void divide(Number num)
	{
		this.divmod(num, false);
	}
	public void mod(Number num)
	{
		this.divmod(num, true);
	}
	private void divmod(Number num, boolean remainder)
	{
		if(num.toString().equals("0"))
		{
			if(this.intValue.equals("0")||remainder)
				throw new ArithmeticException("divide by zero");
			else this.complexInfinity();
			return;
		}
		if(num.toString().equals("1"))
			return;
		SuperInteger C=new SuperInteger("0"),
				temp = new SuperInteger("0");
		while(temp.lessThan(this) || temp.equals(this))
		{
			C.add(1);
			temp.add(num);
		}
		C.subtract(1);
		temp.subtract(num);
		if(remainder)
			this.subtract(temp);
		else if(!remainder)
			this.intValue = C.intValue;
	}
	public void pow(Number num)
	{
		if(new SuperInteger(num.toString()).intValue.equals("0"))
		{
			this.intValue="1";
			return;
		}
		SuperInteger orig = new SuperInteger(this.toString());
		for(SuperInteger i=new SuperInteger("1"); i.lessThan(num); i.add(1))
			this.multiply(orig);
	}
	public void factorial()
	{
		if(this.sign.equals("-"))
		{
			this.complexInfinity();
			return;
		}
		SuperInteger newval = new SuperInteger(1);
		for(SuperInteger i=new SuperInteger(this.toString()); i.greaterThan(1); i.subtract(1))
			newval.multiply(i);
		this.intValue = newval.intValue;
	}
	public static SuperInteger factorial(Number num)
	{
		SuperInteger out = new SuperInteger(num);
		out.factorial();
		return out;
	}
	public void abs()
	{
		this.sign = "";
	}
	public static SuperInteger abs(Number num)
	{
		SuperInteger out = new SuperInteger(num);
		out.sign = "";
		return out;
	}
	public String COMPLEX_INFINITY = "\u221E\u0303";
	private void complexInfinity()
	{
		if(this.sign.equals("-"))
			this.sign = "";
		this.intValue = COMPLEX_INFINITY;
	}
	private Integer[] makeBigger(Integer[] in, Integer value)
	{
		Integer[] out=new Integer[in.length+1];
		for(int i=0;i<in.length;++i)
			out[i] = in[i];
		out[in.length] = value;
		return out;
	}
	private int getIndex(Integer[] vals, int check)
	{
		for(int i=0;i<vals.length;++i)
			if(vals[i]>=check)
				return i;
		return -1;
	}
	public boolean greaterThan(Number n)
	{
		String s1=this.toString(), s2=n.toString();
		if(s1.length() > s2.length())
			return true;
		else if(s1.length() == s2.length())
			for(int k=0;k<s1.length();++k)
				if(s1.charAt(k) != s2.charAt(k))
					return Character.getNumericValue(s1.charAt(k)) > Character.getNumericValue(s2.charAt(k));
					return false;
	}
	public boolean lessThan(Number n)
	{
		String s1=this.toString(), s2=n.toString();
		if(s1.length() < s2.length())
			return true;
		else if(s1.length() == s2.length())
			for(int k=0;k<s1.length();++k)
				if(s1.charAt(k) != s2.charAt(k))
					return Character.getNumericValue(s1.charAt(k)) < Character.getNumericValue(s2.charAt(k));
		return false;
	}
	public boolean equals(Number n)
	{
		return this.toString().equals(new SuperInteger(n).toString());
	}
	private String backToString(Integer[] ina)
	{
		String out = "";
		for(int i=ina.length-1; i>=0; --i)
			out+=ina[i];
		out = out.replace("null","0");
		while((out.charAt(0) == '0') && (out.length() > 1))
			out = out.substring(1,out.length());
		//System.out.println("out = '"+out+"'");
		return out;
	}
	private Integer[] toIntArray(String s)
	{
		Integer[] out = new Integer[s.length()];
		int k=0;
		for(int i=s.length()-1; i>=0; --i)
			out[i]=Integer.parseInt(""+s.charAt(k++));
		return out;
	}
	public String toString()
	{
		return this.sign + this.intValue;
	}
	private String isNumber(String a) throws NumberFormatException
	{
		for(int i=0; i<a.length(); ++i)
			Integer.parseInt(a.charAt(i)+"");
		return a;
	}
	public double doubleValue()
	{
		return new Double(this.toString() + ".0");
	}
	public float floatValue()
	{
		return new Float(this.toString() + ".0");
	}
	public long longValue()
	{
		return new Long(this.toString() + ".0");
	}
	public int intValue()
	{
		return new Integer(this.toString());
	}
}
