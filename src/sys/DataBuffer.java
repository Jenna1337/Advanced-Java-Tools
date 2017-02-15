package sys;

import java.util.Queue;

public class DataBuffer
{
	private static final byte TRUE = 1;
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
	/*public boolean[] toArray(boolean[] a)
	{
		// TODO Auto-generated method stub
		return a;
	}
	public byte[] toArray(byte[] a)
	{
		// TODO Auto-generated method stub
		return a;
	}*/
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
class Main{
	public static void main(String[] args){
		new DataBuffer().toArray(new Boolean[0]);
	}
}