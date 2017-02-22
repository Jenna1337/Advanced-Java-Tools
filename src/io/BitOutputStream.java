package io;

import java.io.DataOutput;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sys.DataBuffer;

public class BitOutputStream extends FilterOutputStream implements DataOutput
{
	DataBuffer buffer;
	
	public BitOutputStream(OutputStream out)
	{
		super(out);
		buffer = new DataBuffer();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void write(int b) throws IOException
	{
		buffer.putByte((byte) b);
	}
	@Override
	public void flush() throws IOException
	{
		int numbytes = (int) Math.round(Math.ceil(buffer.size()/8.0));
		byte[] bytes = new byte[numbytes];
		out.write(bytes);
		super.flush();
	}
	@Override
	public void close() throws IOException
	{
		this.flush();
		super.close();
	}
	
	public void writeBoolean(boolean v) throws IOException
	{
		buffer.putBoolean(v);
	}
	
	public void writeByte(int v) throws IOException
	{
		buffer.putByte((byte) v);
	}
	
	public void writeShort(int v) throws IOException
	{
		buffer.putShort((short) v);
	}
	
	public void writeChar(int v) throws IOException
	{
		buffer.putChar((char) v);
	}
	
	public void writeInt(int v) throws IOException
	{
		buffer.putInt(v);
	}
	
	public void writeLong(long v) throws IOException
	{
		buffer.putLong(v);
	}
	
	public void writeFloat(float v) throws IOException
	{
		buffer.putFloat(v);
	}
	
	public void writeDouble(double v) throws IOException
	{
		buffer.putDouble(v);
	}
	
	public void writeBytes(String s) throws IOException
	{
		byte[] bs = s.getBytes();
		for(byte b : bs)
			writeByte(b);
	}
	
	public void writeChars(String s) throws IOException
	{
		char[] cs = s.toCharArray();
		for(char c : cs)
			writeChar(c);
	}
	
	public void writeUTF(String s) throws IOException
	{
		// TODO Auto-generated method stub
		
	}
}
