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
		
		// read all data from in into buffer
		int b;
		while((b = in.read())>=0){
			buffer.putByte((byte)b);
		}
		in.close();
	}
	
	@Override
	public int read() throws IOException {
		return buffer.isEmpty() ? -1 : Byte.toUnsignedInt(buffer.getByte());
	}
	
	/**
	 * Skips over and discards <code>n</code> bits of data from the
	 * input stream. The <code>skip</code> method may, for a variety of
	 * reasons, end up skipping over some smaller number of bytes,
	 * possibly <code>0</code>. The actual number of bits skipped is
	 * returned.
	 * <p>
	 * This method simply performs <code>in.skip(n)</code>.
	 *
	 * @param      n   the number of bits to be skipped.
	 * @return     the actual number of bits skipped.
	 * @exception  IOException  if the stream does not support seek,
	 *                          or if some other I/O error occurs.
	 */
	@Override
	public long skip(long n) throws IOException {
		long skipped = 0;
		for(skipped=0; skipped<=n; ++skipped){
			if(buffer.isEmpty())
				break;
			buffer.getBit();
		}
		return skipped;
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
	
	/**
	 * Closes this input stream and releases any system resources associated
	 * with the stream.
	 *
	 * <p> The <code>close</code> method of <code>InputStream</code> does
	 * nothing.
	 *
	 * @exception  IOException  if an I/O error occurs.
	 */
	@Override
	public void close() throws IOException {
		in.close();
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
	
	public void readFully(byte[] b) throws IOException
	{
		for(int i=0; i<b.length; ++i){
			if(buffer.isEmpty())
				throw new java.io.EOFException();
			b[i] = buffer.getByte();
		}
	}
	
	public void readFully(byte[] b, int off, int len) throws IOException
	{
		// if(off < 0 || len < 0 || off+len > b.length)
		//     throw new IndexOutOfBoundsException();
		// If len is zero, then no bytes are read.
		// Otherwise, the first byte read is stored into element b[off], 
		// the next one into b[off+1], and so on. The number of bytes read is,
		// at most, equal to len.
		for(int i=0; i<len; ++i){
			if(buffer.isEmpty())
				throw new java.io.EOFException();
			b[i+off] = buffer.getByte();
		}
	}
	
	public int skipBytes(int n) throws IOException
	{
		int skipped = 0;
		for(skipped=0; skipped<=n; ++skipped){
			if(buffer.isEmpty())
				break;
			buffer.getByte();
		}
		return skipped;
	}
	
	public boolean readBoolean() throws IOException
	{
		return buffer.getBoolean();
	}
	
	public byte readByte() throws IOException
	{
		return buffer.getByte();
	}
	
	public int readUnsignedByte() throws IOException
	{
		return Byte.toUnsignedInt(buffer.getByte());
	}
	
	public short readShort() throws IOException
	{
		return buffer.getShort();
	}
	
	public int readUnsignedShort() throws IOException
	{
		return Short.toUnsignedInt(buffer.getShort());
	}
	
	public char readChar() throws IOException
	{
		return buffer.getChar();
	}
	
	public int readInt() throws IOException
	{
		return buffer.getInt();
	}
	
	public long readLong() throws IOException
	{
		return buffer.getLong();
	}
	
	public float readFloat() throws IOException
	{
		return buffer.getFloat();
	}
	
	public double readDouble() throws IOException
	{
		return buffer.getDouble();
	}
	/**
	 * Reads the next line of text from the input stream. It reads successive
	 * characters, until it encounters a line terminator or end of file; the
	 * characters read are then returned as a {@code String}. 
	 * <p>
	 * If end of file is encountered before even one character can be read,
	 * then {@code null} is returned. If the character {@code '\n'} is
	 * encountered, it is discarded and reading ceases. If the character
	 * {@code '\r'} is encountered, it is discarded and, if the following
	 * character is {@code '\n'}, then that is discarded also; reading then
	 * ceases. If end of file is encountered before either of the characters
	 * {@code '\n'} and {@code '\r'} is encountered, reading ceases. Once
	 * reading has ceased, a {@code String} is returned that contains all the
	 * characters read and not discarded, taken in order.
	 *
	 * @return the next line of text from the input stream,
	 *         or {@code null} if the end of file is
	 *         encountered before a character can be read.
	 * @exception  IOException  if an I/O error occurs.
	 */
	public String readLineChars()
	{
		String str = "";
		char c = '0';
		while(!buffer.isEmpty()){
			c = buffer.getChar();
			if(c != '\n' && c != '\r')
				str += c;
			else if(c == '\n')
				break;
			else if(c == '\r'){
				if(!buffer.isEmpty() && buffer.peekChar()=='\n')
					buffer.getChar();
				break;
			}
		}
		return str;
	}
	/**
	 * {@inheritDoc}
     * @deprecated This method does not properly convert bytes to characters.
	 */
	@Deprecated
	public String readLine() throws IOException
	{
		String str = "";
		char c = '0';
		while(!buffer.isEmpty()){
			c = (char) buffer.getByte();
			if(c != '\n' && c != '\r')
				str += c;
			else if(c == '\n')
				break;
			else if(c == '\r'){
				if(!buffer.isEmpty() && ((char)buffer.peekByte())=='\n')
					buffer.getByte();
				break;
			}
		}
		return str;
	}
	
	public String readUTF() throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
