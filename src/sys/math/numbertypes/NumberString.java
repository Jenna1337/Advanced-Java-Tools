package sys.math.numbertypes;

import sys.BitStorage;
import sys.System;

public class NumberString extends BitStorage
{
	public NumberString(boolean[] bitArray)
	{
		super(bitArray);
	}
	public NumberString(Number num)
	{
		super(toBinaryArray(Long.toString(num.longValue(), 2)));
	}
	//@Override
	public static boolean[] toBinaryArray(String str)
	{
		boolean[] bools = new boolean[str.length()];
		for(int i=str.length()-1; i>=0; --i)
			bools[i] = str.charAt(i)=='1';
		return bools;
	}
	public String toBinaryString()
	{
		String out = "";
		for(boolean b : bits)
			out = (b?'1':'0')+out;
		return out;
	}
	@Override
	public String toString()
	{
		return String.valueOf(Long.parseLong(this.toBinaryString(), 2));
	}
	public void add(NumberString numstr)
	{
		this.bits = add(this.bits, numstr.bits);
	}
	public void add(boolean[] a)
	{
		this.bits = add(this.bits, a);
	}
	
	static NumberString add(NumberString a, NumberString b)
	{
		return new NumberString(add(a.bits, b.bits));
	}
	public static boolean [] add(boolean[] a, boolean[] b)
	{
		// pad them to same length
		int l = a.length > b.length ? a.length : b.length;
		a = java.util.Arrays.copyOf(a, l);
		b = java.util.Arrays.copyOf(b, l);

		// prevent overflow, make the results bigger
		boolean [] r = new boolean[a.length + 1];
		boolean t = false;

		for (int i = 0; i < l; i++)
		{
			if (a[i] &&  b[i])
			{
				r[i] = t;
				t = true;
			}
			else if (a[i] ||  b[i])
			{
				r[i] = !t;
			}
			else
			{
				r[i] = t;
				t = false;
			}
		}

		// check overflow
		if (t)
		{
			r[l] = true;
		}

		return r;
	}

	public static void main(String[] args)
	{
		// least significant position is at 0
		NumberString a1 = new NumberString(new boolean[]{true});
		NumberString a2 = new NumberString(new boolean[]{false});
		NumberString a3 = new NumberString(new boolean[]{true, true});
		@SuppressWarnings("unused")
		NumberString a4 = new NumberString(new boolean[]{false, false});
		@SuppressWarnings("unused")
		NumberString a5 = new NumberString(new boolean[]{true, false});
		@SuppressWarnings("unused")
		NumberString a6 = new NumberString(new boolean[]{false, true});
		NumberString a7 = new NumberString(new boolean[]{true, true, true, true});
		NumberString a8 = new NumberString(new boolean[]{false, true, false, true});

		// keep in mind least significant value is at index 0
		System.out.println(a1 + " + " + a1 + " = " + add(a1, a1));
		System.out.println(a1 + " + " + a2 + " = " + add(a1, a2));
		System.out.println(a3 + " + " + a3 + " = " + add(a3, a3));
		System.out.println(a7 + " + " + a8 + " = " + add(a7, a8));
	}
}
