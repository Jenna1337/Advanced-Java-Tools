package utils;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

public class WebResponse
{
	private final Map<String, List<String>> responseHeaders, requestHeaders;
	private final String requestMethod;
	private final int responseCode;
	private final byte[] body;
	
	WebResponse(HttpURLConnection connection){
		this.requestHeaders = connection.getRequestProperties();
		this.responseHeaders = connection.getHeaderFields();
		this.requestMethod = connection.getRequestMethod();
		try{
		this.responseCode = connection.getResponseCode();
		}catch(Exception e){
			throw new IOError(e);
		}
		byte[] b = null;
		try{
			b = getRawBytes(connection);
		}catch(Exception e){
			throw new IOError(e);
		}
		this.body = b;
	}
	
	/**
	 * Reads raw bytes from a server.
	 * @param connection the HttpURLConnection to read data from.
	 * @return The content read from the server.
	 * @throws IOException if an IOException occurs.
	 */
	private static synchronized byte[] getRawBytes(HttpURLConnection connection) throws IOException
	{
		connection.connect();
		String encoding = (connection.getContentEncoding()!=null) ? connection.getContentEncoding().toLowerCase() : "";
		InputStream is = connection.getInputStream();
		switch(encoding){
			case "gzip":
				is=new GZIPInputStream(is);
				break;
			case "deflate":
				is=new DeflaterInputStream(is);
				break;
			default:
				break;
		}
		Path tempfilepath = Files.createTempFile(null, null);
		Files.copy(is, tempfilepath, StandardCopyOption.REPLACE_EXISTING);
		is.close();
		byte[] output = Files.readAllBytes(tempfilepath);
		Files.delete(tempfilepath);
		return output;
	}
	/**
	 * Returns an unmodifiable Map of the header fields. The Map keys are
	 * Strings that represent the response-header field names. Each Map value is
	 * an unmodifiable List of Strings that represents the corresponding field
	 * values.
	 * 
	 * @return a Map of header fields
	 */
	public Map<String, List<String>> getResponseHeaders(){
		return responseHeaders;
	}
	/**
	 * Returns an unmodifiable Map of general request properties for this
	 * connection. The Map keys are Strings that represent the request-header
	 * field names. Each Map value is a unmodifiable List of Strings that
	 * represents the corresponding field values.
	 * 
	 * @return a Map of the general request properties for this connection.
	 */
	public Map<String, List<String>> getRequestHeaders(){
		return requestHeaders;
	}
	public byte[] getBodyAsBytes(){
		return Arrays.copyOf(body, body.length);
	}
	public String getBodyAsString(){
		return Utils.rawBytesToString(getBodyAsBytes());
	}
	public int getResponseCode(){
		return responseCode;
	}
	public String getRequestMethod(){
		return requestMethod;
	}
}
