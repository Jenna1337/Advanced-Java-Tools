package sys;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

// TODO test this class
public class DataBuffer
{
	private static final byte TRUE = 1, FALSE = 0;
	private final BitOrder order;
	private Queue<Boolean> bits;
	enum BitOrder{
		/**
		 * Least Significant Bit
		 */
		LSB,
		/**
		 * Most Significant Bit
		 */
		MSB;
	}
	
	public DataBuffer()
	{
		this(BitOrder.LSB);
	}
	public DataBuffer(BitOrder order)
	{
		this.order = order;
		bits = new ConcurrentLinkedQueue<Boolean>();
	}
	
	public int size()
	{
		return bits.size();
	}
	public int sizeBytes()
	{
		return (int) Math.round(Math.ceil(size()/8.0));
	}
	public boolean isEmpty()
	{
		return bits.size()==0;
	}
	public void clear()
	{
		bits.clear();
	}
	
	private long peekBits(int length)
	{
		if(length>size())
		{
			throw new NullPointerException("");
		}
		long val = 0b0000000000000000000000000000000000000000000000000000000000000000L;
		java.util.Iterator<Boolean> iter = bits.iterator();
		switch(order){
			case LSB:
				for(int i=0;i<length;++i)
				{
					val |= B2B(iter.next());
					val <<= 1;
				}
				break;
			case MSB:
				for(int i=0;i<length;++i)
				{
					val |= ((long)B2B(iter.next()))<<i;
				}
				break;
			default:
				throw new InternalError();
		}
		return val;
	}
	private long getBits(int length)
	{
		if(length>size())
		{
			while(size()<length)
				putBit(FALSE);
		}
		long val = 0b0000000000000000000000000000000000000000000000000000000000000000L;
		switch(order){
			case LSB:
				for(int i=0;i<length;++i)
				{
					val |= getBit();
					val <<= 1;
				}
				break;
			case MSB:
				for(int i=0;i<length;++i)
				{
					val |= ((long)getBit())<<i;
				}
				break;
			default:
				throw new InternalError();
		}
		return val;
	}
	private void putBits(long val, int length)
	{
		//FIXME
		switch(order){
			case LSB:
				int startbit = Long.SIZE-length-1;
				for(int i=0;i<length;++i)
				{
					putBit((byte) ((val>>>(startbit+i))&1));
				}
				break;
			case MSB:
				for(int i=0;i<length;++i)
				{
					putBit((byte) ((val>>>(Long.SIZE-i-1))&1));
				}
				break;
			default:
				throw new InternalError();
		}
	}
	/**
	 * Converts a boolean to a byte.
	 * @return The value of the boolean, either 0 or 1.
	 */
	private static byte B2B(boolean b){
		return b?TRUE:FALSE;
	}
	/**
	 * 
	 * @return
	 */
	public boolean peekBoolean()
	{
		return bits.peek();
	}
	public byte peekBit()
	{
		return B2B(peekBoolean());
	}
	public byte peekByte()
	{
		return (byte)peekBits(Byte.SIZE);
	}
	public char peekChar()
	{
		return (char)peekBits(Character.SIZE);
	}
	public short peekShort()
	{
		return (short)peekBits(Short.SIZE);
	}
	public int peekInt()
	{
		return (int)peekBits(Integer.SIZE);
	}
	public long peekLong()
	{
		return peekBits(Long.SIZE);
	}
	public float peekFloat()
	{
		return Float.intBitsToFloat(peekInt());
	}
	public double peekDouble()
	{
		return Double.longBitsToDouble(peekLong());
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
		return B2B(getBoolean());
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
	public void putBit(Byte val)
	{
	putBoolean(val==TRUE);
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
