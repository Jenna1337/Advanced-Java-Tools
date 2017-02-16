package sys;

import java.util.Queue;

public class DataBuffer
{
	private static final byte TRUE = 1, FALSE = 0;
	Queue<Boolean> bits;
	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	public boolean isEmpty()
	{
		return bits.size()==0;
	}
	public boolean getNextBoolean()
	{
		return bits.poll();
	}
	public byte getNextByte()
	{
		byte val = getNextBoolean()?TRUE:~TRUE;
		val <<= 8;
		val |= getNextByte();
		return val;
	}
	public char getNextChar()
	{
		return (char) getNextShort();
	}
	public short getNextShort()
	{
		short val = getNextByte();
		val <<= 8;
		val |= getNextByte();
		return val;
	}
	public int getNextInt()
	{
		int val = getNextShort();
		val <<= 16;
		val |= getNextShort();
		return val;
	}
	public long getNextLong()
	{
		long val = getNextInt();
		val <<= 32;
		val |= getNextInt();
		return val;
	}
	public float getNextFloat()
	{
		return Float.intBitsToFloat(getNextInt());
	}
	public double getNextDouble()
	{
		return Double.longBitsToDouble(getNextLong());
	}
	public <T> T[] toArray(T[] a)
	{
		String t = a.getClass().getName();
		switch(t)
		{
			/*
			Element Type     Encoding 
			boolean          Z
			byte             B
			char             C
			class/interface  Lclassname;  
			double           D
			float            F
			int              I
			long             J
			short            S
*/
			default:
				System.out.println(t);
		}
		return a;
	}
	/*
	Element Type     Encoding 
	boolean          Z
	byte             B
	char             C
	short            S
	int              I
	long             J
	float            F
	double           D
	class/interface  Lclassname;  
	 */
	public boolean[] toArray(boolean[] a)
	{
		// TODO Auto-generated method stub
		return a;
	}
	public byte[] toArray(byte[] a)
	{
		// TODO Auto-generated method stub
		return a;
	}
	public char[] toArray(char[] a)
	{
		// TODO Auto-generated method stub
		return a;
	}
	public short[] toArray(short[] a)
	{
		// TODO Auto-generated method stub
		return a;
	}
	public int[] toArray(int[] a)
	{
		// TODO Auto-generated method stub
		return a;
	}
	public long[] toArray(long[] a)
	{
		// TODO Auto-generated method stub
		return a;
	}
	public float[] toArray(float[] a)
	{
		// TODO Auto-generated method stub
		return a;
	}
	public double[] toArray(double[] a)
	{
		// TODO Auto-generated method stub
		return a;
	}
	public void clear()
	{
		bits.clear();toArray(new Boolean[0]);
	}

	public <T> boolean add(T e)
	{
		// TODO Auto-generated method stub
		return false;
	}
	public <T> T remove()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
