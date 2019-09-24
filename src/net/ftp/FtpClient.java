/*
 * Note: this file is mostly a wrapper for the sun.net.ftp.FtpClient class.
 */
/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package net.ftp;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.List;
import utils.ClassUtils;
import java.util.Iterator;

/**
 * A class that implements the FTP protocol according to
 * RFCs <A href="http://www.ietf.org/rfc/rfc0959.txt">959</A>,
 * <A href="http://www.ietf.org/rfc/rfc2228.txt">2228</A>,
 * <A href="http://www.ietf.org/rfc/rfc2389.txt">2389</A>,
 * <A href="http://www.ietf.org/rfc/rfc2428.txt">2428</A>,
 * <A href="http://www.ietf.org/rfc/rfc3659.txt">3659</A>,
 * <A href="http://www.ietf.org/rfc/rfc4217.txt">4217</A>.
 * Which includes support for FTP over SSL/TLS (aka ftps).
 *
 * {@code FtpClient} provides all the functionalities of a typical FTP
 * client, like storing or retrieving files, listing or creating directories.
 * A typical usage would consist of connecting the client to the server,
 * log in, issue a few commands then logout.
 * Here is a code example:
 * 
 * <pre>
 * FtpClient cl = FtpClient.create();
 * cl.connect("ftp.gnu.org").login("anonymous", "john.doe@mydomain.com".toCharArray())).changeDirectory("pub/gnu");
 * Iterator&lt;FtpDirEntry&gt; dir = cl.listFiles();
 *     while (dir.hasNext()) {
 *         FtpDirEntry f = dir.next();
 *         System.err.println(f.getName());
 *     }
 *     cl.close();
 * }
 * </pre>
 * <p>
 * <b>Error reporting:</b> There are, mostly, two families of errors that
 * can occur during an FTP session. The first kind are the network related
 * issues
 * like a connection reset, and they are usually fatal to the session, meaning,
 * in all likelyhood the connection to the server has been lost and the session
 * should be restarted from scratch. These errors are reported by throwing an
 * {@link IOException}. The second kind are the errors reported by the FTP
 * server,
 * like when trying to download a non-existing file for example. These errors
 * are usually non fatal to the session, meaning more commands can be sent to
 * the
 * server. In these cases, a {@link FtpProtocolException} is thrown.
 * </p>
 * <p>
 * It should be noted that this is not a thread-safe API, as it wouldn't make
 * too much sense, due to the very sequential nature of FTP, to provide a
 * client able to be manipulated from multiple threads.
 *
 * @since 1.7
 */
public class FtpClient
{
	private static final String ftpClientClassName = "sun.net.ftp.impl.FtpClient";
	private static final Class<?> ftpClientClass;
	private static final String ftpDirEntryClassName = "sun.net.ftp.FtpDirEntry";
	static final Class<?> ftpDirEntryClass;
	
	static {
		try{
			ftpClientClass = Class.forName(ftpClientClassName);
			ftpDirEntryClass = Class.forName(ftpDirEntryClassName);
		}
		catch(ClassNotFoundException e){
			throw new InternalError(e);
		}
	}
	
	private static final int FTP_PORT = 21;
	
//	/**
//	 * Returns the default FTP port number.
//	 *
//	 * @return the port number.
//	 */
//	public static final int defaultPort(){
//		return FTP_PORT;
//	}
	
	/**
	 * Creates an instance of FtpClient and connects it to the specified
	 * address.
	 *
	 * @param dest the {@code InetSocketAddress} to connect to.
	 * @return The created {@code FtpClient}
	 * @throws IOException if the connection fails
	 * @see #connect(java.net.SocketAddress)
	 */
	public static FtpClient create(InetSocketAddress dest)
			throws FtpProtocolException, IOException{
		FtpClient client = create();
		if(dest != null){
			client.connect(dest);
		}
		return client;
	}
	
	/**
	 * Creates an instance of {@code FtpClient} and connects it to the
	 * specified host on the default FTP port.
	 *
	 * @param dest the {@code String} containing the name of the host
	 * to connect to.
	 * @return The created {@code FtpClient}
	 * @throws IOException if the connection fails.
	 * @throws FtpProtocolException if the server rejected the connection
	 */
	public static FtpClient create(String dest)
			throws FtpProtocolException, IOException{
		return create(new InetSocketAddress(dest, FTP_PORT));
	}
	
