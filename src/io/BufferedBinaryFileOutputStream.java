package io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.BufferUnderflowException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BitOutputStream extends FilterOutputStream
{
	public BitOutputStream(OutputStream out)
	{
		super(out);
		// TODO Auto-generated constructor stub
	}
	
	ConcurrentLinkedQueue<Boolean> buffer;
	
	private byte toByte(boolean b){
		return (byte) (b?1:0);
	}
	private byte getNextByte()
	{
		if(buffer.size()<8)
			throw new IOException("Buffer size too small.");
		boolean[] bits = new boolean[8];
		for(int i=0;i<8;++i)
			bits[i]=buffer.poll();
		return getByte(bits);
	}
	private byte getByte(boolean[] bits)
	{
		if(bits.length!=8)
			throw new IllegalArgumentException("Invalid number of bits ("+bits.length+"), expected 8");
		byte b = bits[0];
		for(int i=1;i<8;++i)
			b = (byte) ((b<<1) & toByte(bits[i]));
	}
	@Override
	public void write(int b) throws IOException
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void flush() throws IOException
	{
		// TODO Auto-generated method stub
		super.flush();
	}
	@Override
	public void close() throws IOException
	{
		this.flush();
		super.close();
	}
}
