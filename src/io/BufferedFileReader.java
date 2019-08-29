package io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BufferedFileReader extends java.io.BufferedReader
{
	private static FileReader r;
	
	/**
	 * Creates a new BufferedFileReader, given the name of the file to read from, with a buffered character-input stream that uses a default-sized input buffer.
	 * @param fileName - the name of the file to read from
	 * @throws FileNotFoundException if the named file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
	 */
	public BufferedFileReader(String fileName) throws FileNotFoundException
	{
		super(r = new FileReader(fileName));
	}
	@Override
	public void close() throws IOException{
		super.close();
		r.close();
	}
}
