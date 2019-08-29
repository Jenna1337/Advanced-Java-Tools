package io;

import java.io.FileWriter;
import java.io.IOException;

public class BufferedFileWriter extends java.io.BufferedWriter
{
	private static FileWriter w;
	
	/**
	 * Constructs a BufferedFileWriter object given a file name with a buffered character-output stream that uses a default-sized output buffer.
	 * @param fileName - String The system-dependent filename.
	 * @throws IOException if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
	 */
	public BufferedFileWriter(String fileName) throws IOException 
	{
		super(w = new FileWriter(fileName));
	}
	@Override
	public void close() throws IOException{
		super.close();
		w.close();
	}
}