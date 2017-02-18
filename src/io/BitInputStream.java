package io;

import java.io.DataInput;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import sys.DataBuffer;

public class BitInputStream extends FilterInputStream implements DataInput
{
	DataBuffer buffer;
	
	public BitInputStream(InputStream in) throws IOException
	{
		super(in);
		// TODO read all data from in into buffer
		in.close();
	}
	
	@Override
	public int read() throws IOException {
		return buffer.isEmpty() ? -1 : Byte.toUnsignedInt(buffer.getByte());
	}
	
	@Override
	public long skip(long n) throws IOException {
		// TODO Auto-generated constructor stub
		return 0;
	}
	
	/**
	 * Returns an estimate of the number of bits that can be read (or
	 * skipped over) from this input stream without blocking by the next
	 * caller of a method for this input stream. The next caller might be
	 * the same thread or another thread.  A single read or skip of this
	 * many bits will not block, but may read or skip fewer bits.
	 * <p>
	 * @return     an estimate of the number of bits that can be read (or skipped
	 *             over) from this input stream without blocking.
	 * @exception  IOException  if an I/O error occurs.
	 */
	@Override
	public int available() throws IOException {
		return buffer.size();
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized void mark(int readlimit) {}
	
	@Override
	public synchronized void reset() throws IOException {
		throw new IOException("mark/reset not supported");
	}
	
	@Override
	public boolean markSupported() {
		return false;
	}
	
}
