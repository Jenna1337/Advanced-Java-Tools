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
		// TODO Auto-generated constructor stub
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