	/**
	 * Creates an instance of FtpClient. The client is not connected to any
	 * server yet.
	 *
	 */
	public static FtpClient create(){
		return new FtpClient();
	}
	
	/**
	 * Creates an instance of FtpClient. The client is not connected to any
	 * server yet.
	 *
	 */
	protected FtpClient(){
		try{
			client = ClassUtils.invokeStaticMethod(ftpClientClass, "create");
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Object client;
	
	@SuppressWarnings("unchecked")
	private <T,X extends Throwable> T invokeQualifiedMethod(String name, Object... refpairs) throws X{
		return (T)normalize(ClassUtils.invokeMethod(ftpClientClass, this.client, name, refpairs));
	}
	
	private Object normalize(Object obj){
		if(obj==null)
			return null;
		Class<?> cls = obj.getClass();
		if(ftpClientClass.isAssignableFrom(cls)){
			client = ftpClientClass.cast(obj);
			return this;
		}
		else if(cls.getSimpleName().equals("FtpReplyCode")){
			return ClassUtils.castEnumConstant(obj, FtpReplyCode.class);
		}
		else if(Iterator.class.isAssignableFrom(cls)){
			try{
				if(cls.getMethod("next").getReturnType().isAssignableFrom(ftpDirEntryClass)){
					return new FtpFileIterator(Iterator.class.cast(obj));
				}
			}
			catch(NoSuchMethodException | SecurityException e){
				throw new InternalError(e);
			}
		}
		return obj;
	}
	
	
	
	

	/**
	 * Transfers a file from the client to the server (aka a <I>put</I>)
	 * by sending the STOR command, and returns the {@code OutputStream}
	 * from the established data connection.
	 *
	 * A new file is created at the server site if the file specified does
	 * not already exist.
	 *
	 * {@link #completePending()} <b>has</b> to be called once the application
	 * is finished writing to the returned stream.
	 *
	 * @param name the name of the remote file to write.
	 * @return the {@link java.io.OutputStream} from the data connection or
	 * {@code null} if the command was unsuccessful.
	 * @throws IOException if an error occurred during the transmission.
	 * @throws FtpProtocolException if the command was rejected by the server
	 */
	public OutputStream putFileStream(String name)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("putFileStream", String.class, name);
	}
	
	/**
	 * Transfers a file from the client to the server (aka a <I>put</I>)
	 * by sending the STOR or STOU command, depending on the
	 * {@code unique} argument. The content of the {@code InputStream}
	 * passed in argument is written into the remote file, overwriting any
	 * existing data.
	 *
	 * A new file is created at the server site if the file specified does
	 * not already exist.
	 *
	 * If {@code unique} is set to {@code true}, the resultant file
	 * is to be created under a name unique to that directory, meaning
	 * it will not overwrite an existing file, instead the server will
	 * generate a new, unique, file name.
	 * The name of the remote file can be retrieved, after completion of the
	 * transfer, by calling {@link #getLastFileName()}.
	 *
	 * <p>
	 * This method will block until the transfer is complete or an exception
	 * is thrown.
	 * </p>
	 *
	 * @param name the name of the remote file to write.
	 * @param local the {@code InputStream} that points to the data to
	 * transfer.
	 * @return this FtpClient
	 * @throws IOException if an error occurred during the transmission.
	 * @throws FtpProtocolException if the command was rejected by the server
	 */
	public FtpClient putFile(String name, InputStream local)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("putFile", String.class, name, InputStream.class, local);
	}
	
