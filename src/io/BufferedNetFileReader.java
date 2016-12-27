package io;

import java.io.IOException;

/**This class represents a {@link java.io.BufferedReader BufferedReader}
 * capable of reading data directly from a URL. 
 * 
 * @see java.net.URL#URL(String)
 * @see java.net.URL#openConnection()
 * @see java.net.URLConnection#getInputStream()
 * @see java.io.InputStreamReader#InputStreamReader(java.io.InputStream)
 */
public class BufferedNetFileReader extends java.io.BufferedReader
{
	public BufferedNetFileReader(String urlstring) throws IOException
	{
		this(new java.net.URL(urlstring));
	}
	public BufferedNetFileReader(java.net.URL url) throws IOException
	{
		this(url.openConnection());
	}
	public BufferedNetFileReader(java.net.URLConnection urlconnect) throws IOException
	{
		this(urlconnect.getInputStream());
	}
	public BufferedNetFileReader(java.io.InputStream instream) throws IOException
	{
		super(new java.io.InputStreamReader(instream));
	}
	public String readAllLines() throws IOException
	{
		String text="", line;
		while((line=super.readLine()) != null)
			text+=line+System.lineSeparator();
		return text;
	}
}
