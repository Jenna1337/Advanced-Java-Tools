package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessUtils
{
	private ProcessUtils()
	{
	}
	
	public static void dumpOutput(Process process, OutputStream out)
	{
		dump(process.getInputStream(), out);
	}
	public static void dumpError(Process process, OutputStream out)
	{
		dump(process.getErrorStream(), out);
	}
	/**
	 * Prints input from {@code in} to {@code out}.
	 * @param in The input stream.
	 * @param out The output stream.
	 * @throws Error if an I/O error occurs.
	 */
	public static void dump(InputStream in, OutputStream out)
	{
		try{
		int b;
		while((b = in.read())!=-1)
			out.write(b);
		}catch(IOException e)
		{
			throw new Error(e);
		}
	}
}
