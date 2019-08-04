package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLStreamHandler;

public class InetHost
{
	private final URL url;
	private final String hostname;
	public InetHost(URL url){
		this.url = url;
		this.hostname = url.getHost();
	}
	/**
	 * Get the IP address of our host. An empty host field or a DNS failure
	 * will result in a null return.
	 *
	 * @param u a URL object
	 * @return an {@code InetAddress} representing the host
	 * IP address.
	 */
	public InetAddress getInetAddress(){
		/*
		 * Note: some host's have roaming addresses,
		 * so we can't just do this once and save the result.
		 */
		try{
			Field urlhf = URL.class.getDeclaredField("handler");
			urlhf.setAccessible(true);
			URLStreamHandler h = (URLStreamHandler)urlhf.get(url);
			Method urlshgdpm = URLStreamHandler.class.getDeclaredMethod("getHostAddress", URL.class);
			urlshgdpm.setAccessible(true);
			return (InetAddress)urlshgdpm.invoke(h, url);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Gets the host name of this {@code InetHost}, if applicable.
	 * The format of the host conforms to RFC 2732, i.e. for a
	 * literal IPv6 address, this method will return the IPv6 address
	 * enclosed in square brackets ({@code '['} and {@code ']'}).
	 *
	 * @return  the host name of this {@code InetHost}.
	 */
	public String getHostname(){
		return hostname;
	}
	/**
	 * Shorthand for <br>
	 * {@code getInetAddress().getHostAddress()}
	 */
	public String getHostAddress(){
		return getInetAddress().getHostAddress();
	}
	@Override
	public String toString(){
		//addr != null ? addr.getHostAddress()
		
		return hostname + "," + getInetAddress();
	}
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof InetHost))
			return false;
		InetHost obj2 = (InetHost)obj;
		
		/*
		 * Partially from URLStreamHandler#hostsEqual(URL, URL)
		 */
		InetAddress a1 = this.getInetAddress();
		InetAddress a2 = obj2.getInetAddress();
		// if we have internet address for both, compare them
		if (a1 != null && a2 != null)
			return a1.equals(a2);
		// else, if both have host names, compare them
		else{
			String h1 = this.getHostname(),
					h2= obj2.getHostname();
			return ((h1 != null && h2 != null)
					&& h1.equalsIgnoreCase(h2)
					) || (h1 == null && h2 == null);
		}
	}
}
