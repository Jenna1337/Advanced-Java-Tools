package sys;

import java.nio.ByteOrder;
import java.util.Queue;

public class DataBuffer
{
	// TODO endianness
	private static final byte TRUE = 1, FALSE = 0;
	private final ByteOrder order;
	private Queue<Boolean> bits;
	
	public DataBuffer()
	{
		order = ByteOrder.nativeOrder();
	}
	public DataBuffer(ByteOrder order)
	{
		this.order = order;
	}
	
	public int size()
	{
		return bits.size();
	}
	public boolean isEmpty()
	{
		return bits.size()==0;
	}
	public void clear()
	{
		bits.clear();
	}
	
	private long getBits(int length)
	{
		if(length>size())
		{
			//TODO
		}
		long val = 0b0000000000000000000000000000000000000000000000000000000000000000L;
		//TODO -- Make sure to include endianness!!!
		for(int i=0;i<length;++i)
		{
			val |= getBit();
		}
		return val;
	}
	private void putBits(long val, int length)
	{
		//TODO -- Make sure to include endianness!!!
		long mask = 1;
		for(int i=0;i<length;++i)
		{
			//TODO
		}
		putBoolean((val & mask) ==TRUE);
	}
	
	/**
	 * Gets the next bit and returns it as a boolean. 
	 * @return The truth value of the bit, {@code true} for 1 and {@code false} for 0.
	 * @see #getBit()
	 */
	public boolean getBoolean()
	{
		return bits.poll();
	}
	/**
	 * Gets the next bit and returns it as a number. 
	 * @return The value of the bit, either 0 or 1.
	 * @see #getBoolean()
	 */
	public byte getBit()
	{
		return getBoolean()?TRUE:FALSE;
	}
	public byte getByte()
	{
		return (byte)getBits(Byte.SIZE);
	}
	public char getChar()
	{
		return (char)getBits(Character.SIZE);
	}
	public short getShort()
	{
		return (short)getBits(Short.SIZE);
	}
	public int getInt()
	{
		return (int)getBits(Integer.SIZE);
	}
	public long getLong()
	{
		return (long)getBits(Long.SIZE);
	}
	public float getFloat()
	{
		return Float.intBitsToFloat(getInt());
	}
	public double getDouble()
	{
		return Double.longBitsToDouble(getLong());
	}
	public void putBoolean(boolean val)
	{
		bits.add(val);
	}
	public void putByte(byte val)
	{
		putBits(val, Byte.SIZE);
	}
	public void putChar(char val)
	{
		putBits(val, Character.SIZE);
	}
	public void putShort(short val)
	{
		putBits(val, Short.SIZE);
	}
	public void putInt(int val)
	{
		putBits(val, Integer.SIZE);
	}
	public void putLong(long val)
	{
		putBits(val, Long.SIZE);
	}
	public void putFloat(float val)
	{
		putInt(Float.floatToRawIntBits(val));
	}
	public void putDouble(double val)
	{
		putLong(Double.doubleToRawLongBits(val));
	}
}
