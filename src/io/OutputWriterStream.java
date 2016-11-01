package io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class OutputWriterStream extends OutputStream
{
	private final Writer writer;
	public OutputWriterStream(Writer writer)
	{
		this.writer = writer;
	}
	
	public void write(int b) throws IOException
	{
		writer.write(b);
	}
	@Override
	public void close() throws IOException
	{
		writer.close();
		super.close();
	}
	@Override
	public void flush() throws IOException
	{
		writer.flush();
		super.flush();
	}
}
