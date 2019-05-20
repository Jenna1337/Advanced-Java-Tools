package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

public class WebRequest
{
	private WebRequest(){}
	
	static{
		final CookieManager cook = new CookieManager();
		cook.setCookiePolicy(java.net.CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cook);
		HttpURLConnection.setFollowRedirects(true);
	}
	private static String[][] defaultHeaders = new String[0][0];
	
	static{
		String[][] headers = {
				{"Accept", "*/*"},
				{"Accept-Encoding", "gzip, deflate"},
				{"Accept-Language", "en-US,en;q=0.5"},
				{"DNT", "1"},
				{"Cache-Control", "no-cache"},
				//{"Connection", "keep-alive"},
				{"Content-Type", "application/x-www-form-urlencoded"},
				{"Upgrade-Insecure-Requests", "1"},
				{"User-Agent", "Mozilla/5.0 (X11; Linux i686; rv:17.0) Gecko/20100101 Firefox/17.0"},
		};
		WebRequest.setDefaultHeaders(headers);
	}
	
	
	
	public static synchronized String GET(String url, String[]... optionalheaders) throws MalformedURLException, IOException{
		return GET(new URL(url), optionalheaders);
	}
	public static synchronized String GET(URL url, String[]... optionalheaders) throws IOException{
		HttpURLConnection connection = request0("GET", url, optionalheaders);
		return read(connection);
	}
	
	public static synchronized WebResponse getResponse(String reqMethod, URL url, String[]... optionalheaders){
		return new WebResponse(request0(reqMethod, url, optionalheaders));
	}
	
	public static synchronized String POST(String url, String data, String[]... optionalheaders) throws MalformedURLException, IOException
	{
		return POST(new URL(url), data, optionalheaders);
	}
	public static synchronized String POST(URL url, String data, String[]... optionalheaders) throws IOException
	{
		HttpURLConnection connection = request0("POST", url, optionalheaders);
		connection.setDoOutput(true);
		send(connection, data);
		if(connection.getResponseCode()==409)
			return null;
		try{
			return read(connection);
		}catch(NullPointerException npe){
			npe.printStackTrace();
			return null;
		}
	}
	
	public static synchronized Map<String, List<String>> HEAD(String url, String[]... optionalheaders) throws MalformedURLException, IOException
	{
		return HEAD(new URL(url), optionalheaders);
	}
	public static synchronized Map<String, List<String>> HEAD(URL url, String[]... optionalheaders) throws IOException
	{
		HttpURLConnection connection = request0("HEAD", url, optionalheaders);
		return connection.getHeaderFields();
	}
	public static void setDefaultHeaders(String[][] headers)
	{
		WebRequest.defaultHeaders = headers;
	}
	private static HttpURLConnection setHeaders(HttpURLConnection connection, String[][]... headermaplist){
		for(String[][] headermaps : headermaplist){
			for(String[] headermap : headermaps){
				if(headermap!=null){
					if(headermap.length!=2)
						throw new IllegalArgumentException("Invalid header "+java.util.Arrays.deepToString(headermap));
					connection.setRequestProperty(headermap[0], headermap[1]);
				}
			}
		}
		return connection;
	}
	private static HttpURLConnection request0(String method, URL url, String[]... optionalheaders)
	{
		try{
			HttpURLConnection connection = setHeaders((HttpURLConnection) url.openConnection(), defaultHeaders, optionalheaders);
			connection.setRequestMethod(method);
			return connection;
		}catch(IOException e){
			throw new InternalError(e);
		}
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
	 * Reads the content from a server.
	 * @param connection the HttpURLConnection to read data from.
	 * @return The content read from the server.
	 * @throws IOException if an IOException occurs.
	 */
	private static synchronized String read(HttpURLConnection connection) throws IOException{
		return Utils.rawBytesToString(getRawBytes(connection));
	}
	/**
	 * Sends the data to the server via the {@code connection}.
	 * @param connection the connection to the server to send data to.
	 * @param data the data to send.
	 * @throws IOException if an IOException occurs.
	 */
	private static synchronized void send(HttpURLConnection connection, String data) throws IOException
	{
		try{
			connection.connect();
			OutputStream os = connection.getOutputStream();
			os.write(data.getBytes(StandardCharsets.UTF_8));
			os.close();
		}catch(Exception e){
			try{
				Thread.sleep(1000);
				send(connection, data);
			}
			catch(Exception | StackOverflowError err){
				if(err.getClass().isAssignableFrom(StackOverflowError.class))
					System.err.println("Connection timed out to "+connection.getURL());
			}
		}
	}
}
