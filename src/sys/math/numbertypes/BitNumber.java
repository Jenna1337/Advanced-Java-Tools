package sys.math.numbertypes;

public class BitNumber
{
	private boolean[] bits;
	public BitNumber(boolean... bitArray)
	{
		bits = bitArray;
	}
	public BitNumber(String str)
	{
		this(toBinaryArray(str));
	}
	/**
	 * @param str A {@link java.lang.String String} of 1's and 0's
	 * @see #BitNumber(boolean...)
	 * @see #toBinaryArray(String)
	 */
	public static boolean[] toBinaryArray(String str)
	{
		boolean[] bools = new boolean[str.length()];
		for(int i=0; i<str.length(); ++i)
			bools[i] = str.charAt(i)=='1';
		return bools;
	}
	public String toString()
	{
		String out = "";
		for(boolean b : bits)
			out += b?'1':'0';
		return out;
	}
	public byte[] toByteArray()
	{
		byte[] bytes=new byte[bits.length/8+1];
		String bitString = this.toString();
		while(bytes.length*8>bitString.length())
			bitString+='0';
		for(int i=0; i<bytes.length; ++i)
		{
			String con = bitString.substring(8*i, 8+8*i);
			if(con.charAt(0)=='1')
				con = '-'+con.substring(1);
			bytes[i] = Byte.parseByte(con, 2);
		}
		return bytes;
	}
	public String toCharString()
	{
		return new String(this.toByteArray());
	}
}
