package io;

import java.io.FileNotFoundException;

public class BufferedFileReader extends java.io.BufferedReader
{
	/**
	 * Creates a new BufferedFileReader, given the name of the file to read from, with a buffered character-input stream that uses a default-sized input buffer.
	 * @param fileName - the name of the file to read from
	 * @throws FileNotFoundException if the named file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
	 */
	public BufferedFileReader(String fileName) throws FileNotFoundException
	{
		super(new java.io.FileReader(fileName));
	}
}
