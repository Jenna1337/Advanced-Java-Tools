package sys;


public class BitStorage
{
	public boolean[] bits;
	public BitStorage(boolean... bitArray)
	{
		bits = bitArray;
	}
	public BitStorage(String bools)
	{
		this(toBinaryArray(bools));
	}
	public static boolean[] toBinaryArray(String str)
	{
		boolean[] bools = new boolean[str.length()];
		for(int i=0; i<str.length(); ++i)
			bools[i] = str.charAt(i)=='1';
		return bools;
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
	public char[] toCharArray()
	{
		return this.toCharString().toCharArray();
		/*char[] chars=new char[bits.length/16+1];
		String bitString = this.toString();
		while(chars.length*16>bitString.length())
			bitString+='0';
		for(int i=0; i<chars.length; ++i)
		{
			String con = bitString.substring(16*i, 16+16*i);
			if(con.charAt(0)=='1')
				con = '-'+con.substring(1);
			chars[i] = (char)Integer.parseInt(con, 2);
		}
		return chars;*/
	}
	public String toString()
	{
		String out = "";
		for(boolean b : bits)
			out += b?'1':'0';
		return out;
	}
	public static String toCharString(BitStorage... bitStorages)
	{
		String str="";
		for(BitStorage bs : bitStorages)
			str+=bs.toCharString();
		return str;
	}
}
class SysMain
{
	public static String changebase(Object svar, int frombase, int tobase)
	{
		return Long.toString(Integer.parseInt(svar + "", frombase), tobase).toUpperCase();
	}
}