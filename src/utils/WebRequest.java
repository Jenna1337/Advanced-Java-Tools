package utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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
	
	private static boolean allowUpgradeRedirects = true;
	
	/**
	 * Sets whether HTTP redirects (requests with response code 3xx) that change
	 * the protocol from HTTP to HTTPS should be automatically followed by this
	 * class. True by default.
	 * 
	 * @param set a boolean indicating whether or not to follow HTTP redirects.
	 * @see #getAllowUpgradeRedirects()
	 * @see #setAllowUnsafeRedirects(boolean)
	 * @see #getAllowUnsafeRedirects()
	 * @see HttpURLConnection#setFollowRedirects(boolean)
	 * @see HttpURLConnection#getFollowRedirects()
	 */
	public static void setAllowUpgradeRedirects(boolean set){
		allowUpgradeRedirects = set;
	}
	/**
	 * Returns a {@code boolean} indicating whether or not HTTP redirects (3xx)
	 * that change the protocol from HTTP to HTTPS should be automatically
	 * followed.
	 *
	 * @return {@code true} if redirects that change the protocol from HTTP to
	 * HTTPS should be automatically followed, {@code false} if not.
	 * @see #setAllowUpgradeRedirect(boolean)
	 * @see #setAllowUnsafeRedirects(boolean)
	 * @see #getAllowUnsafeRedirects()
	 * @see HttpURLConnection#setFollowRedirects(boolean)
	 * @see HttpURLConnection#getFollowRedirects()
	 */
	public static boolean getAllowUpgradeRedirects(){
		return allowUpgradeRedirects;
	}
	
	private static boolean allowUnsafeRedirects = false;
	/**
	 * Sets whether HTTP redirects (requests with response code 3xx) that change
	 * the protocol and host or path should be automatically followed by this
	 * class. False by default.<br>
	 * <br>
	 * <b>Note: Enabling this will bypass {@link #setAllowUpgradeRedirects(boolean)}

	 * 
	 * @param set a boolean indicating whether or not to follow HTTP redirects.
	 * @see #getAllowUnsafeRedirects()
	 * @see #setAllowUpgradeRedirects(boolean)
	 * @see #getAllowUpgradeRedirects()
	 * @see HttpURLConnection#setFollowRedirects(boolean)
	 * @see HttpURLConnection#getFollowRedirects()
	 */
	public static void setAllowUnsafeRedirects(boolean set){
		allowUnsafeRedirects = set;
	}
	/**
	 * Returns a {@code boolean} indicating whether or not HTTP redirects (3xx)
	 * that change the protocol and host or path should be automatically
	 * followed.<br>
	 * <br>
	 * <b>Note: Enabling this will bypass {@link #setAllowUpgradeRedirects(boolean)}
	 *
	 * @return {@code true} if redirects that change the protocol and host or
	 * path should be automatically followed, {@code false} if not.
	 * @see #setAllowUnsafeRedirects(boolean)
	 * @see #setAllowUpgradeRedirects(boolean)
	 * @see #getAllowUpgradeRedirects()
	 * @see HttpURLConnection#setFollowRedirects(boolean)
	 * @see HttpURLConnection#getFollowRedirects()
	 */
	public static boolean getAllowUnsafeRedirects(){
		return allowUnsafeRedirects;
	}
	
	static{
		String[][] headers = {
				{"Accept", "*/*"},
				{"Accept-Encoding", "gzip, deflate"},
				{"Accept-Language", "en-US,en;q=0.5"},
				{"DNT", "1"},
				{"Cache-Control", "no-cache"},
				{"Pragma", "no-cache"},
				//{"Connection", "keep-alive"},
				{"Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"},
				{"Upgrade-Insecure-Requests", "1"},
				{"User-Agent", "Mozilla/5.0 (X11; Linux i686; rv:70.0) Gecko/20100101 Firefox/70.0"},
		};
		WebRequest.setDefaultHeaders(headers);
	}
	
	
	private static int maxProtocolRedirects = 2;
	private static int currentProtocolRedirects = 0;
	
	public static synchronized WebResponse getResponse(String reqMethod, String url, String[]... optionalheaders) throws IOException{
		return getResponse(reqMethod, new URL(url), optionalheaders);
	}
	public static synchronized WebResponse getResponse(String reqMethod, URL url, String[]... optionalheaders) throws IOException{
		WebResponse response = new WebResponse(request0(reqMethod, url, optionalheaders));
		final int rcode = response.getResponseCode();
		if(rcode>=300 && rcode<400){
			List<String> ll = response.getResponseHeaders().get("Location");
			String loc = null;
			if(ll!=null && ll.size()>0)
				loc = ll.get(0);
			if(loc != null && !loc.isEmpty()){
				if(currentProtocolRedirects>=maxProtocolRedirects){
					throw new ProtocolRedirectException("Maximum protocol redirects exceeded");
				}
				currentProtocolRedirects++;
				try{
					URL uloc = new URL(loc);
					if(getAllowUnsafeRedirects()){
						return getResponse(reqMethod, uloc, optionalheaders);
					}
					else if(getAllowUpgradeRedirects()
							&& isProtocolUpgradeOnly(url, uloc)){
						return getResponse(reqMethod, uloc, optionalheaders);
					}
				}
				catch(Exception e){
					e.printStackTrace();
					//TODO
				}
				currentProtocolRedirects--;
			}
			else{
				throw new IOException("Server response code was "+rcode+" for "+reqMethod+" request of "+url+" with headers "+optionalheaders.toString()+", but provided no Location header!");
			}
		}
		return response;
	}
	private static boolean isProtocolUpgradeOnly(URL url, URL uloc){
		try{
			return (new InetHost(url).equals(new InetHost(uloc)))
					&& getSecureProtocolForm(url.getProtocol()
							).equals(uloc.getProtocol());
		}
		catch(ProtocolException e){
			e.printStackTrace();
			return false;
		}
	}
	
	private static String getSecureProtocolForm(String protocol) throws ProtocolException{
		switch(protocol){
			case "http":
				return "https";
			case "ftp":
				//note: Java 1.8 does not support sftp by default
				return "sftp";
			case "ws":
				return "wss";
			default:
				throw new ProtocolException("Unknown protocol: "+protocol);
		}
	}
	public static synchronized String GET(String url, String[]... optionalheaders) throws MalformedURLException, IOException{
		return getResponse("GET", url, optionalheaders).getBodyAsString();
	}
	public static synchronized String GET(URL url, String[]... optionalheaders) throws IOException{
		return getResponse("GET", url, optionalheaders).getBodyAsString();
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
			return new WebResponse(connection).getBodyAsString();
		}catch(NullPointerException npe){
			npe.printStackTrace();
			return null;
		}
	}
	
	public static synchronized Map<String, List<String>> HEAD(String url, String[]... optionalheaders) throws MalformedURLException, IOException{
		return getResponse("HEAD", url, optionalheaders).getResponseHeaders();
	}
	public static synchronized Map<String, List<String>> HEAD(URL url, String[]... optionalheaders) throws IOException{
		return getResponse("HEAD", url, optionalheaders).getResponseHeaders();
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
class ProtocolRedirectException extends IOException{
	public ProtocolRedirectException(String string){
		super(string);
	}
}
