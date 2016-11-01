package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class InputReaderStream extends InputStream
{
	private final Reader reader;
	public InputReaderStream(Reader reader)
	{
		this.reader = reader;
	}
	
	public int read() throws IOException
	{
		return reader.read();
	}
	@Override
	public void close() throws IOException
	{
		reader.close();
		super.close();
	}
	@Override
	public synchronized void reset() throws IOException
	{
		reader.reset();
		super.reset();
	}
	@Override
	public long skip(long n) throws IOException
	{
		reader.skip(n);
		return super.skip(n);
	}
}