	/**
	 * Changes the current transfer type to binary.
	 *
	 * @return This FtpClient
	 * @throws IOException if an error occurs during the transmission.
	 * @throws FtpProtocolException if the command was rejected by the server
	 */
	public FtpClient setBinaryType() throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("setBinaryType");
	}
	
	/**
	 * Changes the current transfer type to ascii.
	 *
	 * @return This FtpClient
	 * @throws IOException if an error occurs during the transmission.
	 * @throws FtpProtocolException if the command was rejected by the server
	 */
	public FtpClient setAsciiType() throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("setAsciiType");
	}
	
	/**
	 * Set the transfer mode to <I>passive</I>. In that mode, data connections
	 * are established by having the client connect to the server.
	 * This is the recommended default mode as it will work best through
	 * firewalls and NATs.
	 *
	 * @return This FtpClient
	 * @see #setActiveMode()
	 */
	public FtpClient enablePassiveMode(boolean passive){
		return invokeQualifiedMethod("enablePassiveMode", boolean.class, passive);
	}
	
	/**
	 * Gets the current transfer mode.
	 *
	 * @return the current <code>FtpTransferMode</code>
	 */
	public boolean isPassiveModeEnabled(){
		return invokeQualifiedMethod("isPassiveModeEnabled");
	}
	
	/**
	 * Sets the timeout value to use when connecting to the server,
	 *
	 * @param timeout the timeout value, in milliseconds, to use for the connect
	 * operation. A value of zero or less, means use the default timeout.
	 *
	 * @return This FtpClient
	 */
	public FtpClient setConnectTimeout(int timeout){
		return invokeQualifiedMethod("setConnectTimeout", int.class, timeout);
	}
	
	/**
	 * Returns the current connection timeout value.
	 *
	 * @return the value, in milliseconds, of the current connect timeout.
	 * @see #setConnectTimeout(int)
	 */
	public int getConnectTimeout(){
		return invokeQualifiedMethod("getConnectTimeout");
	}
	
	/**
	 * Sets the timeout value to use when reading from the server,
	 *
	 * @param timeout the timeout value, in milliseconds, to use for the read
	 * operation. A value of zero or less, means use the default timeout.
	 * @return This FtpClient
	 */
	public FtpClient setReadTimeout(int timeout){
		return invokeQualifiedMethod("setReadTimeout", int.class, timeout);
	}
	
	/**
	 * Returns the current read timeout value.
	 *
	 * @return the value, in milliseconds, of the current read timeout.
	 * @see #setReadTimeout(int)
	 */
	public int getReadTimeout(){
		return invokeQualifiedMethod("getReadTimeout");
	}
	
	/**
	 * Set the {@code Proxy} to be used for the next connection.
	 * If the client is already connected, it doesn't affect the current
	 * connection. However it is not recommended to change this during a
	 * session.
	 *
	 * @param p the {@code Proxy} to use, or {@code null} for no proxy.
	 * @return This FtpClient
	 */
	public FtpClient setProxy(Proxy p){
		return invokeQualifiedMethod("setProxy", Proxy.class, p);
	}
	
	/**
	 * Get the proxy of this FtpClient
	 *
	 * @return the <code>Proxy</code>, this client is using, or
	 * <code>null</code>
	 * if none is used.
	 * @see #setProxy(Proxy)
	 */
	public Proxy getProxy(){
		return invokeQualifiedMethod("getProxy");
	}
	
	/**
	 * Tests whether this client is connected or not to a server.
	 *
	 * @return <code>true</code> if the client is connected.
	 */
	public boolean isConnected(){
		return invokeQualifiedMethod("isConnected");
	}
	
	/**
	 * Retrieves the address of the FTP server this client is connected to.
	 *
	 * @return the {@link SocketAddress} of the server, or {@code null} if this
	 * client is not connected yet.
	 */
	public SocketAddress getServerAddress(){
		return invokeQualifiedMethod("getServerAddress");
	}
	
	/**
	 * Connects the {@code FtpClient} to the specified destination server.
	 *
	 * @param dest the address of the destination server
	 * @return this FtpClient
	 * @throws IOException if connection failed.
	 * @throws SecurityException if there is a SecurityManager installed and it
	 * denied the authorization to connect to the destination.
	 * @throws FtpProtocolException
	 */
	public FtpClient connect(SocketAddress dest)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("connect", SocketAddress.class, dest);
	}
	
	/**
	 * Connects the FtpClient to the specified destination.
	 *
	 * @param dest the address of the destination server
	 * @throws IOException if connection failed.
	 */
	public FtpClient connect(SocketAddress dest, int timeout)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("connect", SocketAddress.class, dest, int.class, timeout);
	}
	
	/**
	 * Attempts to log on the server with the specified user name and password.
	 *
	 * @param user The user name
	 * @param password The password for that user
	 * @return <code>true</code> if the login was successful.
	 * @throws IOException if an error occurred during the transmission
	 */
	public FtpClient login(String user, char[] password)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("login", String.class, user, char[].class, password);
	}
	
	/**
	 * Attempts to log on the server with the specified user name, password and
	 * account name.
	 *
	 * @param user The user name
	 * @param password The password for that user.
	 * @param account The account name for that user.
	 * @return <code>true</code> if the login was successful.
	 * @throws IOException if an error occurs during the transmission.
	 */
	public FtpClient login(String user, char[] password, String account)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("login", String.class, user, char[].class, password, String.class, account);
	}
	
	/**
	 * Logs out the current user. This is in effect terminates the current
	 * session and the connection to the server will be closed.
	 *
	 */
	public void close() throws IOException{
		invokeQualifiedMethod("close");
	}
	
	/**
	 * Checks whether the client is logged in to the server or not.
	 *
	 * @return <code>true</code> if the client has already completed a login.
	 */
	public boolean isLoggedIn(){
		return invokeQualifiedMethod("isLoggedIn");
	}
	
	/**
	 * Changes to a specific directory on a remote FTP server
	 *
	 * @param remoteDirectory path of the directory to CD to.
	 * @return <code>true</code> if the operation was successful.
	 * @exception <code>FtpProtocolException</code>
	 */
	public FtpClient changeDirectory(String remoteDirectory)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("changeDirectory", String.class, remoteDirectory);
	}
	
	/**
	 * Changes to the parent directory, sending the CDUP command to the server.
	 *
	 * @return <code>true</code> if the command was successful.
	 * @throws IOException
	 */
	public FtpClient changeToParentDirectory()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("changeToParentDirectory");
	}
	
	/**
	 * Returns the server current working directory, or <code>null</code> if
	 * the PWD command failed.
	 *
	 * @return a <code>String</code> containing the current working directory,
	 * or <code>null</code>
	 * @throws IOException
	 */
	public String getWorkingDirectory()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("getWorkingDirectory");
	}
	
	/**
	 * Sets the restart offset to the specified value. That value will be
	 * sent through a <code>REST</code> command to server before a file
	 * transfer and has the effect of resuming a file transfer from the
	 * specified point. After a transfer the restart offset is set back to
	 * zero.
	 *
	 * @param offset the offset in the remote file at which to start the next
	 * transfer. This must be a value greater than or equal to zero.
	 * @throws IllegalArgumentException if the offset is negative.
	 */
	public FtpClient setRestartOffset(long offset){
		return invokeQualifiedMethod("setRestartOffset", long.class, offset);
	}
	
	/**
	 * Retrieves a file from the ftp server and writes it to the specified
	 * <code>OutputStream</code>.
	 * If the restart offset was set, then a <code>REST</code> command will be
	 * sent before the RETR in order to restart the tranfer from the specified
	 * offset.
	 * The <code>OutputStream</code> is not closed by this method at the end
	 * of the transfer.
	 *
	 * @param name a <code>String<code> containing the name of the file to
	 *        retreive from the server.
	 * @param local the <code>OutputStream</code> the file should be written to.
	 * @throws IOException if the transfer fails.
	 */
	public FtpClient getFile(String name, OutputStream local)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("getFile", String.class, name, OutputStream.class, local);
	}
	
	/**
	 * Retrieves a file from the ftp server, using the RETR command, and
	 * returns the InputStream from* the established data connection.
	 * {@link #completePending()} <b>has</b> to be called once the application
	 * is done reading from the returned stream.
	 *
	 * @param name the name of the remote file
	 * @return the {@link java.io.InputStream} from the data connection, or
	 * <code>null</code> if the command was unsuccessful.
	 * @throws IOException if an error occurred during the transmission.
	 */
	public InputStream getFileStream(String name)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("getFileStream", String.class, name);
	}
	
	/**
	 * Transfers a file from the client to the server (aka a <I>put</I>)
	 * by sending the STOR or STOU command, depending on the
	 * <code>unique</code> argument, and returns the <code>OutputStream</code>
	 * from the established data connection.
	 * {@link #completePending()} <b>has</b> to be called once the application
	 * is finished writing to the stream.
	 *
	 * A new file is created at the server site if the file specified does
	 * not already exist.
	 *
	 * If <code>unique</code> is set to <code>true</code>, the resultant file
	 * is to be created under a name unique to that directory, meaning
	 * it will not overwrite an existing file, instead the server will
	 * generate a new, unique, file name.
	 * The name of the remote file can be retrieved, after completion of the
	 * transfer, by calling {@link #getLastFileName()}.
	 *
	 * @param name the name of the remote file to write.
	 * @param unique <code>true</code> if the remote files should be unique,
	 * in which case the STOU command will be used.
	 * @return the {@link java.io.OutputStream} from the data connection or
	 * <code>null</code> if the command was unsuccessful.
	 * @throws IOException if an error occurred during the transmission.
	 */
	public OutputStream putFileStream(String name, boolean unique)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("putFileStream", String.class, name, boolean.class, unique);
	}
	
	/**
	 * Transfers a file from the client to the server (aka a <I>put</I>)
	 * by sending the STOR command. The content of the <code>InputStream</code>
	 * passed in argument is written into the remote file, overwriting any
	 * existing data.
	 *
	 * A new file is created at the server site if the file specified does
	 * not already exist.
	 *
	 * @param name the name of the remote file to write.
	 * @param local the <code>InputStream</code> that points to the data to
	 * transfer.
	 * @param unique <code>true</code> if the remote file should be unique
	 * (i.e. not already existing), <code>false</code> otherwise.
	 * @return <code>true</code> if the transfer was successful.
	 * @throws IOException if an error occurred during the transmission.
	 * @see #getLastFileName()
	 */
	public FtpClient putFile(String name, InputStream local, boolean unique)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("putFile", String.class, name, InputStream.class, local, boolean.class, unique);
	}
	
	/**
	 * Sends the APPE command to the server in order to transfer a data stream
	 * passed in argument and append it to the content of the specified remote
	 * file.
	 *
	 * @param name A <code>String</code> containing the name of the remote file
	 * to append to.
	 * @param local The <code>InputStream</code> providing access to the data
	 * to be appended.
	 * @return <code>true</code> if the transfer was successful.
	 * @throws IOException if an error occurred during the transmission.
	 */
	public FtpClient appendFile(String name, InputStream local)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("appendFile", String.class, name, InputStream.class, local);
	}
	
	/**
	 * Renames a file on the server.
	 *
	 * @param from the name of the file being renamed
	 * @param to the new name for the file
	 * @throws IOException if the command fails
	 */
	public FtpClient rename(String from, String to)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("rename", String.class, from, String.class, to);
	}
	
	/**
	 * Deletes a file on the server.
	 *
	 * @param name a <code>String</code> containing the name of the file
	 * to delete.
	 * @return <code>true</code> if the command was successful
	 * @throws IOException if an error occurred during the exchange
	 */
	public FtpClient deleteFile(String name)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("deleteFile", String.class, name);
	}
	
	/**
	 * Creates a new directory on the server.
	 *
	 * @param name a <code>String</code> containing the name of the directory
	 * to create.
	 * @return <code>true</code> if the operation was successful.
	 * @throws IOException if an error occurred during the exchange
	 */
	public FtpClient makeDirectory(String name)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("makeDirectory", String.class, name);
	}
	
	/**
	 * Removes a directory on the server.
	 *
	 * @param name a <code>String</code> containing the name of the directory
	 * to remove.
	 *
	 * @return <code>true</code> if the operation was successful.
	 * @throws IOException if an error occurred during the exchange.
	 */
	public FtpClient removeDirectory(String name)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("removeDirectory", String.class, name);
	}
	
	/**
	 * Sends a No-operation command. It's useful for testing the connection
	 * status or as a <I>keep alive</I> mechanism.
	 *
	 * @throws FtpProtocolException if the command fails
	 */
	public FtpClient noop()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("noop");
	}
	
	/**
	 * Sends the STAT command to the server.
	 * This can be used while a data connection is open to get a status
	 * on the current transfer, in that case the parameter should be
	 * <code>null</code>.
	 * If used between file transfers, it may have a pathname as argument
	 * in which case it will work as the LIST command except no data
	 * connection will be created.
	 *
	 * @param name an optional <code>String</code> containing the pathname
	 * the STAT command should apply to.
	 * @return the response from the server or <code>null</code> if the
	 * command failed.
	 * @throws IOException if an error occurred during the transmission.
	 */
	public String getStatus(String name)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("getStatus", String.class, name);
	}
	
	/**
	 * Sends the FEAT command to the server and returns the list of supported
	 * features in the form of strings.
	 *
	 * The features are the supported commands, like AUTH TLS, PROT or PASV.
	 * See the RFCs for a complete list.
	 *
	 * Note that not all FTP servers support that command, in which case
	 * the method will return <code>null</code>
	 *
	 * @return a <code>List</code> of <code>Strings</code> describing the
	 * supported additional features, or <code>null</code>
	 * if the command is not supported.
	 * @throws IOException if an error occurs during the transmission.
	 */
	public List<String> getFeatures()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("getFeatures");
	}
	
	/**
	 * sends the ABOR command to the server.
	 * It tells the server to stop the previous command or transfer.
	 *
	 * @return <code>true</code> if the command was successful.
	 * @throws IOException if an error occurred during the transmission.
	 */
	public FtpClient abort()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("abort");
	}
	
	/**
	 * Some methods do not wait until completion before returning, so this
	 * method can be called to wait until completion. This is typically the case
	 * with commands that trigger a transfer like {@link #getFileStream(String)}
	 * .
	 * So this method should be called before accessing information related to
	 * such a command.
	 * <p>
	 * This method will actually block reading on the command channel for a
	 * notification from the server that the command is finished. Such a
	 * notification often carries extra information concerning the completion
	 * of the pending action (e.g. number of bytes transfered).
	 * </p>
	 * <p>
	 * Note that this will return true immediately if no command or action
	 * is pending
	 * </p>
	 * <p>
	 * It should be also noted that most methods issuing commands to the ftp
	 * server will call this method if a previous command is pending.
	 * <p>
	 * Example of use:
	 * 
	 * <pre>
	 * InputStream in = cl.getFileStream("file");
	 * ...
	 * cl.completePending();
	 * long size = cl.getLastTransferSize();
	 * </pre>
	 * 
	 * On the other hand, it's not necessary in a case like:
	 * 
	 * <pre>
	 * InputStream in = cl.getFileStream("file");
	 * // read content
	 * ...
	 * cl.logout();
	 * </pre>
	 * <p>
	 * Since {@link #logout()} will call completePending() if necessary.
	 * </p>
	 * 
	 * @return <code>true</code> if the completion was successful or if no
	 * action was pending.
	 * @throws IOException
	 */
	public FtpClient completePending()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("completePending");
	}
	
	/**
	 * Reinitializes the USER parameters on the FTP server
	 *
	 * @throws FtpProtocolException if the command fails
	 */
	public FtpClient reInit()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("reInit");
	}
	
	/**
	 * Issues a LIST command to the server to get the current directory
	 * listing, and returns the InputStream from the data connection.
	 * {@link #completePending()} <b>has</b> to be called once the application
	 * is finished writing to the stream.
	 *
	 * @param path the pathname of the directory to list, or <code>null</code>
	 * for the current working directory.
	 * @return the <code>InputStream</code> from the resulting data connection
	 * @throws IOException if an error occurs during the transmission.
	 * @see #changeDirectory(String)
	 * @see #listFiles(String)
	 */
	public InputStream list(String path)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("list", String.class, path);
	}
	
	/**
	 * Issues a NLST path command to server to get the specified directory
	 * content. It differs from {@link #list(String)} method by the fact that
	 * it will only list the file names which would make the parsing of the
	 * somewhat easier.
	 *
	 * {@link #completePending()} <b>has</b> to be called once the application
	 * is finished writing to the stream.
	 *
	 * @param path a <code>String</code> containing the pathname of the
	 * directory to list or <code>null</code> for the current working
	 * directory.
	 * @return the <code>InputStream</code> from the resulting data connection
	 * @throws IOException if an error occurs during the transmission.
	 */
	public InputStream nameList(String path)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("nameList", String.class, path);
	}
	
	/**
	 * Issues the SIZE [path] command to the server to get the size of a
	 * specific file on the server.
	 * Note that this command may not be supported by the server. In which
	 * case -1 will be returned.
	 *
	 * @param path a <code>String</code> containing the pathname of the
	 * file.
	 * @return a <code>long</code> containing the size of the file or -1 if
	 * the server returned an error, which can be checked with
	 * {@link #getLastReplyCode()}.
	 * @throws IOException if an error occurs during the transmission.
	 */
	public long getSize(String path)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("getSize", String.class, path);
	}
	/**
	 * Issues the MDTM [path] command to the server to get the modification
	 * time of a specific file on the server.
	 * Note that this command may not be supported by the server, in which
	 * case <code>null</code> will be returned.
	 *
	 * @param path a <code>String</code> containing the pathname of the file.
	 * @return a <code>Date</code> representing the last modification time
	 * or <code>null</code> if the server returned an error, which
	 * can be checked with {@link #getLastReplyCode()}.
	 * @throws IOException if an error occurs during the transmission.
	 */
	public Date getLastModified(String path)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("getLastModified", String.class, path);
	}
	
	/**
	 * Issues a MLSD command to the server to get the specified directory
	 * listing and applies the current parser to create an Iterator of
	 * {@link java.net.ftp.FtpDirEntry}. Note that the Iterator returned is also
	 * a
	 * {@link java.io.Closeable}.
	 * If the server doesn't support the MLSD command, the LIST command is used
	 * instead.
	 *
	 * {@link #completePending()} <b>has</b> to be called once the application
	 * is finished iterating through the files.
	 *
	 * @param path the pathname of the directory to list or <code>null</code>
	 * for the current working directoty.
	 * @return a <code>Iterator</code> of files or <code>null</code> if the
	 * command failed.
	 * @throws IOException if an error occurred during the transmission
	 * @see #setDirParser(FtpDirParser)
	 * @see #changeDirectory(String)
	 */
	public Iterator<FtpDirEntry> listFiles(String path)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("listFiles", String.class, path);
	}
	
	/**
	 * Attempts to use Kerberos GSSAPI as an authentication mechanism with the
	 * ftp server. This will issue an <code>AUTH GSSAPI</code> command, and if
	 * it is accepted by the server, will followup with <code>ADAT</code>
	 * command to exchange the various tokens until authentification is
	 * successful. This conforms to Appendix I of RFC 2228.
	 *
	 * @return <code>true</code> if authentication was successful.
	 * @throws IOException if an error occurs during the transmission.
	 */
	public FtpClient useKerberos()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("useKerberos");
	}
	
	/**
	 * Returns the Welcome string the server sent during initial connection.
	 *
	 * @return a <code>String</code> containing the message the server
	 * returned during connection or <code>null</code>.
	 */
	public String getWelcomeMsg(){
		return invokeQualifiedMethod("getWelcomeMsg");
	}
	
	/**
	 * Returns the last reply code sent by the server.
	 *
	 * @return the lastReplyCode
	 */
	public FtpReplyCode getLastReplyCode(){
		return invokeQualifiedMethod("getLastReplyCode");
	}
	
	/**
	 * Returns the last response string sent by the server.
	 *
	 * @return the message string, which can be quite long, last returned
	 * by the server.
	 */
	public String getLastResponseString(){
		return invokeQualifiedMethod("getLastResponseString");
	}
	
	/**
	 * Returns, when available, the size of the latest started transfer.
	 * This is retreived by parsing the response string received as an initial
	 * response to a RETR or similar request.
	 *
	 * @return the size of the latest transfer or -1 if either there was no
	 * transfer or the information was unavailable.
	 */
	public long getLastTransferSize(){
		return invokeQualifiedMethod("getLastTransferSize");
	}
	
	/**
	 * Returns, when available, the remote name of the last transfered file.
	 * This is mainly useful for "put" operation when the unique flag was
	 * set since it allows to recover the unique file name created on the
	 * server which may be different from the one submitted with the command.
	 *
	 * @return the name the latest transfered file remote name, or
	 * <code>null</code> if that information is unavailable.
	 */
	public String getLastFileName(){
		return invokeQualifiedMethod("getLastFileName");
	}
	
	/**
	 * Attempts to switch to a secure, encrypted connection. This is done by
	 * sending the "AUTH TLS" command.
	 * <p>
	 * See <a href="http://www.ietf.org/rfc/rfc4217.txt">RFC 4217</a>
	 * </p>
	 * If successful this will establish a secure command channel with the
	 * server, it will also make it so that all other transfers (e.g. a RETR
	 * command) will be done over an encrypted channel as well unless a
	 * {@link #reInit()} command or a {@link #endSecureSession()} command is
	 * issued.
	 *
	 * @return <code>true</code> if the operation was successful.
	 * @throws IOException if an error occurred during the transmission.
	 * @see #endSecureSession()
	 */
	public FtpClient startSecureSession()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("startSecureSession");
	}
	
	/**
	 * Sends a <code>CCC</code> command followed by a <code>PROT C</code>
	 * command to the server terminating an encrypted session and reverting
	 * back to a non crypted transmission.
	 *
	 * @return <code>true</code> if the operation was successful.
	 * @throws IOException if an error occurred during transmission.
	 * @see #startSecureSession()
	 */
	public FtpClient endSecureSession()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("endSecureSession");
	}
	
	/**
	 * Sends the "Allocate" (ALLO) command to the server telling it to
	 * pre-allocate the specified number of bytes for the next transfer.
	 *
	 * @param size The number of bytes to allocate.
	 * @return <code>true</code> if the operation was successful.
	 * @throws IOException if an error occurred during the transmission.
	 */
	public FtpClient allocate(long size)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("allocate", long.class, size);
	}
	
	/**
	 * Sends the "Structure Mount" (SMNT) command to the server. This let the
	 * user mount a different file system data structure without altering his
	 * login or accounting information.
	 *
	 * @param struct a <code>String</code> containing the name of the
	 * structure to mount.
	 * @return <code>true</code> if the operation was successful.
	 * @throws IOException if an error occurred during the transmission.
	 */
	public FtpClient structureMount(String struct)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("structureMount", String.class, struct);
	}
	
	/**
	 * Sends a SYST (System) command to the server and returns the String
	 * sent back by the server describing the operating system at the
	 * server.
	 *
	 * @return a <code>String</code> describing the OS, or <code>null</code>
	 * if the operation was not successful.
	 * @throws IOException if an error occurred during the transmission.
	 */
	public String getSystem()
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("getSystem");
	}
	
	/**
	 * Sends the HELP command to the server, with an optional command, like
	 * SITE, and returns the text sent back by the server.
	 *
	 * @param cmd the command for which the help is requested or
	 * <code>null</code> for the general help
	 * @return a <code>String</code> containing the text sent back by the
	 * server, or <code>null</code> if the command failed.
	 * @throws IOException if an error occurred during transmission
	 */
	public String getHelp(String cmd)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("getHelp", String.class, cmd);
	}
	
	/**
	 * Sends the SITE command to the server. This is used by the server
	 * to provide services specific to his system that are essential
	 * to file transfer.
	 *
	 * @param cmd the command to be sent.
	 * @return <code>true</code> if the command was successful.
	 * @throws IOException if an error occurred during transmission
	 */
	public FtpClient siteCmd(String cmd)
			throws FtpProtocolException, IOException{
		return invokeQualifiedMethod("siteCmd", String.class, cmd);
	}
	
	private class FtpFileIterator implements Iterator<FtpDirEntry>, Closeable{
		private final Iterator<?> it;
		FtpFileIterator(Iterator<?> it){
			if(!Closeable.class.isAssignableFrom(it.getClass()))
				throw new InternalError("Argument \"it\" does not implement Closeable!");
			this.it=it;
		}
		public void close() throws IOException{
			Closeable.class.cast(it).close();
		}
		public boolean hasNext(){
			return it.hasNext();
		}
		public FtpDirEntry next(){
			return new FtpDirEntry(it.next());
		}
		public void remove(){
			it.remove();
		}
	}
}
